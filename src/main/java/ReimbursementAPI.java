import Users.UserDao;
import auth.AuthService;
import auth.AuthServlet;
import Users.UserService;
import Users.UserServlet;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class ReimbursementAPI {
    private static Logger logger = LogManager.getLogger(ReimbursementAPI.class);
    public static void main(String[]args) throws LifecycleException{
        String docBase = System.getProperty("java.io.tmpdir");
        Tomcat webServer = new Tomcat();

        webServer.setBaseDir(docBase);
        webServer.setPort(5000);
        webServer.getConnector();

        UserDao userDao = new UserDao();
        AuthService authService = new AuthService(userDao);
        UserService userService = new UserService(userDao);
        UserServlet userServlet = new UserServlet(userService);
        AuthServlet authServlet = new AuthServlet(authService);

        final String rootContext = "/ers";
        webServer.addContext(rootContext,docBase);
        webServer.addServlet(rootContext,"UserServlet",userServlet).addMapping("/users");
        webServer.addServlet(rootContext,"AuthServlet", authServlet).addMapping("/auth");

        webServer.start();
        webServer.getServer().await();
    }
}
