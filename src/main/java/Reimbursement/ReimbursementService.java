package Reimbursement;

import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
import Reimbursement.UpdateRequestBodyO;
import Users.User;
import common.ResourceCreationResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReimbursementService {
    private final ReimbursementDao reimbursementDao;

    public ReimbursementService(ReimbursementDao reimbursementDao) {
        this.reimbursementDao = reimbursementDao;
    }

    public List<ReimbursementResponse> getAllReimbursement() {
        return reimbursementDao.GetAllReimbursement()
                .stream()
                .map(ReimbursementResponse::new)
                .collect(Collectors.toList());
    }

    public ReimbursementResponse getReimbursementById(String id) {
        if (id == null || id.length() <= 0) {
            throw new InvalidRequestException("A non-empty id must be provided !");
        }
        try {
            UUID uuid = UUID.fromString(id);
            return reimbursementDao.findReimbursementById(uuid)
                    .map(ReimbursementResponse::new)
                    .orElseThrow(ResourceNotFoundException::new);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("An invalid UUID string was provided.");
        }
    }

    public ResourceCreationResponse register(NewReimbursementRequest newReimbursement) {
        if (newReimbursement == null) {
            throw new InvalidRequestException("provided request payload was null.");
        }
        if (newReimbursement.getAmount() == null || newReimbursement.getAmount().length() <= 0 ||
                newReimbursement.getSubmitted() == null || newReimbursement.getSubmitted().length() <= 0) {
            throw new InvalidRequestException("A empty amount and submission must be provided");
        }
        if (newReimbursement.getResolved() == null || newReimbursement.getResolved().length() <= 0) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getDescription() == null || newReimbursement.getDescription().length() < 8) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getPayment_id() == null || newReimbursement.getPayment_id().length() < 4) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getAuthor_id() == null || newReimbursement.getAuthor_id().length() < 4) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getResolver_id() == null || newReimbursement.getResolver_id().length() < 4) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getStatus_id() == null || newReimbursement.getStatus_id().length() < 4) {
            throw new InvalidRequestException("failed to resolve");
        }
        if (newReimbursement.getType_id() == null || newReimbursement.getType_id().length() < 4) {
            throw new InvalidRequestException("failed to resolve");
        }
        Reimbursement reimbursementToPersist = newReimbursement.extractEntity();
        String newReimbursementId = reimbursementDao.save(reimbursementToPersist);
        return new ResourceCreationResponse(newReimbursementId);
    }

    public ResourceCreationResponse updateReimbursement_Id(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);

    }

    public ResourceCreationResponse updateAmount(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);

    }

    public ResourceCreationResponse updateSubmitted(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }

    public ResourceCreationResponse updateResolved(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }

    public ResourceCreationResponse updateDescription(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }

    public ResourceCreationResponse updateAuthor_Id(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }

    public ResourceCreationResponse updateResolver_Id(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }

    public ResourceCreationResponse updateStatus_Id(UpdateRequestBodyO updateRequestBodyO) {
        if (updateRequestBodyO == null) {
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <= 0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length() <= 0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())) {
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(), updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
    }
    public ResourceCreationResponse updateType_Id (UpdateRequestBodyO updateRequestBodyO){
        if (updateRequestBodyO == null){
            throw new InvalidRequestException("provided request not null");
        }
        if (UpdateRequestBodyO.getUpdateto() == null || updateRequestBodyO.getUpdateto().length() <=0 ||
                updateRequestBodyO.getReimbursementid() == null || updateRequestBodyO.getReimbursementid().length()<=0) {
            throw new InvalidRequestException("must provided name and user id");
        }
        if (!reimbursementDao.isIdVaild(updateRequestBodyO.getReimbursementid())){
            throw new InvalidRequestException("must provide reim_id");
        }
        String updateSuccessulMessage = reimbursementDao.updateReimbursement_Id(updateRequestBodyO.getUpdateto(),updateRequestBodyO.getReimbursementid());
        return new ResourceCreationResponse(updateSuccessulMessage);
}



