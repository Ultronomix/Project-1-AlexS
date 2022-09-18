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

    private final String Select = "SELECT er.reimbursement_id, er.amount, er.submitted, er.resolved, "+
            "er.description, er.payment_id, er.author_id, er.resolver_id, ers.status_id ,ert.type_id " +
            "FROM ers_reimbursements er "+
            "JOIN ers_reimbursement_statuses ers ON er.status_id = ers.status_id "+
            "JOIN ers_reimbursement_types ert ON er.type_id = ert.type_id ";

    public List<Reimbursement> getAllReimbursements (){
        List<Reimbursement> allReimbursements = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Select);
            allReimbursements = mapResultSet(rs);
            return allReimbursements;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }



    public Optional<Reimbursement> getReimbursementById(String id) {
        String sqlId = Select + "WHERE er.reimbursement_id = ?";
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
        String sqlId = Select + "WHERE er.reimbursement_id = ?";
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
        String sqlStatus = Select + "WHERE ers.status_id = ?";
        List<Reimbursement> reimbursementStatus = new ArrayList<>();
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlStatus);
            pstmt.setString(1, status.toUpperCase());
            ResultSet rs = pstmt.executeQuery();
            reimbursementStatus = mapResultSet(rs);
            return reimbursementStatus;
        } catch (SQLException e) {
            throw new DataSourceException(e);

        }
    }

    public List<Reimbursement> getReimbursementByType (String type) {
        String sqlType = Select + "WHERE ert.type_id";
        List<Reimbursement> reimbursementType = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sqlType);
            pstmt.setString(1, type.toUpperCase());
            ResultSet rs = pstmt.executeQuery();
            reimbursementType =mapResultSet(rs);
            return reimbursementType;
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
    public String updateRequestStatus (String status ,String reimbursement_id,String resolver_id) {
        String updateSql = "UPDATE ers_reimbursements SET status_id = ?, resolved = ?, resolver_id = ? WHERE reimbursement_id = ?";

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

            throw new DataSourceException(e);
        }
    }
    public String updateUserType (String reimbursementId, String type_id) {


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
         //   reimbursement.setSubmitted(rs.getString("submitted"));
          //  reimbursement.setResolved(rs.getString("resolved"));
            reimbursement.setDescription(rs.getString("description"));
            reimbursement.setPayment_id(rs.getString("payment_id"));
            reimbursement.setAuthor_id(rs.getString("author_id"));
            reimbursement.setResolver_id(rs.getString("resolver_id"));
            reimbursement.setStatus_id(rs.getString("status_id"));
            reimbursement.setType_id(rs.getString("type_id"));
          //  reimbursement.setRole_id(rs.getString("role_id"));

            reimbursements.add(reimbursement);
        }
        return reimbursements;
    }

    public String save(Reimbursement newReimbursement) {
        String select = "INSERT INTO ers_reimbursements (reimbursement_id, amount, description, payment_id, author_id, resolver_id, status_id, type_id) " +
                "VALUES(?,?,?,?,?,?,?,?)"; //submitted, resolved

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(select, new String[]{"reimbursement_id"});
            pstmt.setString(1, newReimbursement.getReimbursement_id());
            pstmt.setDouble(2, newReimbursement.getAmount());
           // pstmt.setString(3, newReimbursement.getSubmitted());
            // pstmt.setString(4, newReimbursement.getResolved());
            pstmt.setString(3, newReimbursement.getDescription());
            pstmt.setString(4, newReimbursement.getPayment_id());
            pstmt.setString(5, newReimbursement.getAuthor_id());
            pstmt.setString(6, newReimbursement.getResolver_id());
            pstmt.setString(7, newReimbursement.getStatus_id());
           pstmt.setString(8, newReimbursement.getType_id());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            newReimbursement.setReimbursement_id(rs.getString("reimbursement_id"));
        } catch (SQLException e){
            e.printStackTrace();
    }
        return newReimbursement.getReimbursement_id();

    }

}


