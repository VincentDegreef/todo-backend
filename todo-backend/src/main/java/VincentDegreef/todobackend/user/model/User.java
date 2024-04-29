package VincentDegreef.todobackend.user.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.roles.model.Role;
import VincentDegreef.todobackend.todoItem.model.TodoItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ManyToMany
    @JoinTable( name="user_todos",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="todo_id"))
    private List<TodoItem> todoItems;


    @OneToMany(mappedBy = "projectOwner" ,fetch=FetchType.EAGER)
    private List<Project> projects;

    public User() {
    }

    public User(String Email, String Password){
        setEmail(Email);
        setPassword(Password);
    }

    public User(String username, String password, String email, Role role) {
        setUsername(username);
        setPassword(password);
        setEmail(email);
        setRole(role);
    }


    

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem todoItem){
        if(todoItems == null){
            todoItems = new ArrayList<>();
        }
        todoItems.add(todoItem);
    }

    public void removeTodoItem(TodoItem todoItem){
        todoItems.remove(todoItem);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(Project project){
        if(projects == null){
            projects = new ArrayList<>();
        }
        projects.add(project);
    }

    public void removeProject(Project project){
        projects.remove(project);
    }



   

    public void setId(Long id) {
        this.id = id;
    }

   

    public void setTodoItems(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Create a list to hold granted authorities
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add a granted authority for each role assigned to the user
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));

        // Return the list of granted authorities
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return true;
    }


}
