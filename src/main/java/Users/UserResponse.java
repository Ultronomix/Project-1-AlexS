package Users;

public class UserResponse {

   private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String is_active;
    private String role_id;

    public UserResponse(User subject){
        this.user_id = subject.getUser_id();
        this.given_name = subject.getUsername();
        this.email=subject.getEmail();
        this.password=subject.getPassword();
        this.given_name=subject.getGiven_name();
        this.surname=subject.getSurname();
        this.is_active=subject.getIs_active();
        this.role_id=subject.getRole_id();
    }

    public String getUser_id() {
        return user_id;
    }

    public UserResponse setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getIs_active() {
        return is_active;
    }

    public UserResponse setIs_active(String is_active) {
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
    public String toString() {
        return "UserResponse{" +
                "user_id='" + user_id + '\'' +
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
