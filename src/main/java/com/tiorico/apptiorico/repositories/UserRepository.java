package com.tiorico.apptiorico.repositories;

import java.util.List;
import java.util.Optional;

import com.tiorico.apptiorico.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
	boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    public User findByUsername(String username);
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM users u JOIN u.userRoles ur WHERE ur.rol.name = 'NORMAL'")
    List<User> findAllByNormalRole();
}