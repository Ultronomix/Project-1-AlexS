package Users;


import Exceptions.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.ConnectionUtility;
import Exceptions.DataSourceException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class UserDao {
    private static Logger logger = LogManager.getLogger(UserDao.class);
    private final String baseSelect = "SELECT au.id, au.username, au.email, au.password, au.given_name, au.surname, au.is_active, au.role_id " +
            "FROM ers_users au "+
            "JOIN user_roles ur "+
            "ON au.role_id = ur.id ";

    public List<User>GetAllUsers(){

        List<User> allUsers = new ArrayList<>();

        try (Connection conn  = ConnectionUtility.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(baseSelect);
            allUsers = mapResultSet(rs);

        } catch (SQLException e){
            System.err.println("something went wrong with database");
            e.printStackTrace();
        }
        return allUsers;
    }

    public Optional<User> findUserById (UUID id){
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
   public Optional<User> findUserByUsername (String username){
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
    public boolean isUsernameTaken(String username){
        return findUserByUsername(username).isPresent();
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
    public Optional<User> findUserByUsernameAndPassword(String username,String password){
        String sql = baseSelect + "WHERE au.username = ? AND au.password = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            e.printStackTrace();
            throw new DataSourceException(e);

        }


    }
    public boolean is_Active(String username, String password){
        System.out.println("is_active called");
        try {
            Optional<User>user = findUserByUsernameAndPassword(username,password);
            System.out.println(user.get().getIs_active());
            if (user.get().getIs_active()){
                return true;
            } else {
                return false;
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
            throw new ResourceNotFoundException();
        }
    }


    public String updateUserGiven_Name(String to, String id) {
        String sql = "update\"user\"set given_name =? where id = ?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, Boolean.parseBoolean(to));
            pstmt.setInt(2, Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            return "User active status updated to " + to + ".Rows affected =" + rs;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    public String updateUserSurname (String to, String id) {
        String sql = "update\"user\"set surname =? where id = ?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, Boolean.parseBoolean(to));
            pstmt.setInt(2, Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            return "User active status updated to " + to + ".Rows affected =" + rs;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    public String updateUserEmail (String to, String id){
        String sql = "update\"user\"set email =? where id = ?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1,Boolean.parseBoolean(to));
            pstmt.setInt(2,Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            return "User active status updated to "+ to +".Rows affected =" + rs;
        } catch (SQLException e){
            throw new DataSourceException(e);
        }
    }
    public String updateUserPassword (String to ,String id) {
        String sql = "update\"user\"set password =? where id = ?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, Boolean.parseBoolean(to));
            pstmt.setInt(2, Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            return "User active status updated to " + to + ".Rows affected =" + rs;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    //public String updateUserIs_Active (String to, String id) {
        //String sql = "update\"user\"set is_active =? where id = ?;";
       // try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
        //    PreparedStatement pstmt = conn.prepareStatement(sql);
         //   pstmt.setBoolean(1, Boolean.parseBoolean(to));
           // pstmt.setInt(2, Integer.parseInt(id));
           // int rs = pstmt.executeUpdate();
           // return "User active status updated to " + to + ".Rows affected =" + rs;
        //} catch (SQLException e) {
        //    throw new DataSourceException(e);
       // }
   // }
    public String updateUserRole_Id (String to ,String id) {
        String sql = "update\"user\"set role_id =? where id = ?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, Boolean.parseBoolean(to));
            pstmt.setInt(2, Integer.parseInt(id));
            int rs = pstmt.executeUpdate();
            return "User active status updated to " + to + ".Rows affected =" + rs;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    public  String save(User user){
        String sql = "INSERT INTO ers_users ( username, email,password,given_name,surname, is_active, role_id) " +
                "VALUES(?,?,?,?,?,true, '49ce5a1f-6b51-4e40-8e88-2bf9289292cd')";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"});
            //pstmt.setString(1,user.getId());
            pstmt.setString(1,user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getGiven_name());
            pstmt.setString(5, user.getSurname());
            System.out.println(pstmt);
            pstmt.executeUpdate();
System.out.println(pstmt);
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
           // user.setId(rs.getNString("id"));

        } catch (SQLException e){
            e.printStackTrace();
            //log("ERROR",e.getMessage());
        }
        //log("INFO","Successfully persisted new used id:" + user.getId());
        return user.getId();

    }

    private List<User> mapResultSet(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setGiven_name(rs.getString("given_name"));
            user.setSurname(rs.getString("surname"));
            user.setEmail(rs.getString("email"));
            user.setUsername(rs.getString("username"));
            user.setPassword("***********");
            user.setIs_active(rs.getBoolean("is_active"));
            user.setRole_id(rs.getString("role_id"));
            users.add(user);
        }
        return users;
    }
   // public void log(String level, String message) {
    //    try {
       //     File logFile = new File("logs/app.log");
         //   logFile.createNewFile();
         //   BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile));
         //   logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
         //   logWriter.flush();
       // } catch (IOException e) {
       //     throw new RuntimeException(e);
      //  }
  //  }
   // public boolean isIdVaild(String id){
    //    return findUserById(UUID.fromString(id)).isPresent();
    }

