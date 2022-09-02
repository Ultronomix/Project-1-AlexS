package Repository;

import Models.User;
import util.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    @Override
    public User create(User user) {
        String sql = "insert into users(User_id,username,email,password,given_name,surname,is_active,role_id) values(?,?,?,?,?,?,?,?)";
        try (Connection connection = ConnectionUtility.getInstance()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUser_id());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getGiven_name());
            stmt.setString(6, user.getIs_active());
            stmt.setString(7, user.getRole_id());

            if (stmt.executeUpdate() == 1) {
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    }

