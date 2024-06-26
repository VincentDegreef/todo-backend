package VincentDegreef.todobackend.user.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import VincentDegreef.todobackend.Auth.JwtUtil;
import VincentDegreef.todobackend.response.model.ErrorRes;
import VincentDegreef.todobackend.response.model.LoginRes;
import VincentDegreef.todobackend.roles.model.Role;
import VincentDegreef.todobackend.roles.service.RoleService;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.service.UserService;
import VincentDegreef.todobackend.user.service.UserServiceException;
import VincentDegreef.todobackend.request.model.LoginReq;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

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

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginReq loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = userService.getByEmail(email);
            String token = jwtUtil.createToken(user);
            Long id = user.getId();
            String username = user.getUsername();
            String role = user.getRole().getName();
            LoginRes loginRes = new LoginRes(id, email,token ,username, role);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User newUser) throws UserServiceException {
        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);

        // Check if the role is already persisted or not
        Role role = newUser.getRole();
        if (role != null && role.getId() == null) {
            // Role is transient, so persist it
            roleService.saveRole(role); // Assuming you have a service for managing roles
        }

        // Now the role should be persisted, so you can safely create the user
        return userService.createUser(newUser);
    }

    // @PostMapping("/register")
    // public User createUser(@Valid @RequestBody User newUser) throws UserServiceException {
    //     String hashedPassword = passwordEncoder.encode(newUser.getPassword());
    //     newUser.setPassword(hashedPassword);
        
    //     return userService.createUser(newUser);
    // }
}
