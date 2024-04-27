package VincentDegreef.todobackend.project.model;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;

import VincentDegreef.todobackend.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User projectOwner;

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

}
