package com.tiorico.apptiorico.services;

import java.util.List;
import java.util.Set;

import com.tiorico.apptiorico.models.User;
import com.tiorico.apptiorico.models.UserRol;

public interface UserService
{
	public User createUser(User user, Set<UserRol> userRoles) throws Exception;
	
	public User getUser(String username);
	
	public User updateUser(User user) throws Exception;
	
	public User softDeleteUser(User user);
	
	public User getUserById(Integer userId);

	public void hardDeleteUser(Integer userId);
	
	public boolean existsByPhone(String phone);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);
    
    public boolean deleteUserById(Integer userId);

	public List<User> getNormalUsers();
}