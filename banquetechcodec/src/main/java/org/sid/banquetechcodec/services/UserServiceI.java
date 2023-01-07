package org.sid.banquetechcodec.services;

import org.sid.banquetechcodec.data.UpdatePasswordF;
import org.sid.banquetechcodec.data.UserDtoRegister;
import org.sid.banquetechcodec.entities.User;
import org.sid.banquetechcodec.exception.InvalidTokenException;
import org.sid.banquetechcodec.exception.UnkownIdentifierException;
import org.sid.banquetechcodec.exception.UserAlreadyExistException;


public interface UserServiceI {
    public User saveUser(User user);
    public User findUserByName(String userName);
    public User findUseuByEmail(String email);
    public void addRoleToUser(String userName);
    public User registerUser(UserDtoRegister userDtoRegister) throws UserAlreadyExistException;
    public void encoder(UserDtoRegister source, User user);
    boolean checkIfUserExist(final String email);
    void sendRegistrationConfirmationEmail(final User user);
    boolean verifyUser(final String token) throws InvalidTokenException;
    User getUserByEmail(final String id) throws UnkownIdentifierException;
    public User addRoleToAdmin(String username);

    void forgottenPassword(final String userName) throws UnkownIdentifierException;
    void updatePassword(final String password, final String token) throws InvalidTokenException, UnkownIdentifierException;
    //void encodePassword(String password, User target);
    //boolean loginDisabled(final String username);
    User changePassword(Long id, UpdatePasswordF userForm);
}
