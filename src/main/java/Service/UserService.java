package Service;

import Users.User;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    public User createUser(User user){
        return userRepository.create(user);
    }
}
