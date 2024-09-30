package com.example.csvtodb;

public class FinalBillingDetails {
    private long finalBilledToDeveloperValue;
    private double finalEarningToBlox;
    private String remarksForDifferenceValue;

    public FinalBillingDetails() {}

    public FinalBillingDetails(long finalBilledToDeveloperValue, long finalEarningToBlox, String remarksForDifferenceValue) {
        this.finalBilledToDeveloperValue = finalBilledToDeveloperValue;
        this.finalEarningToBlox = finalEarningToBlox;
        this.remarksForDifferenceValue = remarksForDifferenceValue;
    }

    public long getFinalBilledToDeveloperValue() {
        return finalBilledToDeveloperValue;
    }

    public void setFinalBilledToDeveloperValue(long finalBilledToDeveloperValue) {
        this.finalBilledToDeveloperValue = finalBilledToDeveloperValue;
    }

    public double getFinalEarningToBlox() {
        return finalEarningToBlox;
    }

    public void setFinalEarningToBlox(double finalEarningToBlox) {
        this.finalEarningToBlox = finalEarningToBlox;
    }

    public String getRemarksForDifferenceValue() {
        return remarksForDifferenceValue;
    }

    public void setRemarksForDifferenceValue(String remarksForDifferenceValue) {
        this.remarksForDifferenceValue = remarksForDifferenceValue;
    }
}
