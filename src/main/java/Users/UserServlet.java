package Users;
import Exceptions.DataSourceException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ResourcePersistenceException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ErrorResponse;
import common.ResourceCreationResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.List;

public class UserServlet extends HttpServlet {
    private final UserService userService;

    public UserServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession userSession = req.getSession(false);
        if (userSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated, please log in")));
            return;
        }

        String idToSearchFor = req.getParameter("id");

        UserResponse requester = (UserResponse) userSession.getAttribute("authUser");
        if (!requester.getRole_id().equals("49ce5a1f-6b51-4e40-8e88-2bf9289292cd") && !requester.getRole_id().equals("b02a2f8a-36f2-4193-bcbd-2058d7628c31")){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester is not permitted to communicate with this endpoint")));
            return;
        }

        try {
            if (idToSearchFor == null) {
                List<UserResponse> allUsers = userService.getAllUsers();
                resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
            } else {
                UserResponse foundUser = userService.getUserById(idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
            }

        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));


        } catch (ResourceNotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));


        } catch (DataSourceException e) {
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        try {

            NewUserRequest requestBody = jsonMapper.readValue(req.getInputStream(), NewUserRequest.class);
            ResourceCreationResponse responseBody = userService.register(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));

        } catch (InvalidRequestException | JsonMappingException e) {

            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

        } catch (ResourcePersistenceException e) {

            resp.setStatus(409);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));

        } catch (DataSourceException e) {

            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException , IOException{
        HttpSession userSession = req.getSession(false);
        ObjectMapper jsonMapper = new ObjectMapper();
        UserResponse loggedInUser =(UserResponse) userSession.getAttribute("loggedInUser");

        if (userSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401,"please log in")));
        return;
    }
        UserResponse loggedInUser1 = (UserResponse) userSession.getAttribute("loggedInUser");
        boolean w = loggedInUser1.getRole_id().equals("admin");
        if((!w)){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403,"Requester is not permitted to communicate")));
            return;
        }

        resp.setContentType("application/json");
        String toBeUpdated = req.getParameter("update");
        try {
            UpdateRequestBody requestBody = jsonMapper.readValue(req.getInputStream(),UpdateRequestBody.class);

            if (toBeUpdated.equals("given_name")){
                ResourceCreationResponse generatedId = userService.updateGiven_name(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("surname")){
                ResourceCreationResponse generatedId = userService.updateSurname(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("email")){
                ResourceCreationResponse generatedId = userService.updateEmail(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("password")){
                ResourceCreationResponse generatedId = userService.updatePassword(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("is_active")){
                ResourceCreationResponse generatedId = userService.updateIs_Active(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("role_id")){
                ResourceCreationResponse generatedId = userService.updateRole_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
        }catch (InvalidRequestException | JsonMappingException e){
            resp.setStatus(400);
            ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
            resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
        } catch (DataSourceException e){
            resp.setStatus(500);
            ErrorResponse errorResponse = new ErrorResponse(500, e.getMessage());
            resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
        }
        }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
    }
}



