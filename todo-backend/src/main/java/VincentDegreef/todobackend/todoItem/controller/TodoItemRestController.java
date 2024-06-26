package VincentDegreef.todobackend.todoItem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.todoItem.service.TodoItemService;
import VincentDegreef.todobackend.todoItem.service.TodoItemServiceException;
import VincentDegreef.todobackend.user.service.UserServiceException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todoItems")
public class TodoItemRestController {
    
    @Autowired
    private TodoItemService todoItemService;

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
    @ExceptionHandler({ TodoItemServiceException.class})
    public Map<String, String>
    handleServiceExceptions(TodoItemServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    public TodoItemRestController() {
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> todoItems = todoItemService.getAllTodoItems();
        return new ResponseEntity<List<TodoItem>>(todoItems, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem newTodoItem, @PathVariable Long userId) {
        try {
            TodoItem createdTodoItem = todoItemService.createToDoItem(newTodoItem, userId);
            return new ResponseEntity<TodoItem>(createdTodoItem, HttpStatus.CREATED);
        } catch (TodoItemServiceException e) {
            return new ResponseEntity<TodoItem>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PutMapping("/inprogress/{itemId}")
    public ResponseEntity<TodoItem> setItemInProgress(@PathVariable Long itemId) {
        try {
            TodoItem inProgressItem = todoItemService.setItemInprogress(itemId);
            return new ResponseEntity<TodoItem>(inProgressItem, HttpStatus.OK);
        } catch (TodoItemServiceException e) {
            return new ResponseEntity<TodoItem>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PutMapping("/done/{itemId}")
    public ResponseEntity<TodoItem> setItemDone(@PathVariable Long itemId) {
        try {
            TodoItem doneItem = todoItemService.setItemDone(itemId);
            return new ResponseEntity<TodoItem>(doneItem, HttpStatus.OK);
        } catch (TodoItemServiceException e) {
            return new ResponseEntity<TodoItem>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @DeleteMapping("/delete/{itemId}/{userId}")
    public ResponseEntity<TodoItem> deleteItem(@PathVariable Long itemId, @PathVariable Long userId) {
        try {
            todoItemService.removeTodoItem(itemId, userId);
            return new ResponseEntity<TodoItem>(HttpStatus.OK);
        } catch (TodoItemServiceException e) {
            return new ResponseEntity<TodoItem>(HttpStatus.BAD_REQUEST);
        }
    }



    
}
