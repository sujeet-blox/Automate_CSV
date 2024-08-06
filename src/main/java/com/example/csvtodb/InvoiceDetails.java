package com.example.csvtodb;

public class InvoiceDetails {
    private String invoiceSubmittedToDeveloperDate;
    private String invoiceNumber;
    private String tentativePaymentDate;
    private String monthOfExpectedPayment;
    private String paymentFollowUpDate;
    private String remarksTillPaymentNotReceived;

    public InvoiceDetails() {}

    public InvoiceDetails(String invoiceSubmittedToDeveloperDate, String invoiceNumber, String tentativePaymentDate, String monthOfExpectedPayment, String paymentFollowUpDate, String remarksTillPaymentNotReceived) {
        this.invoiceSubmittedToDeveloperDate = invoiceSubmittedToDeveloperDate;
        this.invoiceNumber = invoiceNumber;
        this.tentativePaymentDate = tentativePaymentDate;
        this.monthOfExpectedPayment = monthOfExpectedPayment;
        this.paymentFollowUpDate = paymentFollowUpDate;
        this.remarksTillPaymentNotReceived = remarksTillPaymentNotReceived;
    }

    public String getInvoiceSubmittedToDeveloperDate() {
        return invoiceSubmittedToDeveloperDate;
    }

    public void setInvoiceSubmittedToDeveloperDate(String invoiceSubmittedToDeveloperDate) {
        this.invoiceSubmittedToDeveloperDate = invoiceSubmittedToDeveloperDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getTentativePaymentDate() {
        return tentativePaymentDate;
    }

    public void setTentativePaymentDate(String tentativePaymentDate) {
        this.tentativePaymentDate = tentativePaymentDate;
    }

    public String getMonthOfExpectedPayment() {
        return monthOfExpectedPayment;
    }

    public void setMonthOfExpectedPayment(String monthOfExpectedPayment) {
        this.monthOfExpectedPayment = monthOfExpectedPayment;
    }

    public String getPaymentFollowUpDate() {
        return paymentFollowUpDate;
    }

    public void setPaymentFollowUpDate(String paymentFollowUpDate) {
        this.paymentFollowUpDate = paymentFollowUpDate;
    }

    public String getRemarksTillPaymentNotReceived() {
        return remarksTillPaymentNotReceived;
    }

    public void setRemarksTillPaymentNotReceived(String remarksTillPaymentNotReceived) {
        this.remarksTillPaymentNotReceived = remarksTillPaymentNotReceived;
    }
}
