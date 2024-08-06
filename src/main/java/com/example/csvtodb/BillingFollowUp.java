package com.example.csvtodb;

public class BillingFollowUp {
    private String billedStatusCollectionsTeam;
    private String billingFollowUpDate;
    private Integer noOfDaysLeftToRaiseInvoice;
    private String remarksTillBillNotSubmitted;

    public BillingFollowUp() {}

    public BillingFollowUp(String billedStatusCollectionsTeam, String billingFollowUpDate, Integer noOfDaysLeftToRaiseInvoice, String remarksTillBillNotSubmitted) {
        this.billedStatusCollectionsTeam = billedStatusCollectionsTeam;
        this.billingFollowUpDate = billingFollowUpDate;
        this.noOfDaysLeftToRaiseInvoice = noOfDaysLeftToRaiseInvoice;
        this.remarksTillBillNotSubmitted = remarksTillBillNotSubmitted;
    }

    public String getBilledStatusCollectionsTeam() {
        return billedStatusCollectionsTeam;
    }

    public void setBilledStatusCollectionsTeam(String billedStatusCollectionsTeam) {
        this.billedStatusCollectionsTeam = billedStatusCollectionsTeam;
    }

    public String getBillingFollowUpDate() {
        return billingFollowUpDate;
    }

    public void setBillingFollowUpDate(String billingFollowUpDate) {
        this.billingFollowUpDate = billingFollowUpDate;
    }

    public Integer getNoOfDaysLeftToRaiseInvoice() {
        return noOfDaysLeftToRaiseInvoice;
    }

    public void setNoOfDaysLeftToRaiseInvoice(Integer noOfDaysLeftToRaiseInvoice) {
        this.noOfDaysLeftToRaiseInvoice = noOfDaysLeftToRaiseInvoice;
    }

    public String getRemarksTillBillNotSubmitted() {
        return remarksTillBillNotSubmitted;
    }

    public void setRemarksTillBillNotSubmitted(String remarksTillBillNotSubmitted) {
        this.remarksTillBillNotSubmitted = remarksTillBillNotSubmitted;
    }
}
