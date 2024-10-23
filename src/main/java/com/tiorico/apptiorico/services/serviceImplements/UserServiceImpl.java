package com.tiorico.apptiorico.services.serviceImplements;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.repositories.UserRepository;
import com.tiorico.apptiorico.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiorico.apptiorico.exceptions.UserException;
import com.tiorico.apptiorico.repositories.RolRepository;
import com.tiorico.apptiorico.models.UserRol;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RolRepository rolRepository;
	
	public User createUser(User user, Set<UserRol> userRoles) throws UserException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserException.UsernameAlreadyExistsException(user.getUsername());
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserException.EmailAlreadyExistsException(user.getEmail());
        }
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new UserException.PhoneAlreadyExistsException(user.getPhone());
        }
        // Guardar cada rol asociado al usuario
        for (UserRol userRol : userRoles){
            rolRepository.save(userRol.getRol());
        }

        user.getUserRoles().addAll(userRoles);

        return userRepository.save(user);
    }

    @Override
    public User getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getIsActive()) {
            throw new UserException.UserNotActiveException(username);
        }
        return user;
    }

    @Override
    public User updateUser(User user) throws UserException {
    	Optional<User> optionalUser = userRepository.findById(user.getId());
        
        if (!optionalUser.isPresent()) {
            throw new UserException.UserNotFoundException(user.getUsername());
        }
        
        User existingUser = optionalUser.get();

	    if (!user.getUsername().equals(existingUser.getUsername()) &&
	        userRepository.existsByUsername(user.getUsername())) {
	        throw new UserException.UsernameAlreadyExistsException(user.getUsername());
	    }
	    if (!user.getEmail().equals(existingUser.getEmail()) &&
	        userRepository.existsByEmail(user.getEmail())) {
	        throw new UserException.EmailAlreadyExistsException(user.getEmail());
	    }
	    if (!user.getPhone().equals(existingUser.getPhone()) &&
	        userRepository.existsByPhone(user.getPhone())) {
	        throw new UserException.PhoneAlreadyExistsException(user.getPhone());
	    }
	    
	    if (user.getUsername() != null) {
	        existingUser.setUsername(user.getUsername());
	    }
	    if (user.getPassword() != null) {
	        existingUser.setPassword(user.getPassword());
	    }
	    if (user.getEmail() != null) {
	        existingUser.setEmail(user.getEmail());
	    }
	    if (user.getPhone() != null) {
	        existingUser.setPhone(user.getPhone());
	    }
	    if (user.getIsActive() != null) {
	        existingUser.setIsActive(user.getIsActive());
	    }
	
	    return userRepository.save(existingUser);
    }

    @Override
    public User softDeleteUser(User user) {
    	if (!user.getIsActive()) {
    	    return user;
    	}
        user.setIsActive(false);
        return userRepository.save(user);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void hardDeleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }
    
    @Override
    public boolean deleteUserById(Integer userId) {
        if (userRepository.existsById(userId)) {
            hardDeleteUser(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getNormalUsers() {
        return userRepository.findAllByNormalRole();
    }

    @Override
    public User getUserById(Integer userId) throws UserException.UserNotFoundException {
        return userRepository.findById(userId).orElse(null);
    }
}