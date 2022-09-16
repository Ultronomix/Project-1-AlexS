package Reimbursement;


import common.Request;

public class UpdateRequestBodyO implements Request<Reimbursement> {
    private String updateto;
    private String reimbursement_id;
    private int amount;
    private String submitted;
    private String description;
    private String payment_id;
    private String author_id;
    private String type_id;

   // private String role_id;


  //  public String getRole_id(){
   //     return role_id;
  //  }
 //   public void setRole_id(String role_id){
  //      this.role_id = role_id;
  //  }

    public String getUpdateto() {
        return updateto;
    }

    public void setUpdateto(String updateto) {
        this.updateto = updateto;
    }

    public String getReimbursement_id() {
        return reimbursement_id;
    }

    public void setReimbursement_id(String reimbursement_id) {
        this.reimbursement_id = reimbursement_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
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

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;

    }

    @Override
    public String toString() {
        return "UpdateRequestBodyO{" +
                "updateto='" + updateto + '\'' +
                ", reimbursement_id='" + reimbursement_id + '\'' +
                ", amount=" + amount +
                ", submitted='" + submitted + '\'' +
                ", description='" + description + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", type_id='" + type_id + '\'' +
             //   ", role_id'" + role_id + '\'' +
                '}';
    }
    @Override
    public Reimbursement extractEntity(){
        Reimbursement extractEntity = new Reimbursement();
        extractEntity.setReimbursement_id(this.reimbursement_id);
        extractEntity.setAmount(this.amount);
        extractEntity.setSubmitted(this.submitted);
        extractEntity.setDescription(this.description);
        extractEntity.setPayment_id(this.payment_id);
        extractEntity.setAuthor_id(this.author_id);
        extractEntity.setType_id(this.type_id);
       // extractEntity.setRole_id(this.role_id);
        return null;
    }
}
