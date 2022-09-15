package Users;
import Exceptions.ResourceNotFoundException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourcePersistenceException;
import common.ResourceCreationResponse;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<UserResponse> getAllUsers() {
        return userDao.GetAllUsers()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(String id) {
        if (id == null || id.length() <= 0) {
            throw new InvalidRequestException("A non-empty id must be provided !");
        }
        try {
            UUID uuid = UUID.fromString(id);
            return userDao.findUserById(uuid)
                    .map(UserResponse::new)
                    .orElseThrow(ResourceNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid UUID string was provided.");
        }
    }

    public UserResponse getUserByUsername(String username) {
        if (username == null || username.length() <= 0) {
            throw new InvalidRequestException("A non-empty username must be provided");
        }
        try {
            return userDao.findUserByUsername(username)
                    .map(UserResponse::new)
                    .orElseThrow(InvalidRequestException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid username was provided");
        }
    }

    public UserResponse getUserByEmail(String email) {
        if (email == null || email.length() <= 0) {
            throw new InvalidRequestException("A email must be provided");
        }
        try {
            return userDao.findUserByEmail(email)
                    .map(UserResponse::new)
                    .orElseThrow(InvalidRequestException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid email was provided");
        }
    }

    public ResourceCreationResponse register(NewUserRequest newUser) {

        if (newUser == null) {
            throw new InvalidRequestException("Provided request payload was null.");
        }

        if (newUser.getGivenName() == null || newUser.getGivenName().length() <= 0 ||
                newUser.getSurname() == null || newUser.getSurname().length() <= 0) {

            throw new InvalidRequestException("A non-empty given name and surname must be provided");

        }

        if (newUser.getEmail() == null || newUser.getEmail().length() <= 0) {
            throw new InvalidRequestException("A non-empty email must be provided.");
        }

        if (newUser.getUsername() == null || newUser.getUsername().length() < 4) {
            throw new InvalidRequestException("A username with at least 4 characters must be provided.");
        }

        if (newUser.getPassword() == null || newUser.getPassword().length() < 8) {
            throw new InvalidRequestException("A password with at least 8 characters must be provided.");
        }

        if (userDao.isEmailTaken(newUser.getEmail())) {
            throw new ResourcePersistenceException("Resource not persisted! The provided email is already taken.");
        }

        if (userDao.isUsernameTaken(newUser.getUsername())) {
            throw new ResourcePersistenceException("Resource not persisted! The provided username is already taken.");
        }

        User userToPersist = newUser.extractEntity();
        String newUserId = userDao.save(userToPersist);
        return new ResourceCreationResponse(newUserId);


    }

    public ResourceCreationResponse updateUser(UpdateRequestBody updateUser, String idToSearchFor) {

        if (updateUser == null) {
            throw new InvalidRequestException("Provided request payload was null.");
        }
        if (updateUser.getEmail() != null && updateUser.getEmail().trim().length() > 3) {
            userDao.updateUserEmail(updateUser.getEmail(), idToSearchFor);
            // throw new InvalidRequestException("Email cannot be empty");
        }
        if (updateUser.getGiven_name() != null && updateUser.getGiven_name().trim().length() > 4) {
            // userDAO.updateUserGivenName(updateUser.getGiven_Name(), idToSearchFor);
            // throw new InvalidRequestException("A empty given name or surname");
        }
        if (updateUser.getSurname() != null && updateUser.getSurname().trim().length() > 4) {
            // userDAO.updateUserSurname(updateUser.getSurname(), idToSearchFor);
            // throw new InvalidRequestException("A empty given name or surname");
        }
        if (updateUser.getIs_active() == false || updateUser.getIs_active() == true) {
            // userDao.updateUserIs_Active(updateUser.getIs_active(), idToSearchFor);
            // throw new InvalidRequestException("A empty given name or surname");
        }

        String userToUpdate = updateUser.extractEntity().getEmail();
        System.out.println(userToUpdate); //! delete
        String updateEmail = userDao.updateUserEmail(userToUpdate, idToSearchFor);
        System.out.println("update: " + updateEmail);
        return new ResourceCreationResponse(updateEmail);
    }
}




