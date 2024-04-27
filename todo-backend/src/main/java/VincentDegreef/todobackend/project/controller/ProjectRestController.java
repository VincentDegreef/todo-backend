package VincentDegreef.todobackend.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/projects")
public class ProjectRestController {
    @Autowired
    private ProjectService projectService;

    public ProjectRestController() {
    }


    @PostMapping("/create/{userId}")
    public Project createProject(@RequestBody Project project, @PathVariable Long userId) {
        try {
            return projectService.CreateProject(project, userId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{projectId}")
    public Project getProjectById(@PathVariable Long projectId) {
        try {
            return projectService.getProjectById(projectId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/delete/{projectId}/{userId}")
    public Project deleteProject(@PathVariable Long projectId, @PathVariable Long userId) {
        try {
            return projectService.DeleteProject(projectId, userId);
        } catch (ProjectServiceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
