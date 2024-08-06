package com.example.csvtodb;

public class JSONModel {
    private String uniqueCode;
    private BookingInfo bookingInfo;
    private FinancialDetails financialDetails;
    private AgreementDetails agreementDetails;
    private SourcingCredits sourcingCredits;
    private FollowUpDetails followUpDetails;
    private FinanceApproval financeApproval;
    private FinalBillingDetails finalBillingDetails;
    private BillingFollowUp billingFollowUp;
    private InvoiceDetails invoiceDetails;
    private PaymentDetails paymentDetails;
    private ProjectDetails projectDetails;

    // Getters and Setters
    public String getUniqueCode() { return uniqueCode; }
    public void setUniqueCode(String uniqueCode) { this.uniqueCode = uniqueCode; }
    public BookingInfo getBookingInfo() { return bookingInfo; }
    public void setBookingInfo(BookingInfo bookingInfo) { this.bookingInfo = bookingInfo; }
    public FinancialDetails getFinancialDetails() { return financialDetails; }
    public void setFinancialDetails(FinancialDetails financialDetails) { this.financialDetails = financialDetails; }
    public AgreementDetails getAgreementDetails() { return agreementDetails; }
    public void setAgreementDetails(AgreementDetails agreementDetails) { this.agreementDetails = agreementDetails; }
    public SourcingCredits getSourcingCredits() { return sourcingCredits; }
    public void setSourcingCredits(SourcingCredits sourcingCredits) { this.sourcingCredits = sourcingCredits; }
    public FollowUpDetails getFollowUpDetails() { return followUpDetails; }
    public void setFollowUpDetails(FollowUpDetails followUpDetails) { this.followUpDetails = followUpDetails; }
    public FinanceApproval getFinanceApproval() { return financeApproval; }
    public void setFinanceApproval(FinanceApproval financeApproval) { this.financeApproval = financeApproval; }
    public FinalBillingDetails getFinalBillingDetails() { return finalBillingDetails; }
    public void setFinalBillingDetails(FinalBillingDetails finalBillingDetails) { this.finalBillingDetails = finalBillingDetails; }
    public BillingFollowUp getBillingFollowUp() { return billingFollowUp; }
    public void setBillingFollowUp(BillingFollowUp billingFollowUp) { this.billingFollowUp = billingFollowUp; }
    public InvoiceDetails getInvoiceDetails() { return invoiceDetails; }
    public void setInvoiceDetails(InvoiceDetails invoiceDetails) { this.invoiceDetails = invoiceDetails; }
    public PaymentDetails getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetails paymentDetails) { this.paymentDetails = paymentDetails; }
    public ProjectDetails getProjectDetails() { return projectDetails; }
    public void setProjectDetails(ProjectDetails projectDetails) { this.projectDetails = projectDetails; }
}
