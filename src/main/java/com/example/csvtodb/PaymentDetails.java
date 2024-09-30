package com.example.csvtodb;

public class PaymentDetails {
    private long receivedAmount;
    private String actualPaymentReceivedDate;
    private String monthOfReceivedPayment;
    private String paymentReferenceId;
    private long valueOfReferenceId;
    private Integer ageingDaysFromBookedToBilled;
    private Integer ageingTentativePaymentDaysFromBillSubmission;
    private Integer noOfDaysLeftToExpectPayment;
    private Integer ageingDaysFromBilledToReceived;

    public PaymentDetails() {}

    public PaymentDetails(long receivedAmount, String actualPaymentReceivedDate, String monthOfReceivedPayment, String paymentReferenceId, long valueOfReferenceId, Integer ageingDaysFromBookedToBilled, Integer ageingTentativePaymentDaysFromBillSubmission, Integer noOfDaysLeftToExpectPayment, Integer ageingDaysFromBilledToReceived) {
        this.receivedAmount = receivedAmount;
        this.actualPaymentReceivedDate = actualPaymentReceivedDate;
        this.monthOfReceivedPayment = monthOfReceivedPayment;
        this.paymentReferenceId = paymentReferenceId;
        this.valueOfReferenceId = valueOfReferenceId;
        this.ageingDaysFromBookedToBilled = ageingDaysFromBookedToBilled;
        this.ageingTentativePaymentDaysFromBillSubmission = ageingTentativePaymentDaysFromBillSubmission;
        this.noOfDaysLeftToExpectPayment = noOfDaysLeftToExpectPayment;
        this.ageingDaysFromBilledToReceived = ageingDaysFromBilledToReceived;
    }

    public long getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(long receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getActualPaymentReceivedDate() {
        return actualPaymentReceivedDate;
    }

    public void setActualPaymentReceivedDate(String actualPaymentReceivedDate) {
        this.actualPaymentReceivedDate = actualPaymentReceivedDate;
    }

    public String getMonthOfReceivedPayment() {
        return monthOfReceivedPayment;
    }

    public void setMonthOfReceivedPayment(String monthOfReceivedPayment) {
        this.monthOfReceivedPayment = monthOfReceivedPayment;
    }

    public String getPaymentReferenceId() {
        return paymentReferenceId;
    }

    public void setPaymentReferenceId(String paymentReferenceId) {
        this.paymentReferenceId = paymentReferenceId;
    }

    public long getValueOfReferenceId() {
        return valueOfReferenceId;
    }

    public void setValueOfReferenceId(long valueOfReferenceId) {
        this.valueOfReferenceId = valueOfReferenceId;
    }

    public Integer getAgeingDaysFromBookedToBilled() {
        return ageingDaysFromBookedToBilled;
    }

    public void setAgeingDaysFromBookedToBilled(Integer ageingDaysFromBookedToBilled) {
        this.ageingDaysFromBookedToBilled = ageingDaysFromBookedToBilled;
    }

    public Integer getAgeingTentativePaymentDaysFromBillSubmission() {
        return ageingTentativePaymentDaysFromBillSubmission;
    }

    public void setAgeingTentativePaymentDaysFromBillSubmission(Integer ageingTentativePaymentDaysFromBillSubmission) {
        this.ageingTentativePaymentDaysFromBillSubmission = ageingTentativePaymentDaysFromBillSubmission;
    }

    public Integer getNoOfDaysLeftToExpectPayment() {
        return noOfDaysLeftToExpectPayment;
    }

    public void setNoOfDaysLeftToExpectPayment(Integer noOfDaysLeftToExpectPayment) {
        this.noOfDaysLeftToExpectPayment = noOfDaysLeftToExpectPayment;
    }

    public Integer getAgeingDaysFromBilledToReceived() {
        return ageingDaysFromBilledToReceived;
    }

    public void setAgeingDaysFromBilledToReceived(Integer ageingDaysFromBilledToReceived) {
        this.ageingDaysFromBilledToReceived = ageingDaysFromBilledToReceived;
    }
}