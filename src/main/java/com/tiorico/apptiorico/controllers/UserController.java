package com.tiorico.apptiorico.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.services.UserService;
import com.tiorico.apptiorico.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.tiorico.apptiorico.models.Rol;
import com.tiorico.apptiorico.models.UserRol;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@CrossOrigin("*")
public class UserController
{
	@Autowired
	private UserService userService;
    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO, @RequestParam(required = false, defaultValue = "NORMAL") String role) throws Exception {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setIsActive(true);

        // Definir roles del usuario
        Set<UserRol> userRoles = new HashSet<>();
        Rol rol = new Rol();

        if ("ADMIN".equalsIgnoreCase(role)) {
            rol.setId(1);
            rol.setName("ADMIN");
        } else {
            rol.setId(2);
            rol.setName("NORMAL");
        }
        rol.setIsActive(true);

        UserRol userRol = new UserRol();
        userRol.setUser(user);
        userRol.setRol(rol);
        userRol.setIsActive(true);
        userRoles.add(userRol);

        // Crear usuario
        User createdUser = userService.createUser(user, userRoles);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) throws Exception {
        User user = userService.getUser(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDTO userDTO) throws Exception {
        User userToUpdate = new User();
        userToUpdate.setId(id);
        userToUpdate.setUsername(userDTO.getUsername());
        userToUpdate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPhone(userDTO.getPhone());

        User updatedUser = userService.updateUser(userToUpdate);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam("id") Integer userId, @RequestParam("type") String type) throws Exception {
        if ("hard".equalsIgnoreCase(type)) {
            boolean isDeleted = userService.deleteUserById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if ("soft".equalsIgnoreCase(type)) {
            User user = userService.getUserById(userId);
            userService.softDeleteUser(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new IllegalArgumentException("Invalid deletion type");
        }
    }

    @GetMapping("/normal")
    public ResponseEntity<List<UserDTO>> getNormalUsers() {
        List<User> normalUsers = userService.getNormalUsers();

        // Convertir la lista de usuarios en una lista de UserDTO
        List<UserDTO> normalUsersDTO = normalUsers.stream()
                .map(user -> UserDTO.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(normalUsersDTO);
    }
}