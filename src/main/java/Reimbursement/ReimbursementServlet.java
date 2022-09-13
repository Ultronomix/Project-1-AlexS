package Reimbursement;


import Exceptions.DataSourceException;
import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Exceptions.ResourcePersistenceException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.ErrorResponse;

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
        ReimbursementResponse requester = (ReimbursementResponse) reimbursementSession.getAttribute("");// add here

        if (!isDirector(requester) && !requesterOwned(requester, idToSearchFor)) {
            resp.setStatus(403);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "request isnt permitted with endpoint")));
            return;
        }
        try {
            if (idToSearchFor == null) {
                List<ReimbursementResponse> allReimbursements = reimbursementService.getAllReimbursement();
                resp.addHeader("X-MY-CUSTOM-HEADER", "some random value");
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

            NewReimbursementRequest requestBody = jsonMapper.readValue(req.getInputStream(), NewReimbursementRequest.class);
            ResourceCreationResponse responseBody = ReimbursementService.register(requestBody);
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
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        resp.setContentType("application/json");
        HttpSession loggedInReimbursementSession = req.getSession(false);

        if (loggedInReimbursementSession == null){
            resp.setStatus(401);
            resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401,"please log in")));
            return;
        }
        ReimbursementResponse loggedInReminbursement = (ReimbursementResponse) loggedInReimbursementSession.getAttribute("loggedInReminbursement");
        boolean w = loggedInReminbursement.getRoleName().equals("manager");
        if (!w)){
    resp.setStatus(403);
    resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403,"request isnt permitted to communicate with endpoint")));
    return;
        }

        resp.setContentType("application/json");
String toBeUpdated = req.getParameter("update");

try {
    UpdateRequestBody requestBody = jsonMapper.readValue(req.getInputStream(), UpdateRequestBody.class);

    if (toBeUpdated.equals("reimbursement_id")) {
            ResponseString generatedId = reimbursementService.updateReimbursement_Id(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }

        if (toBeUpdated.equals("amount")) {
            ResponseString generatedId = reimbursementService.updateAmount(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }

        if (toBeUpdated.equals("submitted")) {
            ResponseString generatedId = reimbursementService.updateSubmitted(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }

        if (toBeUpdated.equals("resolved")) {
            ResponseString generatedId = reimbursementService.updateResolved(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }

        if (toBeUpdated.equals("description")) {
            ResponseString generatedId = reimbursementService.updateDescription(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }

        if (toBeUpdated.equals("author_id")) {
            ResponseString generatedId = reimbursementService.updateAuthor_Id(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }
        if (toBeUpdated.equals("resolver_id")) {
            ResponseString generatedId = reimbursementService.updateResolver_Id(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }
        if (toBeUpdated.equals("status_id")) {
            ResponseString generatedId = reimbursementService.updateStatus_Id(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));


        }
        if (toBeUpdated.equals("type_id")) {
            ResponseString generatedId = reimbursementService.updateType_Id(requestBody);
            resp.getWriter().write(jsonMapper.writeValueAsString(generatedId));
        }
}
}
    }
}

