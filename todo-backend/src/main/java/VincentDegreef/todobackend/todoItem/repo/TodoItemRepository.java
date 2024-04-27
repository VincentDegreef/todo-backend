package VincentDegreef.todobackend.todoItem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import VincentDegreef.todobackend.todoItem.model.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long>{

    public TodoItem findByTitle(String title);

    public TodoItem findItemById(Long id);

    public List<TodoItem> findByCompleted(boolean completed);


}
