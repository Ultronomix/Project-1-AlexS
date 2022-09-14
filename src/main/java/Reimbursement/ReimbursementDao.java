package Reimbursement;

import Exceptions.DataSourceException;
import util.ConnectionUtility;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;

public class ReimbursementDao{

    private final String baseSelect = "SELECT au.reimbursement_id,au.amount,au.submitted,au.resolved,au.description,au.payment_id,au.author_id,au.resolver_id,au.status_id " +
      "FROM ers_reimbursements eu " +
       "JOIN reimbursements_roles ur "+
    " ON au.role_id = ur.role_id ";

    public List<Reimbursement> GetAllReimbursement () {
        List<Reimbursement> allReimbursement = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(baseSelect);

            allReimbursement = mapResultSet(rs);
        } catch (SQLException e) {
            System.err.println("Something went with the database.");
            e.printStackTrace();
        }
        return allReimbursement;
    }

    public Optional<Reimbursement> findReimbursementById(UUID reimbursement_id) {
        String sql = baseSelect + "WHERE au.reimbursement_id = ?";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, reimbursement_id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e);
        }
    }

    public Optional<Reimbursement> findReimbursementByAmount(String amount) {
        String sql = baseSelect + "WHERE au.amount = ?";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, amount);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public boolean isAmountTaken(String amount) {
        return findReimbursementByAmount(amount).isPresent();
    }

    public Optional<Reimbursement> findReimbursementBySubmitted(String submitted) {

        String sql = baseSelect + "WHERE.au.submitted = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, submitted);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public boolean isSubmittedTaken(String submitted) {
        return findReimbursementBySubmitted(submitted).isPresent();
    }

    public Optional<Reimbursement> findReimbursementByResolvedAndDescription(String resolved, String description) {
        String sql = baseSelect + "WHERE au.resolved = ? AND au.description = ?";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, resolved);
            pstmt.setString(2, description);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e);
        }

    }

    public Optional<Reimbursement> findReimbursementByAuthor_idAndResolver_id(String author_id, String resolver_id) {

        String sql = baseSelect + "WHERE au.author_id = ? AND au.resolver_id = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, author_id);
            pstmt.setString(2, resolver_id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            // TODO log this exception
            throw new DataSourceException(e);
        }
    }

    public Optional<Reimbursement> findReimbursementByStatus_idAndType_id(String status_id, String type_id) {

        String sql = baseSelect + "WHERE au.status_id = ? AND au.type_id = ?";

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {


            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status_id);
            pstmt.setString(2, type_id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();

        } catch (SQLException e) {
            // TODO log this exception
            throw new DataSourceException(e);


        }
    }
    public String updateReimbursement_Id(String to,String id){
        String sql ="update\"reimbursement\"set reimbursement_id =? where id=?;";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1,Boolean.parseBoolean(to));
            pstmt.setInt(2,Integer.parseInt(id));
            int rs= pstmt.executeUpdate();
            return "Reim active status updaed to " + to + "Rows affected =" +rs;
        }catch (SQLException e){
            throw new DataSourceException(e);
        }


    }

    public String save(Reimbursement reimbursement) {

        String sql = "INSERT INTO app_users (reimbursement_id,amount,submitted,resolved,description,payment_id,author_id,resolver_id,status_id,type_id) " +
         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? '49ce5a1f-6b51-4e40-8e88-2bf9289292cd')";
        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id"});
            pstmt.setString(1, reimbursement.getReimbursement_id());
            pstmt.setString(2, reimbursement.getAmount());
            pstmt.setString(3, reimbursement.getSubmitted());
            pstmt.setString(4, reimbursement.getResolved());
            pstmt.setString(5, reimbursement.getDescription());
            pstmt.setString(6, reimbursement.getPayment_id());
            pstmt.setString(7, reimbursement.getAuthor_id());
            pstmt.setString(8, reimbursement.getResolver_id());
            pstmt.setString(9, reimbursement.getStatus_id());
            pstmt.setString(10, reimbursement.getType_id());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            reimbursement.setReimbursement_id(rs.getString("reimbursement_id"));

        } catch (SQLException e) {
            log("ERROR", e.getMessage());
        }

        log("INFO", "Successfully persisted new used with id: " + reimbursement.getReimbursement_id());

        return reimbursement.getReimbursement_id();
    }



    private List<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
        List<Reimbursement> reimbursements = new ArrayList<>();
        while (rs.next()) {
            Reimbursement reimbursement = new Reimbursement();
            reimbursement.setReimbursement_id(rs.getString("reimbursement_id"));
            reimbursement.setAmount(rs.getString("amount"));
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
    public void log(String level, String message) {
        try {
            File logFile = new File("logs/app.log");
            logFile.createNewFile();
                BufferedWriter logWriter = new BufferedWriter(new FileWriter(logFile));
            logWriter.write(String.format("[%s] at %s logged: [%s] %s\n", Thread.currentThread().getName(), LocalDate.now(), level.toUpperCase(), message));
            logWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

}
public boolean isIdVaild(String id){
        return findReimbursementById(UUID.fromString(id)).isPresent();
}
}

