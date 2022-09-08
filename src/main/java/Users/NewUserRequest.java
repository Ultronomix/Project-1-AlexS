package Users;

import org.omg.CORBA.Request;

import java.util.UUID;

public class NewUserRequest implements Request<User> {

    private String givenName;
    private String surname;
    private String email;
    public String username;
    public String password;

    public String getGivenName() {
        return givenName;
    }

    public NewUserRequest setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public NewUserRequest setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public NewUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public NewUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public NewUserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "NewUserRequest{" +
                "givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public User extractEntity(){
        User extractedEntity = new User();
        extractedEntity.setid(UUID.randomUUID().toString());
        extractedEntity.setGiven_name(this.givenName);
        extractedEntity.setSurname(this.surname);
        extractedEntity.setEmail(this.email);
        extractedEntity.setUsername(this.username);
        extractedEntity.setPassword(this.password);
        return extractedEntity;

}

}
