package com.example.csvtodb;

public class FinanceApproval {
    private Double homeFinanceApprovedValue;
    private String banksName;
    private String homeLoanFinanceStatus;
    private Double accrued;

    public FinanceApproval() {}

    public FinanceApproval(Double homeFinanceApprovedValue, String banksName, String homeLoanFinanceStatus, Double accrued) {
        this.homeFinanceApprovedValue = homeFinanceApprovedValue;
        this.banksName = banksName;
        this.homeLoanFinanceStatus = homeLoanFinanceStatus;
        this.accrued = accrued;
    }

    public Double getHomeFinanceApprovedValue() {
        return homeFinanceApprovedValue;
    }

    public void setHomeFinanceApprovedValue(Double homeFinanceApprovedValue) {
        this.homeFinanceApprovedValue = homeFinanceApprovedValue;
    }

    public String getBanksName() {
        return banksName;
    }

    public void setBanksName(String banksName) {
        this.banksName = banksName;
    }

    public String getHomeLoanFinanceStatus() {
        return homeLoanFinanceStatus;
    }

    public void setHomeLoanFinanceStatus(String homeLoanFinanceStatus) {
        this.homeLoanFinanceStatus = homeLoanFinanceStatus;
    }

    public Double getAccrued() {
        return accrued;
    }

    public void setAccrued(Double accrued) {
        this.accrued = accrued;
    }
}
