package Users;

import java.util.Objects;

public class User {
    private String user_id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String is_active;
    private String role_id;

    public User(){

    }
    public User(String user_id, String username, String email, String password, String given_name, String surname, String is_active, String role_id) {
    }
    public String getUser_id() {
        return user_id;
    }

    public User setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getGiven_name() {
        return given_name;
    }

    public User setGiven_name(String given_name) {
        this.given_name = given_name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getIs_active() {
        return is_active;
    }

    public User setIs_active(String is_active) {
        this.is_active = is_active;
        return this;
    }

    public String getRole_id() {
        return role_id;
    }

    public User setRole_id(String role_id) {
        this.role_id = role_id;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(given_name, user.given_name) && Objects.equals(surname, user.surname) && Objects.equals(is_active, user.is_active) && Objects.equals(role_id, user.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, email, password, given_name, surname, is_active, role_id);
    }

    @Override
    public String toString() {
        return "User{" +
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

