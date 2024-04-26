package VincentDegreef.todobackend.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import VincentDegreef.todobackend.user.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    public User findByUsername(String username);

    public User findByEmail(String email);

    public User findUserById(Long id);
    
} 
