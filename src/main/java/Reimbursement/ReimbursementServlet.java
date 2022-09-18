package Reimbursement;


import Exceptions.*;
import Users.UpdateRequestBody;
import Users.UserResponse;
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

public class ReimbursementServlet extends HttpServlet {
    private final ReimbursementService reimbursementService;

    public ReimbursementServlet(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession reimbursementSession = req.getSession(false);
        if (reimbursementSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester is not authenticated with the system,log in")));
            return;
        }

        UserResponse requester = (UserResponse) reimbursementSession.getAttribute("authUser");

        String idToSearchFor = req.getParameter("id");
        String statusToSearchFor = req.getParameter("status");
        String typeToSearchFor = req.getParameter("type");


        if (!requester.getRole_id().equals("49ce5a1f-6b51-4e40-8e88-2bf9289292cd") && !requester.getRole_id().equals(idToSearchFor)) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester is not permitted to communicate with this endpoint")));
            return;
        }
        try {
            if (idToSearchFor == null && statusToSearchFor == null && typeToSearchFor == null) {
                List<ReimbursementResponse> allReimbursement = reimbursementService.getAllReimbursement();
                resp.getWriter().write(jsonMapper.writeValueAsString(allReimbursement));
            }
            if (idToSearchFor != null) {
                ReimbursementResponse foundRequest = reimbursementService.getReimbursementById(idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundRequest));
            }
            if (statusToSearchFor != null) {
                List<ReimbursementResponse> foundStatus = reimbursementService.getReimbursementByStatus(statusToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundStatus));
            }
            if (typeToSearchFor != null) {
                List<ReimbursementResponse> foundStatus = reimbursementService.getReimbursementByType(typeToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundStatus));
            }

        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (ResourceNotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
        } catch (DataSourceException e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        HttpSession reimbursementSession = req.getSession(false);
        if (reimbursementSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester not authenticated with server,log in")));
            return;
        }
        UserResponse requester =(UserResponse) reimbursementSession.getAttribute("authUser");
        if (!requester.getRole_id().equals("49ce5a1f-6b51-4e40-8e88-2bf9289292cd")){
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403,"Requester not permitted to communicate with this endpoint")));
            return;
        }
        try {
            NewReimbursementRequest requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            requestBody.setReimbursement_id(requester.getId());
            ResourceCreationResponse responseBody = reimbursementService.create(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(requestBody));
        }catch (InvalidRequestException | JsonMappingException e){
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400,e.getMessage())));
            e.printStackTrace();
        }catch (ResourceNotFoundException e){
            resp.setStatus(409);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
        } catch (DataSourceException e){
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }

      //  resp.getWriter().write("Post to reimbursement work");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        HttpSession reimbursementSession = req.getSession(false);
try {
        if (reimbursementSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "log in")));
            return;
        }
        UserResponse requester = (UserResponse) reimbursementSession.getAttribute("authUser");
        String idToSearchFor = req.getParameter("id");
        String reimbursementToSearchFor = req.getParameter("reimbursementId");

        if ((!requester.getRole_id().equals("b02a2f8a-36f2-4193-bcbd-2058d7628c31") && !requester.getRole_id().equals("49ce5a1f-6b51-4e40-8e88-2bf9289292cd")) && !requester.getId().equals(idToSearchFor)) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with endpoint")));
            return;
        }
        ResourceCreationResponse responseBody =
                reimbursementService.updateReimbursement(jsonMapper.readValue(req.getInputStream(), UpdateRequestBodyO.class), reimbursementToSearchFor, requester.getId());
        resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
    } catch (InvalidRequestException | JsonMappingException e){
    resp.setStatus(400);
    resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

    } catch (AuthenticationException e){
    resp.setStatus(400);
    resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));

    } catch (DataSourceException e){
    resp.setStatus(500);
    resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
    }
resp.getWriter().write("\nPut to /reimbursement end");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
       req.getSession().invalidate();
        }
}


