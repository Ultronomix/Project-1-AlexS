package Users;

import java.util.Objects;

public class UserResponse {

   private String id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private  Boolean is_active;
    private String role_id;

    public UserResponse(User subject){
        this.id = subject.getId();
        this.username = subject.getUsername();
        this.email=subject.getEmail();
        this.password=subject.getPassword();
        this.given_name=subject.getGiven_name();
        this.surname=subject.getSurname();
        this.is_active=subject.getIs_active();
        this.role_id=subject.getRole_id();
    }

    public String getId() {
        return id;
    }

    public UserResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserResponse setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGiven_name() {
        return given_name;
    }

    public UserResponse setGiven_name(String given_name) {
        this.given_name = given_name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public UserResponse setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public UserResponse setIs_active( Boolean is_active) {
        this.is_active = is_active;
        return this;
    }

    public String getRole_id() {
        return role_id;
    }

    public UserResponse setRole_id(String role_id) {
        this.role_id = role_id;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserResponse that = (UserResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(given_name, that.given_name) && Objects.equals(surname, that.surname) && Objects.equals(is_active, that.is_active) && Objects.equals(role_id, that.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, given_name, surname, is_active, role_id);
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user_id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", given_name='" + given_name + '\'' +
                ", surname='" + surname + '\'' +
                ", is_active='" + is_active + '\'' +
                ", role_id='" + role_id + '\'' +
                '}';
    }
}
