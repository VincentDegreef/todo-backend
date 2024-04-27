package VincentDegreef.todobackend.todoItem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.todoItem.repo.TodoItemRepository;
import VincentDegreef.todobackend.user.model.User;
import VincentDegreef.todobackend.user.repo.UserRepository;

@Service
public class TodoItemService {
    

    @Autowired
    private TodoItemRepository todoItemRepository;

    @Autowired
    private UserRepository userRepository;

    
    public TodoItemService() {}


    public TodoItem createToDoItem(TodoItem newtodoItem, Long userId) throws TodoItemServiceException{
        if(userRepository.findById(userId).isEmpty()){
            throw new TodoItemServiceException("User not found", "userId");
        }
        User user = userRepository.findById(userId).get();

        todoItemRepository.save(newtodoItem);

        user.addTodoItem(newtodoItem);
        userRepository.save(user);
        return newtodoItem;
    }
}
