package com.tiorico.apptiorico.exceptions;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserException(String message) {
        super(message);
    }

    public static class UserNotFoundException extends UserException {
        public UserNotFoundException(String username) {
            super("User with username '" + username + "' not found.");
        }
    }

    public static class PhoneAlreadyExistsException extends UserException {
        public PhoneAlreadyExistsException(String phone) {
            super("The phone number '" + phone + "' is already in use.");
        }
    }

    public static class EmailAlreadyExistsException extends UserException {
        public EmailAlreadyExistsException(String email) {
            super("The email '" + email + "' is already in use.");
        }
    }

    public static class UsernameAlreadyExistsException extends UserException {
        public UsernameAlreadyExistsException(String username) {
            super("The username '" + username + "' is already in use.");
        }
    }

    public static class UserNotActiveException extends UserException {
        public UserNotActiveException(String username) {
            super("User with username '" + username + "' is not active.");
        }
    }
}