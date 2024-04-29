package VincentDegreef.todobackend.response.model;

import java.util.List;

import VincentDegreef.todobackend.roles.model.Role;

public class LoginRes {
    private Long id;
    private String email;
    private String token;
    private String username;
    private String role;

    public LoginRes(Long id, String email, String token, String username, String role) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
