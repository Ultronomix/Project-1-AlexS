package auth;

import Exceptions.AuthenticationException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Users.UserDao;
import Users.UserResponse;



public class AuthService {

    private final UserDao userDAO;

    public AuthService(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    public UserResponse authenticate(Credentials credentials){
System.out.println("authenticate call");
        if (credentials == null || credentials.getUsername().trim().length()<4 || credentials.getPassword().trim().length()<6){
            throw new InvalidRequestException("The provided credentials object was found to be null.");
        }
        try {
            boolean active = userDAO.is_Active(credentials.getUsername(), credentials.getPassword());
System.out.println(active);
            UserResponse user = userDAO.findUserByUsernameAndPassword(credentials.getUsername(),credentials.getPassword())
                    .map(UserResponse :: new).orElseThrow(AuthenticationException::new);

            if (active == true){
                return user;
            } else {
                throw new InvalidRequestException("user is inactive");
            }
        }catch (ResourceNotFoundException e){
            throw new AuthenticationException();
        }
    }
}



