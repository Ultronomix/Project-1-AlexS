package Controller;

import Models.User;
import Service.UserService;

import java.util.logging.Handler;

public class UserController {
    private final UserService userService = new UserService();
    public Handler createNewUser = ctx -> {
        User newUser = ctx.bodyAsClass(User.class);
    try{
        ctx.json(userService.createUser(newUser));
    }catch(NullPointerException e){
        ctx.status(400).result("Could not create user, try again later");
    }
};
}