package Reimbursement;


import Exceptions.DataSourceException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ResourcePersistenceException;
import Users.UpdateRequestBody;
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
        String idToSearchFor = req.getParameter("id");
        ReimbursementResponse requester = (ReimbursementResponse) reimbursementSession.getAttribute("authUser");

        if (!requester.getReimbursement_id().equals("1") && !requester.getReimbursement_id().equals(idToSearchFor)) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester is not permitted to communicate with this endpoint")));
            return;
        }
        try {
            if (idToSearchFor == null) {
                List<ReimbursementResponse> allReimbursements = reimbursementService.getAllReimbursement();
                resp.getWriter().write(jsonMapper.writeValueAsString(allReimbursements));
            } else {
                ReimbursementResponse foundReimbursement = reimbursementService.getReimbursementById(idToSearchFor);
                resp.getWriter().write(jsonMapper.writeValueAsString(foundReimbursement));
            }
        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (ResourceNotFoundException e) {
            resp.setStatus(404);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
        } catch (DataSourceException e) {
            resp.setStatus(500);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");

        try {
            ResourceCreationResponse requestBody = reimbursementService
                    .register(jsonMapper.readValue(req.getInputStream(), NewReimbursementRequest.class));

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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        HttpSession reimbursementSession = req.getSession(false);
        resp.setContentType("application/json");
        if (reimbursementSession == null) {
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "log in")));
            return;
        }
        ReimbursementResponse reimbursementResponse = (ReimbursementResponse) reimbursementSession.getAttribute("loggedInReim");
        boolean w = reimbursementResponse.getReimbursement_id().equals("FINANCE MANAGER");
        if (!w) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "No communication")));
        }
        resp.setContentType("appplication/json");
        String toBeUpdated = req.getParameter("update");
        try {
            UpdateRequestBodyO requestBody = jsonMapper.readValue(req.getInputStream(), UpdateRequestBodyO.class);


            if (toBeUpdated.equals("reimbursement_id")) {
                ResourceCreationResponse generatedId = reimbursementService.updateReimbursement_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }


            if (toBeUpdated.equals("amount")) {
                ResourceCreationResponse generatedId = reimbursementService.updateAmount(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }

            if (toBeUpdated.equals("submitted")) {
                ResourceCreationResponse generatedId = reimbursementService.updateSubmitted(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }

            if (toBeUpdated.equals("resolved")) {
                ResourceCreationResponse generatedId = reimbursementService.updateResolved(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }

            if (toBeUpdated.equals("description")) {
                ResourceCreationResponse generatedId = reimbursementService.updateDescription(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }

            if (toBeUpdated.equals("author_id")) {
                ResourceCreationResponse generatedId = reimbursementService.updateAuthor_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("resolver_id")) {
                ResourceCreationResponse generatedId = reimbursementService.updateResolver_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
            if (toBeUpdated.equals("status_id")) {
                ResourceCreationResponse generatedId = reimbursementService.updateStatus_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));


            }
            if (toBeUpdated.equals("type_id")) {
                ResourceCreationResponse generatedId = reimbursementService.updateType_Id(requestBody);
                resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
            }
        } catch (InvalidRequestException | JsonMappingException e) {
            resp.setStatus(400);
            ErrorResponse errorResponse = new ErrorResponse(400, e.getMessage());
            resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
        } catch (DataSourceException e) {
            resp.setStatus(500);
            ErrorResponse errorResponse = new ErrorResponse(500, e.getMessage());
            resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getSession().invalidate();
    }
}


