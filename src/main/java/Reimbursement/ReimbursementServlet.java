package Reimbursement;


import javax.servlet.http.HttpServlet;

public class ReimbursementServlet extends HttpServlet {
    private final ReimbursementService reimbursementService;
    public ReimbursementServlet(ReimbursementService reimbursementService){
        this.reimbursementService = reimbursementService;
    }
}
