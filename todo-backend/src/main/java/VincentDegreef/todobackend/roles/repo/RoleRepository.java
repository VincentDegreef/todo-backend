package VincentDegreef.todobackend.roles.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import VincentDegreef.todobackend.roles.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
