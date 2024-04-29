package VincentDegreef.todobackend.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.service.UserService;
import VincentDegreef.todobackend.user.service.UserServiceException;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserRestController {
    
    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.BAD_REQUEST )
    @ExceptionHandler({
    MethodArgumentNotValidException.class})
    public Map<String, String>
    handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    
    @ResponseStatus(HttpStatus.BAD_REQUEST )
    @ExceptionHandler({ UserServiceException.class})
    public Map<String, String>
    handleServiceExceptions(UserServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    public UserRestController() {}


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<User> getAllUsers() throws UserServiceException{
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @GetMapping("/userTodos/{userId}")
    public List<TodoItem> getUserTodos(@PathVariable Long userId) throws UserServiceException{
        return userService.getUserTodos(userId);
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @GetMapping("/userProject/{userId}")
    public List<Project> getUserProjects(@PathVariable Long userId) throws UserServiceException{
        return userService.getUserProjects(userId);
    }


}
