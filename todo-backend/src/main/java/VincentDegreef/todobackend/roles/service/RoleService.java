package VincentDegreef.todobackend.roles.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import VincentDegreef.todobackend.roles.model.Role;
import VincentDegreef.todobackend.roles.repo.RoleRepository;


@Service
public class RoleService {
    
    @Autowired
    private final RoleRepository roleRepository;

   
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    public Role saveRole(Role role) {  
        return roleRepository.save(role);
    }


    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }


    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + id + " not found"));
    }

    // Other methods for updating, deleting, and retrieving roles can be added here
}
