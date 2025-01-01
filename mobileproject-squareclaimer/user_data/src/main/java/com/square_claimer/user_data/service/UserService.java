package com.square_claimer.user_data.service;

import com.square_claimer.user_data.model.team.Team;
import com.square_claimer.user_data.model.user.AuthKey;
import com.square_claimer.user_data.model.user.AuthKeyRepository;
import com.square_claimer.user_data.model.user.User;
import com.square_claimer.user_data.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthKeyRepository authKeyRepository;
    @Autowired
    private TeamService teamService;

    public User addUser(User user){
        Team defaultTeam = teamService.getDefault();
        user.setTeam(defaultTeam);
        User newUser = userRepository.save(user);
        teamService.addUserToTeam(newUser, defaultTeam);
        return newUser;
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getById(long id) throws ServiceException{
        return userRepository.getUserById(id);
    }

    public User login(String username, String password) throws ServiceException{
        User user = userRepository.getUserByUserName(username);
        if(user == null) throw new ServiceException("Username and/or password is wrong");
        if(new BCryptPasswordEncoder().matches(password, user.getPassword())){
            return user;
        }
        else throw new ServiceException("Username and/or password is wrong");
    }

    public AuthKey getAuthKey(String value){
        return authKeyRepository.getByValue(value);
    }

    public AuthKey getAuthKeyForUser(User user){
        try{
            AuthKey authKey =  authKeyRepository.getByUser(user);
            if(authKey == null || authKey.getLifeTime() <= System.currentTimeMillis()) return createNewAuthKey(user);
            else return authKey;
        }
        catch (Exception e){
            return createNewAuthKey(user);
        }
    }

    public AuthKey createNewAuthKey(User user){
        authKeyRepository.deleteByUser(user);
        return authKeyRepository.save(AuthKey.create(user));
    }

}
