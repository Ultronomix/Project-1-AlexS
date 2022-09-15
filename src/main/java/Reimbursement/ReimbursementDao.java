package Reimbursement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import Exceptions.DataSourceException;
import Exceptions.ResourceNotFoundException;
import util.ConnectionUtility;
import java.sql.*;
import java.time.LocalDateTime;



public class ReimbursementDao {

    private final String Select = "SELECT au.reimbursement_id,au.amount,au.submitted,au.resolved, " +
            "au.description,au.payment_id,au.author_id,au.resolver_id,au.status_id" +
            "ers.status, ert.type" +
            "FROM ers_reimbursements eu " +
            "JOIN reimbursements_statuses ers ON au.status_id = ers.status_id " +
            "JOIN reimbursements_types ert ON au.type ert ON au.type_id = ert.type_id";

    public List<Reimbursement> getAllReimbursements (){
        List<Reimbursement> allReimbursements = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Select);
            return allReimbursements;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }



    public Optional<Reimbursement> getReimbursementById(String id) {
        String sqlId = Select + "WHERE au.author_id = ?";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlId);
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
           // e.printStackTrace();
            throw new DataSourceException(e);
        }
    }

    public Optional<Reimbursement> getReimbursementByReimbursementId (String reimbursementId) {
        String sqlId = Select + "WHERE au.reimbursement_id = ?";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlId);
            pstmt.setString(1, reimbursementId);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public List<Reimbursement> getReimbursementByStatus (String status) {
        String sqlStatus = Select + "WHERE au.status = ?";
        List<Reimbursement> reimbursementStatus = new ArrayList<>();
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlStatus);
            pstmt.setString(1, status.toUpperCase());
            ResultSet rs = pstmt.executeQuery();
            return reimbursementStatus;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public List<Reimbursement> getReimbursementByType (String type) {
        String sqlType = Select + "WHERE ert.type";
        List<Reimbursement> reimbursementType = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlType);
            pstmt.setString(1, type.toUpperCase());
            ResultSet rs = pstmt.executeQuery();
            return reimbursementType;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    public String updateRequestStatus (String status ,String reimbursement_id,String resolver_id) {
        String updateSql = "UPDATE ers_reimbursements SET status_id = ?, resolved = ?, resolver_id = ? WHERE reimb_id = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(updateSql);
            pstmt.setString(1, status);
            pstmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(3, resolver_id);
            pstmt.setString(4, reimbursement_id);
            pstmt.executeUpdate();
            return "Updated status";
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
        public String updateUserAmount(String reimbursement_id, double newAmount) {

            // TODO add log
            String updateAmountSql = "UPDATE ers_reimbursements SET amount = ? WHERE reimbursement_id = ?";

            try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

                PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
                pstmt.setDouble(1, newAmount);
                pstmt.setString(2, reimbursement_id);

                pstmt.executeUpdate();

                return "Amount ";
            } catch (SQLException e) {
                // TODO add log
                throw new DataSourceException(e);


            }
        }
    public String updateUserDescription (String reimbursementId, String description) {

        String updateAmountSql = "UPDATE ers_reimbursements SET description = ? WHERE reimbursement_id = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
            pstmt.setString(1, description);
            pstmt.setString(2, reimbursementId);
            System.out.println(pstmt);
            pstmt.executeUpdate();

            return "Description ";
        } catch (SQLException e) {
            // TODO add log
            throw new DataSourceException(e);
        }
    }
    public String updateUserType (String reimbursementId, String type_id) {

        // TODO add log
        String updateAmountSql = "UPDATE ers_reimbursements SET type_id = ? WHERE reimbursement_id = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(updateAmountSql);
            pstmt.setString(1, type_id);
            pstmt.setString(2, reimbursementId);
            System.out.println(pstmt);
            pstmt.executeUpdate();

            return "Type ";
        } catch (SQLException e) {

            throw new DataSourceException(e);
        }
    }
    public boolean isPending (String reimbursementId){
        try {
            Optional<Reimbursement> reimbursement = getReimbursementByReimbursementId(reimbursementId);

            if (reimbursement.get().getStatus_id().equals("PENDING")) {
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException e) {
            throw new ResourceNotFoundException();

        }
    }
    private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        while (rs.next()) {
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursement_id(rs.getString("reimbursement_id"));
            reimbursement.setAmount(rs.getDouble("amount"));
            reimbursement.setSubmitted(rs.getString("submitted"));
            reimbursement.setResolved(rs.getString("resolved"));
            reimbursement.setDescription(rs.getString("description"));
            reimbursement.setPayment_id(rs.getString("payment_id"));
            reimbursement.setAuthor_id(rs.getString("author_id"));
            reimbursement.setResolver_id(rs.getString("resolver_id"));
            reimbursement.setStatus_id(rs.getString("status_id"));
            reimbursement.setType_id(rs.getString("type_id"));
        }
        return reimbursements;
    }

}


