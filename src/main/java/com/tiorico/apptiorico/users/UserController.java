package com.tiorico.apptiorico.users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tiorico.apptiorico.exceptions.ErrorResponse;
import com.tiorico.apptiorico.exceptions.UserException;
import com.tiorico.apptiorico.roles.Rol;
import com.tiorico.apptiorico.userRoles.UserRol;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
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
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserSignupDTO userDTO, BindingResult bindingResult, @RequestParam(required = false, defaultValue = "NORMAL") String role) throws Exception {
    	if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    	try {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setIsActive(true);
            
            Set<UserRol> userRoles = new HashSet<>();
            
            Rol rol = new Rol();
    		
    		UserRol userRol = new UserRol();
    		
    		if ("ADMIN".equalsIgnoreCase(role)) {
                rol.setId(1);
                rol.setName("ADMIN");
            }else{
            	rol.setId(2);
        		rol.setName("NORMAL");
            }
    		rol.setIsActive(true);
    		
    		userRol.setUser(user);
    		userRol.setRol(rol);
    		userRol.setIsActive(true);
    		
    		userRoles.add(userRol);

            User createdUser = userService.createUser(user, userRoles);

            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (UserException.PhoneAlreadyExistsException | UserException.EmailAlreadyExistsException | UserException.UsernameAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse("User creation failed", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", "An unexpected error occurred.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            User user = userService.getUser(username);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserException.UserNotActiveException | UserException.UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("User not found", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", "An unexpected error occurred.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserSignupDTO userDTO) {
        try {
            User userToUpdate = new User();
            userToUpdate.setId(id);
            userToUpdate.setUsername(userDTO.getUsername());
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
            userToUpdate.setEmail(userDTO.getEmail());
            userToUpdate.setPhone(userDTO.getPhone());

            User updatedUser = userService.updateUser(userToUpdate);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (UserException.UserNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse("User not found", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (UserException.UsernameAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse("Username already exists", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (UserException.EmailAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse("Email already exists", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (UserException.PhoneAlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse("Phone already exists", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponse errorResponse = new ErrorResponse("Internal server error", "An unexpected error occurred.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@DeleteMapping
	public ResponseEntity<?> deleteUser(@RequestParam("id") Integer userId, @RequestParam("type") String type) {
	    try {
	        if ("hard".equalsIgnoreCase(type)) {
	            boolean isDeleted = userService.deleteUserById(userId);
	            if (isDeleted) {
	                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            } else {
	                ErrorResponse errorResponse = new ErrorResponse("User not found", "The user with the given ID does not exist.");
	                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	            }
	        } else if ("soft".equalsIgnoreCase(type)) {
	        	 User user = userService.getUserById(userId);
	             if (user != null) {
	                 if (!user.getIsActive()) {
	                     ErrorResponse errorResponse = new ErrorResponse("User already inactive", "The user with the given ID is already inactive.");
	                     return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	                 }
	                 userService.softDeleteUser(user);
	                 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	             } else {
	                 ErrorResponse errorResponse = new ErrorResponse("User not found", "The user with the given ID does not exist.");
	                 return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	             }
	        } else {
	            ErrorResponse errorResponse = new ErrorResponse("Invalid type", "The deletion type is not recognized.");
	            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        ErrorResponse errorResponse = new ErrorResponse("Internal server error", "An unexpected error occurred.");
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}