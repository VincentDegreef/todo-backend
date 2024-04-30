package VincentDegreef.todobackend.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.project.repo.ProjectRepository;
import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.todoItem.repo.TodoItemRepository;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.repo.UserRepository;

@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoItemRepository todoItemRepository;

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
        int random = (int)(Math.random() * 1000000 + 1);

        project.setProjectInviteCode(Integer.toString(random));

        project.addProjectMember(projectUser);
        project.setProjectOwner(projectUser.getUsername());
        projectRepository.save(project);
        projectUser.addProject(project);
        userRepository.save(projectUser);
        return project;
    }

    public Project joinProject(Long userId, String code) throws ProjectServiceException{
        if(userRepository.findUserById(userId) == null){
            throw new ProjectServiceException("User does not exist", "userId");
        }
        if(projectRepository.findProjectByProjectInviteCode(code) == null){
            throw new ProjectServiceException("Project does not exist", "code");
        }
        Project project = projectRepository.findProjectByProjectInviteCode(code);
        User projectUser = userRepository.findUserById(userId);
        project.addProjectMember(projectUser);
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

    public Project deleteProject(Long projectId, Long userId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        if(userRepository.findUserById(userId) == null){
            throw new ProjectServiceException("User does not exist", "userId");
        }

        Project project = projectRepository.findProjectById(projectId);
        User projectUser = userRepository.findUserById(userId);
        project.setTasks(null);
        projectRepository.save(project);
        projectUser.removeProject(project);
        userRepository.save(projectUser);
        projectRepository.delete(project);
        return project;
    }


    public Project updateProject(Project project, Long projectId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        Project projectToUpdate = projectRepository.findProjectById(projectId);
        projectToUpdate.setProjectName(project.getProjectName());
        projectToUpdate.setProjectDescription(project.getProjectDescription());
        projectRepository.save(projectToUpdate);
        return projectToUpdate;
    }

    public Project addTaskToProject(Long projectId, TodoItem task) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        Project project = projectRepository.findProjectById(projectId);

        task.setProject(project);
        todoItemRepository.save(task);
        project.addTask(task);
        projectRepository.save(project);

        return project;
    }

    public Project removeTaskFromProject(Long projectId, Long taskId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        if(todoItemRepository.findItemById(taskId) == null){
            throw new ProjectServiceException("Task does not exist", "taskId");
        }
        Project project = projectRepository.findProjectById(projectId);
        TodoItem task = todoItemRepository.findItemById(taskId);

        project.removeTask(task);
        projectRepository.save(project);
        todoItemRepository.delete(task);

        return project;
    }

    public List<TodoItem> getTasksFromProject(Long projectId) throws ProjectServiceException{
        if(projectRepository.findProjectById(projectId) == null){
            throw new ProjectServiceException("Project does not exist", "projectId");
        }
        Project project = projectRepository.findProjectById(projectId);
        return project.getTasks();
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    
}
