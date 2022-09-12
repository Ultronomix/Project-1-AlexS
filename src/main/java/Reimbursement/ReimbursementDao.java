package Reimbursement;

import Exceptions.DataSourceException;
import util.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReimbursementDao {

   // private String baseSelect= = "SELECT eu.Reimbursement_id, eu.username, eu.email, eu.password, " +
          //  "eu.given_name, eu.surname, " +
          //  "eu.is_active, eur.role " +
          //  "FROM ers_users eu " +
         //   "JOIN ers_user_roles eur ON eu.role_id = eur.role_id "; // change this

    public List<Reimbursement> getAllReimbursement() {
        String sql = baseSelect;
        List<Reimbursement> allReimbursement = new ArrayList<>();

        try (Connection conn = ConnectionUtility.getInstance().getConnection()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            allReimbursement = mapResultSet(rs);
        } catch (SQLException e) {
            System.err.println("Something went with the database.");
            e.printStackTrace();
        }
        return allReimbursement;
    }
    public Optional<Reimbursement> findReimbursementById(UUID id){
        String sql = baseSelect + "WHERE au.id = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataSourceException(e);
        }
    }
    public Optional<Reimbursement> findReimbursementByAmount (String amount){
        String sql = baseSelect + "WHERE au.username = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, amount);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            throw new DataSourceException(e);
        }
    }
    public boolean isAmountTaken(String amount) {
        return findReimbursementByAmount(amount).isPresent();
    }
    public Optional<Reimbursement> findReimbursementBySubmitted(String submitted) {

          String sql = baseSelect + //PUT TABLE HERE

        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, submitted);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            throw new DataSourceException(e);
        }
    }
    public boolean isSubmittedTaken(String submitted) {
        return findReimbursementBySubmitted(submitted).isPresent();
    }
    public Optional<Reimbursement> findReimbursementByResolvedAndDescription(String resolved,String description){
        String sql = baseSelect + "WHERE au.resolved = ? AND au.description = ?";
        try(Connection conn = ConnectionUtility.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,resolved);
            pstmt.setString(2,description);
            ResultSet rs = pstmt.executeQuery();
            return mapResultSet(rs).stream().findFirst();
        }catch (SQLException e){
            e.printStackTrace();
            throw new DataSourceException(e);
        }
        public Optional<Reimbursement> findReimbursementByPayment_Id (String payment_id)
            //String sql = baseSelect + "WHERE au.id = ?";
            try(Connection conn = ConnectionUtility.getInstance().getConnection()){
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setObject(1, payment_id);
                ResultSet rs = pstmt.executeQuery();
                return mapResultSet(rs).stream().findFirst();
            } catch (SQLException e){
                e.printStackTrace();
                throw new DataSourceException(e);
            }