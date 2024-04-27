package VincentDegreef.todobackend.todoItem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import VincentDegreef.todobackend.todoItem.model.TodoItem;
import VincentDegreef.todobackend.todoItem.service.TodoItemService;
import VincentDegreef.todobackend.todoItem.service.TodoItemServiceException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todoItems")
public class TodoItemRestController {
    
    @Autowired
    private TodoItemService todoItemService;

    public TodoItemRestController() {
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem newTodoItem, @PathVariable Long userId) {
        try {
            TodoItem createdTodoItem = todoItemService.createToDoItem(newTodoItem, userId);
            return new ResponseEntity<TodoItem>(createdTodoItem, HttpStatus.CREATED);
        } catch (TodoItemServiceException e) {
            return new ResponseEntity<TodoItem>(HttpStatus.BAD_REQUEST);
        }
    }

    
}
