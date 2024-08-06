package com.example.csvtodb;

public class FinalBillingDetails {
    private Double finalBilledToDeveloperValue;
    private Double finalEarningToBlox;
    private String remarksForDifferenceValue;

    public FinalBillingDetails() {}

    public FinalBillingDetails(Double finalBilledToDeveloperValue, Double finalEarningToBlox, String remarksForDifferenceValue) {
        this.finalBilledToDeveloperValue = finalBilledToDeveloperValue;
        this.finalEarningToBlox = finalEarningToBlox;
        this.remarksForDifferenceValue = remarksForDifferenceValue;
    }

    public Double getFinalBilledToDeveloperValue() {
        return finalBilledToDeveloperValue;
    }

    public void setFinalBilledToDeveloperValue(Double finalBilledToDeveloperValue) {
        this.finalBilledToDeveloperValue = finalBilledToDeveloperValue;
    }

    public Double getFinalEarningToBlox() {
        return finalEarningToBlox;
    }

    public void setFinalEarningToBlox(Double finalEarningToBlox) {
        this.finalEarningToBlox = finalEarningToBlox;
    }

    public String getRemarksForDifferenceValue() {
        return remarksForDifferenceValue;
    }

    public void setRemarksForDifferenceValue(String remarksForDifferenceValue) {
        this.remarksForDifferenceValue = remarksForDifferenceValue;
    }
}
