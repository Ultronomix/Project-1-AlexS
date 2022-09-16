import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import Users.UserDao;
import auth.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import Exceptions.AuthenticationException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Users.UserResponse;
import Users.User;



public class AuthServiceTest {
    AuthService sut;
    UserDao mockUserDao;
    @BeforeEach
    public  void Setup(){
       // mockUserDao= Mockito.mock((UserDao.class);
        sut = new AuthService(mockUserDao);
    }
    @AfterEach
    public  void cleanUp(){
        Mockito.reset(mockUserDao);
    }

    @Test
    public void test_authenticationReturnsSuccessfully_givenValidAndKnownCredentials () {

        //* Arrange
       // Credentials credentialsStub = new Credentials("valid", "credentials");
      //  User userStub = new User("some-id", "valid", "valid123@gmail.com", "credentials", "Valid", "test", true);
       // when(mockUserDao.findUserByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(userStub));
       // when(mockUserDao.isActive(anyString(), anyString())).thenReturn(true);
      //  UserResponse expectedResult = new UserResponse(userStub);

        //* Act
      //  UserResponse actualResult = sut.authenticate(credentialsStub);

        //* Assert
       // assertNotNull(actualResult);
      //  assertEquals(expectedResult, actualResult); //* Objects being compared needs to have a .equals method
    }

    @Test
    public void test_authentication_throwsInvalidRequestException_givenTooShortPassword() {

        //* Arrange
    //    Credentials credentialsStub = new Credentials("invalid", "cred");

        //* Act & Assert
        assertThrows(InvalidRequestException.class, () -> {
     //       sut.authenticate(credentialsStub);
        });

        verify(mockUserDao, times(0)).findUserByUsernameAndPassword(anyString(), anyString());
    }

    @Test
    public void test_authentication_throwsAuthenticationException_givenTooShortUsername() {

        //* Arrange
    //    Credentials credentialsStub = new Credentials("x", "credential");
        //* Act & Assert
        assertThrows(InvalidRequestException.class, () ->{
      //      sut.authenticate(credentialsStub);
        });

        verify(mockUserDao, times(0)).findUserByUsernameAndPassword(anyString(), anyString());
    }

    @Test
    public void test_authentication_throwsInvalidRequestException_givenNullCredential () {
        // Arrange
       // Credentials credentialsStub = null;

        //act
        assertThrows(InvalidRequestException.class, () -> {
         //   sut.authenticate(credentialsStub);
        });

        // Assert
       // verify(mockUserDAO, times(0)).findUserByUsernameAndPassword(anyString(), anyString());
    }

    @Test
    public void test_authenticate_throwsAuthenticationException_givenValidUnknownCredentials() {

        // Arrange
     //   Credentials credentialsStub = new Credentials("unknown", "credentials");
        when(mockUserDao.findUserByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());

        // Act
        assertThrows(AuthenticationException.class, () -> {
        //    sut.authenticate(credentialsStub);
        });

        // Assert
        verify(mockUserDao, times(1)).findUserByUsernameAndPassword(anyString(), anyString());

    }

    @Test
    public void test_authentication_throwsInvalidRequestException_givenInActiveUser () {

        //Credentials credentialsStub = new Credentials("valid", "credentials");
      //  User userStub = new User("some-id", "valid", "valid123@gmail.com", "credentials", "Valid", "test", false);
       // when(mockUserDao.findUserByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(userStub));
       // when(mockUserDao.isActive(anyString(), anyString())).thenReturn(false);

        assertThrows(InvalidRequestException.class, () -> {
        //    sut.authenticate(credentialsStub);
        });

       // verify(mockUserDao, times(1)).isActive("valid", "credentials");
    }

    @Test
    public void test_authenticate_throwsAuthenticationException_givenValidUnknownCredentialsToIsActive () {

      //  Credentials credentialsStub = new Credentials("unknown", "credential");
        when(mockUserDao.findUserByUsernameAndPassword(anyString(), anyString())).thenThrow(ResourceNotFoundException.class);

        assertThrows(AuthenticationException.class, () -> {
           // sut.authenticate(credentialsStub);
        });

      //  verify(mockUserDao, times(1)).isActive(anyString(), anyString());
    }
}

