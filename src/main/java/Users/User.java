package Users;

import java.util.Objects;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String given_name;
    private String surname;
    private String is_active;
    private String role_id;

    public User(){
        super();
    }
    public User(String id,String username,String email,String password,String given_name, String surname,String is_active, String role_id){
        this.id = id;
        this.username = username;
        this.email = email;
        this. password = password;
        this.given_name = given_name;
        this.surname = surname;
        this.is_active = is_active;
        this.role_id = role_id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(given_name, user.given_name) && Objects.equals(surname, user.surname) && Objects.equals(is_active, user.is_active) && Objects.equals(role_id, user.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, given_name, surname, is_active, role_id);
    }



    @Override
    public String toString() {
        return "User{" +
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

