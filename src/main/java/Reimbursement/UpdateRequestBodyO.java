package Reimbursement;
import java.util.Objects;


public class UpdateRequestBodyO {
    private String  updateto;
    private String reimbursementid;

    public String getUpdateto() {
        return updateto;
    }

    public void setUpdateto(String  Updateto) {
        this.updateto =     Updateto;

    }

    public String getReimbursementid() {
        return reimbursementid;
    }

    public void setReimbursementid(String reimbursementid) {
        this.reimbursementid = reimbursementid;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateRequestBodyO)) return false;
        UpdateRequestBodyO that = (UpdateRequestBodyO) o;
        return Objects.equals(updateto, that.updateto) && Objects.equals(reimbursementid, that.reimbursementid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateto, reimbursementid);
    }

    @Override
    public String toString() {
        return "UpdateRequestBody{" +
                "updateto='" + updateto + '\'' +
                ", reimbursementid='" + reimbursementid + '\'' +
                '}';
    }
}

