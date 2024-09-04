package com.tiorico.apptiorico.users;

import java.util.Set;

import com.tiorico.apptiorico.userRoles.UserRol;

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
}