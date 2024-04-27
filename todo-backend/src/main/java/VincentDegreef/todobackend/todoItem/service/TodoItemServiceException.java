package VincentDegreef.todobackend.todoItem.service;

public class TodoItemServiceException extends Exception {
    private String field;
    public TodoItemServiceException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
    
}
