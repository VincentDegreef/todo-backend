package VincentDegreef.todobackend.project.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "projects")
public class Project {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is mandatory")
    private String projectName;

    @NotBlank(message = "Project description is mandatory")
    private String projectDescription;

    private LocalDate projectCreationDate;
    
    private String projectInviteCode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User projectOwner;


    @OneToMany(mappedBy = "project")
    private List<TodoItem> tasks;

    public Project() {
    }

    public Project(String projectName, String projectDescription) {
        setProjectName(projectName);
        setProjectDescription(projectDescription);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectInviteCode() {
        return projectInviteCode;
    }

    public void setProjectInviteCode(String projectInviteCode) {
        this.projectInviteCode = projectInviteCode;
    }


    public User getProjectOwner() {
        return projectOwner;
    }

    public void setProjectOwner(User projectOwner) {
        this.projectOwner = projectOwner;
    }

    public LocalDate getProjectCreationDate() {
        return projectCreationDate;
    }

    public void setProjectCreationDate(LocalDate projectCreationDate) {
        this.projectCreationDate = projectCreationDate;
    }

    public List<TodoItem> getTasks() {
        return tasks;
    }

    public void setTasks(List<TodoItem> tasks) {
        this.tasks = tasks;
    }

    public void addTask(TodoItem task) {
        if(tasks == null){
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public void removeTask(TodoItem task) {
        tasks.remove(task);
    }



}
