package Users;

import Users.User;
import util.ConnectionUtility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public List<User> userList = new ArrayList<>();
    try{
        Connection conn = ConnectionUtility.getInstance().getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from user_table");
        while (rs.next()){
            User user = new User();
            user.setUser_id(rs.getString("user_id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));

        }


    }
}

