package VincentDegreef.todobackend.project.service;

public class ProjectServiceException  extends Exception{
    private String field;
    public ProjectServiceException(String message, String field) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
