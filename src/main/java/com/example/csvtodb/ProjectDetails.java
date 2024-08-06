package com.example.csvtodb;

public class ProjectDetails {
    private String nameOfDeveloper;
    private String zoneOfProject;
    private String bookingMonth;
    private String enquiryId;
    private String customerName;

    public ProjectDetails() {}

    public ProjectDetails(String nameOfDeveloper, String zoneOfProject, String bookingMonth, String enquiryId, String customerName) {
        this.nameOfDeveloper = nameOfDeveloper;
        this.zoneOfProject = zoneOfProject;
        this.bookingMonth = bookingMonth;
        this.enquiryId = enquiryId;
        this.customerName = customerName;
    }

    public String getNameOfDeveloper() {
        return nameOfDeveloper;
    }

    public void setNameOfDeveloper(String nameOfDeveloper) {
        this.nameOfDeveloper = nameOfDeveloper;
    }

    public String getZoneOfProject() {
        return zoneOfProject;
    }

    public void setZoneOfProject(String zoneOfProject) {
        this.zoneOfProject = zoneOfProject;
    }

    public String getBookingMonth() {
        return bookingMonth;
    }

    public void setBookingMonth(String bookingMonth) {
        this.bookingMonth = bookingMonth;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(String enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
