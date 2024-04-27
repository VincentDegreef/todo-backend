package VincentDegreef.todobackend.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.project.repo.ProjectRepository;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.repo.UserRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectService(){}


    public Project CreateProject(Project project, Long userId) throws ProjectServiceException{
        if(projectRepository.findByProjectName(project.getProjectName()) != null){
            throw new ProjectServiceException("Project with name " + project.getProjectName() + " already exists", "projectName");
        }
        if(userRepository.findUserById(userId) == null){
            throw new ProjectServiceException("User does not exist", "userId");
        }

        project.setProjectCreationDate(java.time.LocalDate.now());
        User projectUser = userRepository.findUserById(userId);

        project.setProjectOwner(projectUser);
        projectRepository.save(project);
        projectUser.addProject(project);
        userRepository.save(projectUser);

        return project;
    }

    public Project getProjectById(Long projectId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }   
        return projectRepository.findProjectById(projectId);
    }

    public Project DeleteProject(Long projectId, Long userId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        if(userRepository.findUserById(userId) == null){
            throw new ProjectServiceException("User does not exist", "userId");
        }

        Project project = projectRepository.findProjectById(projectId);
        User projectUser = userRepository.findUserById(userId);
        projectUser.removeProject(project);
        userRepository.save(projectUser);
        projectRepository.delete(project);
        return project;
    }

    
}
