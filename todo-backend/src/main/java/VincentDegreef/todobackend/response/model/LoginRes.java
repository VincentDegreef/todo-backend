package VincentDegreef.todobackend.response.model;

public class LoginRes {
    private Long id;
    private String email;
    private String token;
    private String username;

    public LoginRes(Long id, String email, String token, String username) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.username = username;

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

}
