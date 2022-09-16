package Reimbursement;

import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import common.ResourceCreationResponse;

import java.util.ArrayList;
import java.util.List;



public class ReimbursementService {
    private final ReimbursementDao reimbursementDao;

    public ReimbursementService(ReimbursementDao reimbursementDao) {
        this.reimbursementDao = reimbursementDao;
    }

    public List<ReimbursementResponse> getAllReimbursement() {
        List<ReimbursementResponse> result = new ArrayList<>();
        List<Reimbursement> reimbursements = reimbursementDao.getAllReimbursements();
        for (Reimbursement reimbursement : reimbursements) {
            result.add(new ReimbursementResponse(reimbursement));
        }
        return result;
    }

    public ReimbursementResponse getReimbursementById(String id) {
        if (id == null || id.length() <= 0) {
            throw new InvalidRequestException("A non-empty id must be provided !");
        }
        return reimbursementDao.getReimbursementById(id).map(ReimbursementResponse::new).orElseThrow(ResourceNotFoundException::new);
    }

    public List<ReimbursementResponse> getReimbursementByStatus(String status) {
        if (status == null || (!status.toUpperCase().trim().equals("APPROVED")
                && !status.toUpperCase().trim().equals("PENDING")
                && !status.toUpperCase().trim().equals("DENIED"))) {
        }
        List<ReimbursementResponse> result = new ArrayList<>();
        List<Reimbursement> reimbursements = reimbursementDao.getReimbursementByStatus(status);
        for (Reimbursement reimbursement : reimbursements) {
            result.add(new ReimbursementResponse(reimbursement));
        }
        return result;


    }

    public List<ReimbursementResponse> getReimbursementByType(String type) {
        if (type == null || (!type.toUpperCase().trim().equals("LODGING")
                && !type.toUpperCase().trim().equals("TRAVEL")
                && !type.toUpperCase().trim().equals("FOOD")
                && !type.toUpperCase().trim().equals("OTHER"))) {
            throw new InvalidRequestException("Type must be 'Lodging', 'Travel', " + "'Food', or 'Other'");

        }
        List<ReimbursementResponse> result = new ArrayList<>();
        List<Reimbursement> reimbursements = reimbursementDao.getReimbursementByType(type);
        for (Reimbursement reimbursement : reimbursements) {
            result.add(new ReimbursementResponse(reimbursement));
        }
        return result;
    }

    public ResourceCreationResponse updateReimbursement(UpdateRequestBodyO updateReimbursement, String reimbursementIdToSearch, String resolver_id) {
        if (updateReimbursement == null) {
            throw new InvalidRequestException("provided request not null");
        }
        String reimbursementToUpdate = updateReimbursement.extractEntity().getStatus_id().toUpperCase();
        if (reimbursementToUpdate.equals("APPROVED")) {
            reimbursementToUpdate = "100001";
        } else if (reimbursementToUpdate.equals("DENIED")) {
            reimbursementToUpdate = "10003";
        }
        String update = reimbursementDao.updateRequestStatus(reimbursementToUpdate, reimbursementIdToSearch, resolver_id);
        return new ResourceCreationResponse(update);
    }

    public ResourceCreationResponse updateUserReimbursement(UpdateRequestBodyO updateReimbursement, String reimbursementId) {
        if (updateReimbursement == null) {
            throw new InvalidRequestException("Provide request payload");
        }
        double newAmount = updateReimbursement.extractEntity().getAmount();
        String newDescription = updateReimbursement.extractEntity().getDescription();
        String newType = updateReimbursement.extractEntity().getType_id();

        System.out.println(newAmount);

        if (newAmount > 0) {
            if (newAmount > 9999.99) {
                throw new InvalidRequestException("Amount must be below 10k");
            }
            reimbursementDao.updateUserDescription(reimbursementId, newDescription);
        }
        if (newType != null) {
            if (!newType.toUpperCase().equals("LODGING") && !newType.toUpperCase().equals("TRAVEL")
                    && !newType.toUpperCase().equals("FOOD") && !newType.toUpperCase().equals("Other")) {
                throw new InvalidRequestException("Type must be 'lodging,'Travel','Food'" + "or 'other'");
            }
            if (newType.toUpperCase().equals("LODGING")) {
                newType = "20001";
            }
            if (newType.toUpperCase().equals("TRAVEL")) {
                newType = "200002";
            }
            if (newType.toUpperCase().equals("FOOD")) {
                newType = "200003";
            }
            if (newType.toUpperCase().equals("OTHER")) {
                newType = "200004";
            }
            reimbursementDao.updateUserType(reimbursementId, newType);
        }
        return new ResourceCreationResponse("Updated requests");
    }
}

