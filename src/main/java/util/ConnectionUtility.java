package util;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtility {
    private static ConnectionUtility connectionUtility;
    public static Properties dbProps = new Properties();

    private ConnectionUtility(){
        try {
            Class.forName("org.postgresql.Driver");
            dbProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db-config.properties"));
        }catch (IOException e){
            throw new RuntimeException("could not read from properties file",e);
        }catch (ClassNotFoundException e){
            throw new RuntimeException("failed to load PostGreSQL driver",e);
        }
    }

    public static ConnectionUtility getInstance(){
        if (connectionUtility == null){
            connectionUtility = new ConnectionUtility();
        }
        return connectionUtility;
    }
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbProps.getProperty("db-url"),dbProps.getProperty("db-username"), dbProps.getProperty("db-password"));
    }
}


