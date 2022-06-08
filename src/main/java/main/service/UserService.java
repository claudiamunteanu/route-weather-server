package main.service;

import main.domain.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import main.persistence.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User newUser, String language) throws ServiceException {
        User user = userRepository.findByUsername(newUser.getUsername());
        if(user!=null){
            switch (language) {
                case "english" -> throw new ServiceException("There is already an user with this username!");
                case "romanian" -> throw new ServiceException("Deja există un cont cu acest username!");
            }
        }

        user = userRepository.findByEmail(newUser.getEmail());
        if(user!=null){
            switch (language) {
                case "english" -> throw new ServiceException("There is already an user with this email!");
                case "romanian" -> throw new ServiceException("Deja există un cont cu acest email!");
            }
        }

        String passw = newUser.getPassword();
        newUser.setPassword(passwordEncoder.encode(passw));
        newUser.setToken(RandomStringUtils.randomAscii(30));
        return userRepository.save(newUser);
    }


    public User login(User user, String language) throws ServiceException {
        try{
            User userFound = userRepository.findByEmail(user.getEmail());
            if (passwordEncoder.matches(user.getPassword(), userFound.getPassword())) {
                System.out.println("User gasit: " + userFound);
                return userFound;
            } else {
                String message = "";
                switch (language) {
                    case "english" -> message = "Invalid credentials!";
                    case "romanian" -> message = "Credențiale greșite!";
                }
                throw new ServiceException(message);
            }
        } catch (Exception ex){
            String message = "";
            switch (language) {
                case "english" -> message = "Invalid credentials!";
                case "romanian" -> message = "Credențiale greșite!";
            }
            throw new ServiceException(message);
        }
    }
}