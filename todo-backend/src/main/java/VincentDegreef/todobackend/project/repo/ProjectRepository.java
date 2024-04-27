package VincentDegreef.todobackend.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import VincentDegreef.todobackend.project.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    
    public Project findByProjectName(String projectName);

    public Project findProjectById(Long id);
}
