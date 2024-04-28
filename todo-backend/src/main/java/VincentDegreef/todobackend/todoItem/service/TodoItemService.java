package VincentDegreef.todobackend.todoItem.service;

import java.util.List;

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

    public TodoItem setItemInprogress(Long itemId) throws TodoItemServiceException{
        if(todoItemRepository.findById(itemId).isEmpty()){
            throw new TodoItemServiceException("Item not found", "itemId");
        }
        TodoItem item = todoItemRepository.findById(itemId).get();
        item.setNotStarted(false);
        item.setInProgress(true);

        todoItemRepository.save(item);
        return item;
    }

    public TodoItem setItemDone(Long itemId) throws TodoItemServiceException{
        if(todoItemRepository.findById(itemId).isEmpty()){
            throw new TodoItemServiceException("Item not found", "itemId");
        }
        TodoItem item = todoItemRepository.findById(itemId).get();
        item.setInProgress(false);
        item.setCompleted(true);

        todoItemRepository.save(item);
        return item;
    }

    public TodoItem removeTodoItem(Long itemId, Long userId) throws TodoItemServiceException{
        if(userRepository.findById(userId).isEmpty()){
            throw new TodoItemServiceException("User not found", "userId");
        }
        User user = userRepository.findById(userId).get();
        if(todoItemRepository.findById(itemId).isEmpty()){
            throw new TodoItemServiceException("Item not found", "itemId");
        }
        
        TodoItem item = todoItemRepository.findById(itemId).get();
        user.removeTodoItem(item);
        userRepository.save(user);
        todoItemRepository.delete(item);
        return item;
    }

    public List<TodoItem> getAllTodoItems(){
        return todoItemRepository.findAll();
    }
}
