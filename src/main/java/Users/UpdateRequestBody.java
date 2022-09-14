package Users;

import java.util.Objects;

public class UpdateRequestBody {
    private String updateto;
    private String userId;


    public String getUpdateto() {
        return updateto;
    }

    public void setUpdateto(String updateto) {
        this.updateto = updateto;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UpdateRequestBody)) return false;
        UpdateRequestBody that = (UpdateRequestBody) o;
        return Objects.equals(updateto, that.updateto) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(updateto, userId);
    }

    @Override
    public String toString() {
        return "UpdateRequestBody{" +
                "updateto='" + updateto + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}



