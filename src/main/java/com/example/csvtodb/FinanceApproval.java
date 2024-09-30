package com.example.csvtodb;

public class FinanceApproval {
    private long homeFinanceApprovedValue;
    private String banksName;
    private String homeLoanFinanceStatus;
    private long accrued;

    public FinanceApproval() {}

    public FinanceApproval(long homeFinanceApprovedValue, String banksName, String homeLoanFinanceStatus, long accrued) {
        this.homeFinanceApprovedValue = homeFinanceApprovedValue;
        this.banksName = banksName;
        this.homeLoanFinanceStatus = homeLoanFinanceStatus;
        this.accrued = accrued;
    }

    public long getHomeFinanceApprovedValue() {
        return homeFinanceApprovedValue;
    }

    public void setHomeFinanceApprovedValue(long homeFinanceApprovedValue) {
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

    public long getAccrued() {
        return accrued;
    }

    public void setAccrued(long accrued) {
        this.accrued = accrued;
    }
}
