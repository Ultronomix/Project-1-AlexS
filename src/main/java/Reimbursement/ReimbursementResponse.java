package Reimbursement;

import java.io.Serializable;
import java.util.Objects;

public class ReimbursementResponse implements Serializable {
        private String reimbursement_id;
        private double amount;
        private String submitted;
        private String resolved;
        private String description;
        private String payment_id;
        private String author_id;
        private String resolver_id;
        private String status_id;
        private String type_id;
     //   private String role_id;
        public ReimbursementResponse(Reimbursement subject){
        this.reimbursement_id = subject.getReimbursement_id();
        this.amount = subject.getAmount();
        this.submitted = subject.getSubmitted();
        this.resolved =subject.getResolved();
        this.description = subject.getDescription();
        this.payment_id = subject.getPayment_id();
        this.author_id = subject.getAuthor_id();
        this.resolver_id = subject.getResolver_id();
        this.status_id = subject.getStatus_id();
        this.type_id = subject.getType_id();
    //    this.role_id =subject.getRole_id();

    }
    public String getReimbursement_id() {
        return reimbursement_id;
    }

    public void setReimbursement_id(String reimbursement_id) {
        this.reimbursement_id = reimbursement_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getResolved() {
        return resolved;
    }

    public void setResolved(String resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getResolver_id() {
        return resolver_id;
    }

    public void setResolver_id(String resolver_id) {
        this.resolver_id = resolver_id;

    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;

    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;

    }
   // public String getRole_id() {
    //    return role_id;
   // }

 //   public void setRole_id(String role_id) {
  //      this.role_id = role_id;

  //  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReimbursementResponse)) return false;
        ReimbursementResponse that = (ReimbursementResponse) o;
        return Objects.equals(reimbursement_id, that.reimbursement_id) && Objects.equals(amount, that.amount) && Objects.equals(submitted, that.submitted) && Objects.equals(resolved, that.resolved) && Objects.equals(description, that.description) && Objects.equals(payment_id, that.payment_id) && Objects.equals(author_id, that.author_id) && Objects.equals(resolver_id, that.resolver_id) && Objects.equals(status_id, that.status_id) && Objects.equals(type_id, that.type_id); //&& Objects.equals(role_id, that.role_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbursement_id, amount, submitted, resolved, description, payment_id, author_id, resolver_id, status_id, type_id); //, role_id);
    }

    @Override
    public String toString() {
        return "ReimbursementResponse{" +
                "reimbursement_id='" + reimbursement_id + '\'' +
                ", amount='" + amount + '\'' +
                ", submitted='" + submitted + '\'' +
                ", resolved='" + resolved + '\'' +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", resolver_id='" + resolver_id + '\'' +
                ", status_id='" + status_id + '\'' +
                ", type_id='" + type_id + '\'' +
               // ", role_id='" + role_id + '\'' +
                '}';
    }
}
