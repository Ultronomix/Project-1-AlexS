package Users;

import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public List<UserResponse> getAllUsers(){
        return userDao.getAllUsers()
                .stream()
                .map(UserResponse:: new)
                .collect(Collectors.toList());
    }
    public UserResponse getUserById(String id){
        if (id == null || id.length() <=0){
            throw new InvalidRequestException("A non-empty id must be provided !");
        } catch (IllegalArgumentException e){
            throw new InvalidRequestException("An invalid UUID string was proivded.");
        }
    }

}
