package org.sid.banquetechcodec.services;

import org.sid.banquetechcodec.dao.RoleRepository;
import org.sid.banquetechcodec.dao.SecureTokenRepository;
import org.sid.banquetechcodec.dao.UserRepository;
import org.sid.banquetechcodec.data.UpdatePasswordF;
import org.sid.banquetechcodec.data.UserDtoRegister;
import org.sid.banquetechcodec.entities.Role;
import org.sid.banquetechcodec.entities.SecureToken;
import org.sid.banquetechcodec.entities.User;
import org.sid.banquetechcodec.exception.InvalidTokenException;
import org.sid.banquetechcodec.exception.UnkownIdentifierException;
import org.sid.banquetechcodec.exception.UserAlreadyExistException;
import org.sid.banquetechcodec.security.AccountVerificationEmailContext;
import org.sid.banquetechcodec.security.ForgotPasswordEmailContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.mail.MessagingException;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserServiceI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    SecureTokenRepository secureTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${site.base.url.https}")
    private String baseURL;

    @Override
    public User saveUser(User user) {
        String hasHPW = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hasHPW);
        return userRepository.save(user);
    }

    @Override
    public User findUserByName(String userName) {
        User user = userRepository.findByUsername(userName);
        return user;
    }

    @Override
    public User findUseuByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public void addRoleToUser(String userName) {
        User user = findUserByName(userName);
        Role role = new Role(null,"USER");
        roleRepository.save(role);
        user.addRoleToUser(role);
    }

    @Override
    public User registerUser(UserDtoRegister userDtoRegister) throws UserAlreadyExistException {
        if(checkIfUserExist(userDtoRegister.getEmail())){
            throw new UserAlreadyExistException("User already exists for this email");
        }
        if(!userDtoRegister.getPassword().equals(userDtoRegister.getRepassword()))
            throw new RuntimeException("you must confirm your password");
        if(!userDtoRegister.getEmail().contains("@"))
            throw new RuntimeException("you email is incorrect");
        if(userDtoRegister.getEmail() == null|| userDtoRegister.getPassword() == null || userDtoRegister.getRepassword() == null || userDtoRegister.getUsername() == null)
            throw new RuntimeException("vous avez laisse un champ vide");
        if(userDtoRegister.getEmail() == "" || userDtoRegister.getPassword() == "" || userDtoRegister.getRepassword() == "" || userDtoRegister.getUsername() == "")
            throw new RuntimeException("vous avez laisse un champ vide");

        User user = new User();
        BeanUtils.copyProperties(userDtoRegister,user);
        encoder(userDtoRegister,user);
        user.setAccountVerified(true);
        userRepository.save(user);
        addRoleToUser(userDtoRegister.getUsername());
        sendRegistrationConfirmationEmail(user);
        System.out.println("le mode  nouveau avec moi");
        return user;
    }

    @Override
    public void encoder(UserDtoRegister source, User user) {
        user.setPassword(bCryptPasswordEncoder.encode(source.getPassword()));
    }

    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email)!=null?true:false;
    }

    @Override
    public void sendRegistrationConfirmationEmail(User user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyUser(String token) throws InvalidTokenException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        User user = userRepository.getOne(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            return false;
        }
        user.setAccountVerified(false);
        userRepository.save(user); // let's same user details

        // we don't need invalid password now
        secureTokenService.removeToken(secureToken);
        return true;
    }

    @Override
    public User getUserByEmail(String id) throws UnkownIdentifierException {
        User user= userRepository.findByEmail(id);
        if(user == null || user.isAccountVerified()==true){
            // we will ignore in case account is not verified or account does not exists
            throw new UnkownIdentifierException("unable to find account or account is not active");
        }
        return user;
    }


    @Override
    public User addRoleToAdmin(String username) {
        User user = findUserByName(username);
        Role role = new Role(null,"ADMIN");
        roleRepository.save(role);
        user.addRoleToUser(role);
        userRepository.save(user);
        return user;
    }


    @Override
    public void forgottenPassword(String email) throws UnkownIdentifierException {
        if(!email.contains("@"))
            throw new RuntimeException("you email is incorrect");
        if(email == null)
            throw new RuntimeException("vous avez laisse un champ vide");
        if(email == "")
            throw new RuntimeException("vous avez laisse un champ vide");
        User user= getUserByEmail(email);
        sendResetPasswordEmail(user);
    }

    @Override
    public void updatePassword(String password, String token) throws InvalidTokenException, UnkownIdentifierException {
        SecureToken secureToken = secureTokenService.findByToken(token);
        if(Objects.isNull(secureToken) || !StringUtils.equals(token, secureToken.getToken()) || secureToken.isExpired()){
            throw new InvalidTokenException("Token is not valid");
        }
        User user = userRepository.getOne(secureToken.getUser().getId());
        if(Objects.isNull(user)){
            throw new UnkownIdentifierException("unable to find user for the token");
        }
        secureTokenService.removeToken(secureToken);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    /*@Override
    public void encodePassword(String password, User target) {
        target.setPassword(bCryptPasswordEncoder.encode(password));
    }*/

    @Override
    public User changePassword(Long id, UpdatePasswordF userForm)  {
        User user = userRepository.findById(id).orElse(null);
        if(!bCryptPasswordEncoder.matches(userForm.getPassword(),user.getPassword())){
            throw new RuntimeException("votre mot de passe est incorrecte");
        }else {
            if(!userForm.getNewpassword().equals(userForm.getConfirmpassword())){
                throw new RuntimeException("vous avez mal confirmé votre mot de passe");
            }

        }
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getNewpassword()));
        userRepository.save(user);
        return user;
    }


    protected void sendResetPasswordEmail(User user) {
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUser(user);
        secureTokenRepository.save(secureToken);
        ForgotPasswordEmailContext emailContext = new ForgotPasswordEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL, secureToken.getToken());
        try {
            emailService.sendMail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
