package VincentDegreef.todobackend.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VincentDegreef.todobackend.project.model.Project;
import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.repo.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public UserService() {
    }

    public User createUser(User newUser) throws UserServiceException{
        if(userRepository.findByUsername(newUser.getUsername()) != null ){
            throw new UserServiceException("Username already exists", "error");
        }
        if(userRepository.findByEmail(newUser.getEmail()) != null ){
            throw new UserServiceException("Email already exists", "error");
        }
        userRepository.save(newUser);
        return newUser;
    }

    public List<User> getAllUsers() throws UserServiceException{
        if(userRepository.findAll().isEmpty()){
            throw new UserServiceException("No users found", "error");
        }
        return userRepository.findAll();
    }

    public User getByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User deleteUser(Long id){
        User user = userRepository.findUserById(id);
        userRepository.delete(user);
        return user;
    }

    public List<TodoItem> getUserTodos(Long id) throws UserServiceException{
        if(userRepository.findUserById(id).getTodoItems().isEmpty()){
            throw new UserServiceException("No todos found", "error");
        }
        User user = userRepository.findUserById(id);
        return user.getTodoItems();
    }

    public List<Project> getUserProjects(Long id) throws UserServiceException{
        if(userRepository.findUserById(id).getProjects().isEmpty()){
            throw new UserServiceException("No projects found", "error");
        }
        User user = userRepository.findUserById(id);
        return user.getProjects();
    }

}
