package VincentDegreef.todobackend.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.project.service.ProjectService;
import VincentDegreef.todobackend.project.service.ProjectServiceException;
import VincentDegreef.todobackend.todoItem.model.TodoItem;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectRestController {
    @Autowired
    private ProjectService projectService;

    public ProjectRestController() {
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }


    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PostMapping("/create/{userId}")
    public Project createProject(@RequestBody Project project, @PathVariable Long userId) {
        try {
            return projectService.CreateProject(project, userId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable Long projectId) {
        try {
            return projectService.getProjectById(projectId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @DeleteMapping("/delete/{projectId}/{userId}")
    public Project deleteProject(@PathVariable Long projectId, @PathVariable Long userId) {
        try {
            return projectService.deleteProject(projectId, userId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PostMapping("/addTask/{projectId}")
    public Project addTaskToProject(@RequestBody TodoItem todoItem, @PathVariable Long projectId) {
        try {
            return projectService.addTaskToProject(projectId, todoItem);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @DeleteMapping("/deleteTask/{projectId}/{taskId}")
    public Project deleteTaskFromProject(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            return projectService.removeTaskFromProject(projectId, taskId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @GetMapping("/tasks/{projectId}")
    public List<TodoItem> getTasksFromProject(@PathVariable Long projectId) {
        try {
            return projectService.getTasksFromProject(projectId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PostMapping("/join/{userId}/{inviteCode}")
    public Project joinProject(@PathVariable Long userId, @PathVariable String inviteCode) {
        try {
            return projectService.joinProject( userId, inviteCode);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
