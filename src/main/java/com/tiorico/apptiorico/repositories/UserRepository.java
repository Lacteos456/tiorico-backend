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
    Optional<User> findByResetToken(String resetToken);
	boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    public User findByUsername(String username);
    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);
    User getUserByResetToken(String resetToken);

    @Query("SELECT u FROM users u JOIN u.userRoles ur WHERE ur.rol.name = 'NORMAL' AND u.isActive = true")
    List<User> findAllByNormalRole();

    @Query("SELECT u FROM users u JOIN u.userRoles ur WHERE ur.rol.name = 'ADMIN' AND u.isActive = true")
    List<User> findAllByAdminRole();
}