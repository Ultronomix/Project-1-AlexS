package Reimbursement;

import Exceptions.InvalidRequestException;
import Exceptions.ResourceNotFoundException;
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
    } catch (IllegalArgumentException e)

    {
        throw new InvalidRequestException("An invalid UUID string was provided.");
    }
}
    public ResourceCreationResponse register(NewReimbursementRequest newReimbursement){
        if (newReimbursement == null) {
            throw new InvalidRequestException("Provided request payload was null.");
        }
        if (newReimbursement.getGivenName() == null || newReimbursement.getGivenName().length() <= 0 ||
                newReimbursement.getSurname() == null || newReimbursement.getSurname().length() <= 0)
    }

}
