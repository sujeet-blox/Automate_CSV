package com.example.csvtodb;

public class AgreementDetails {
    private String agreementStatus;
    private String dateOfCriteriaOneOrTwo;
    private String invoiceIssued;
    private String invoiceDate;
    private String paymentDueDate;
    private String paymentStatusRecd;
    private String passbackComments;

    // Getters and Setters
    public String getAgreementStatus() { return agreementStatus; }
    public void setAgreementStatus(String agreementStatus) { this.agreementStatus = agreementStatus; }
    public String getDateOfCriteriaOneOrTwo() { return dateOfCriteriaOneOrTwo; }
    public void setDateOfCriteriaOneOrTwo(String dateOfCriteriaOneOrTwo) { this.dateOfCriteriaOneOrTwo = dateOfCriteriaOneOrTwo; }
    public String getInvoiceIssued() { return invoiceIssued; }
    public void setInvoiceIssued(String invoiceIssued) { this.invoiceIssued = invoiceIssued; }
    public String getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(String invoiceDate) { this.invoiceDate = invoiceDate; }
    public String getPaymentDueDate() { return paymentDueDate; }
    public void setPaymentDueDate(String paymentDueDate) { this.paymentDueDate = paymentDueDate; }
    public String getPaymentStatusRecd() { return paymentStatusRecd; }
    public void setPaymentStatusRecd(String paymentStatusRecd) { this.paymentStatusRecd = paymentStatusRecd; }
    public String getPassbackComments() { return passbackComments; }
    public void setPassbackComments(String passbackComments) { this.passbackComments = passbackComments; }
}
