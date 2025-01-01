package com.square_claimer.user_data.controller;

import com.square_claimer.user_data.model.user.AuthKey;
import com.square_claimer.user_data.model.user.User;
import com.square_claimer.user_data.service.ServiceException;
import com.square_claimer.user_data.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.session.StandardSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value={"/api/user"})
public class UserRestController extends BaseRestController{

    private static final String USER_ID = "userID";

    @Autowired
    UserService service;

    @GetMapping("/all")
    public Iterable<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/get")
    public User getUserInfo(HttpSession session) throws ServiceException{
        System.out.println(session.getAttribute(USER_ID));
        if(session.getAttribute(USER_ID) == null) throw new ServiceException("You are not logged in, log in te request user info");
        User user = service.getById((long)session.getAttribute(USER_ID));
        if(user == null) throw new ServiceException("Invalid user ID");
        return user;
    }

    @PostMapping("/add")
    public Iterable<User> add(@Valid @RequestBody User user){
        service.addUser(user);
        return this.getAll();
    }

    @PostMapping("/login/credentials")
    public LoginResponse loginByCredentials(@Valid @RequestBody LoginCreds loginCreds, HttpSession session) throws ServiceException {
        User user = service.login(loginCreds.username, loginCreds.password);
        if(session.getAttribute(USER_ID) == null){
            session.setAttribute(USER_ID, user.getId());
            return new LoginResponse("200","Successfully logged in",  service.getAuthKeyForUser(user).getValue());
        }
        else throw new ServiceException("You are already logged in, log out to log in with a different user");
    }

    @PostMapping("/login/key")
    public LoginResponse loginByAuthKey(@Valid @RequestBody LoginKey loginKey, HttpSession session) throws ServiceException {
        AuthKey authKey = service.getAuthKey(loginKey.key);
        if(authKey == null) throw new ServiceException("Invalid authentication key");
        User user = authKey.getUser();
        if(user == null) throw new ServiceException("Invalid authentication key");
        if(session.getAttribute(USER_ID) == null){
            session.setAttribute(USER_ID, user.getId());
            return new LoginResponse("200", "Successfully logged in", authKey.getValue());
        }
        else throw new ServiceException("You are already logged in, log out to log in with different user");
    }

    @GetMapping("/logout")
    public LogoutResponse logout(HttpSession session){
        if(session.getAttribute(USER_ID) == null) throw new ServiceException("You are not logged in");
        session.removeAttribute(USER_ID);
        return new LogoutResponse("200", "Logged out successfully");
    }

    /*
    --------------------------------------------------------------------------------------------
    Request / Response body's
    --------------------------------------------------------------------------------------------
     */

    @Getter @Setter
    private static class LoginCreds{
        @NotNull(message = "user.name.missing")
        @NotBlank(message = "user.name.missing")
        private String username;

        @NotNull(message = "user.password.missing")
        @NotBlank(message = "user.password.missing")
        private String password;
    }

    @Getter @Setter
    private static class LoginKey{
        @NotNull(message = "auth.key.missing")
        @NotBlank(message = "auth.key.missing")
        private String key;
    }

    @Getter @Setter
    @AllArgsConstructor
    private static class LoginResponse{
        private String response;
        private String message;
        private String key;
    }

    @Getter @Setter
    @AllArgsConstructor
    private static class LogoutResponse{
        private String response;
        private String message;
    }
}
