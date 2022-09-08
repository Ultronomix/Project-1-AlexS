package Users;


import util.ConnectionUtility;
import Exceptions.DataSourceException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserDao {
    private final String baseSelect = "SELECT au.user_id, au.username, au.email, au.password, au.given_name, au.surname, au.is_active, au.role_id" +
            "FROM app_users au"+
            "JOIN user_roles ur"+
            "ON au.role_id = ur.id ";

    public List<User>GetAllUsers(){
        List<User> allUsers = new ArrayList<>();
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(baseSelect);

            allUsers = mapResultSet(rs);

        } catch (SQLException e){
            System.err.println("something went wrong with database");
            e.printStackTrace();
        }
        return allUsers;
    }

    public Optional<User> findUserById(UUID id){
        String sql = baseSelect + "WHERE au.id = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataSourceException(e);
        }
    }
   public Optional<User> findUserByUsername(String username){
        String sql = baseSelect + "WHERE au.username = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            throw new DataSourceException(e);
        }

    }
    public Optional<User> findUserByEmail(String email){
        String sql = baseSelect + "WHERE au.email = ?";

        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            throw new DataSourceException(e);
        }

    }

    public boolean isEmailTaken(String email){
        return findUserByEmail(email).isPresent();
    }
    public Optional<User> findUserbyUsernameandPassword(String username,String password){
        String sql = baseSelect + "WHERE au.username = ? AND au.password = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            throw new DataSourceException(e);
        }


    }
    public  String save(User user){
        String sql = "INSERT INTO app_users (given_name, surname, email, username, password, role_id) " +
                "VALUES(?,?,?,?,?,?,'5a2e0415-ee08-440f-ab8a-778b37ff6874')";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, user.getGiven_name());
            pstmt.setString(2, user.getSurname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPassword());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            user.setid(rs.getNString("id"));

        } catch (SQLException e){
            log("ERROR",e.getMessage());
        }
        log("INFO","Successfully persisted new used id:" + user+getId());/// fix this
        return user.getId();

    }

    private List<User> mapResultSet(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setid(rs.getString("id"));
            user.setGiven_name(rs.getString("given_name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword("***********"); // done for security purposes
            user.setRole_id(new Role(rs.getString("role_id"), rs.getString("role")));
            users.add(user);
        }
        return users;
    }
    public void log(String level, String message) {
        try {
            File logFile = new File("logs/app.log");
            logFile.createNewFile();
            BufferedWriter = new BufferedWriter(new FileWriter(logFile));
            logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
