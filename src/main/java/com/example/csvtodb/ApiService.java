package com.example.csvtodb;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.qa.blox.co.in/crm/").build();
    }

    public void insertData(JSONModel model) {
        String bookingId = createBooking(model);
        if (bookingId != null) {
            createCustomers(bookingId, model);
            updateBooking(bookingId);
            createPassback(bookingId, model);
            updatePassback(bookingId, model);
            setPaymentSchedule(bookingId);
            authorizeBooking(bookingId);
            updateInstallmentStatus(bookingId);
            generateInvoice(bookingId, model);
        } else {
            System.out.println("Failed to get bookingId");
        }
    }

    private String createBooking(JSONModel model) {
        BookingInfo bookingInfo = model.getBookingInfo();
        ProjectDetails projectDetails = model.getProjectDetails();

        if (bookingInfo != null && projectDetails != null) {
            return webClient.post()
                    .uri("/createBooking")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(createBookingPayload(bookingInfo, projectDetails)))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .map(response -> {
                        if (Boolean.TRUE.equals(response.get("status"))) {
                            return (String) response.get("bookingId");
                        } else {
                            System.out.println("Error creating booking: " + response.get("errorMessage"));
                            return null;
                        }
                    })
                    .block();
        } else {
            System.out.println("BookingInfo or ProjectDetails is missing in the provided JSON model");
            return null;
        }
    }

    private Map<String, Object> createBookingPayload(BookingInfo bookingInfo, ProjectDetails projectDetails) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("costSheetId", bookingInfo.getSeriesNo());
        payload.put("dateOfBooking", bookingInfo.getDateOfBooking());
        payload.put("rmId", 1);
        payload.put("projectId", 1);
        payload.put("towerId", 1);
        payload.put("propertyName", bookingInfo.getProjectName());
        payload.put("enquiryId", projectDetails.getEnquiryId());
        return payload;
    }

    private void createCustomers(String bookingId, JSONModel model) {
        webClient.post()
                .uri("/createCustomers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(createCustomersPayload(bookingId, model)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createCustomersPayload(String bookingId, JSONModel model) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookingId", bookingId);

        Map<String, Object> customer = new HashMap<>();
        customer.put("name", "Applicant_name");
        customer.put("phone", "Applicant_phone");
        customer.put("emailId", "Applicant_email");
        customer.put("address", "Address");

        payload.put("customersList", List.of(customer));
        return payload;
    }

    private void updateBooking(String bookingId) {
        webClient.post()
                .uri("/updateBooking/" + bookingId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(createUpdateBookingPayload()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createUpdateBookingPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("collectionManagerId", 827);
        return payload;
    }

    private void createPassback(String bookingId, JSONModel model) {
        webClient.post()
                .uri("/createPassback")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(createPassbackPayload(bookingId, model)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createPassbackPayload(String bookingId, JSONModel model) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookingId", bookingId);
        payload.put("amount", model.getFinancialDetails().getPassbackAmount());
        payload.put("passbackType", model.getFinancialDetails().getPassbackType());
        payload.put("details", "Added");
        return payload;
    }

    private void updatePassback(String bookingId, JSONModel model) {
        webClient.post()
                .uri("/updatePassback")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(createUpdatePassbackPayload(bookingId, model)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createUpdatePassbackPayload(String bookingId, JSONModel model) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookingId", bookingId);
        payload.put("amount", model.getFinancialDetails().getPassbackAmount());
        payload.put("passbackType", model.getFinancialDetails().getPassbackType());
        payload.put("details", "Comment Added");
        return payload;
    }

    private void setPaymentSchedule(String bookingId) {
        webClient.post()
                .uri("/setPaymentSchedule")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(createPaymentSchedulePayload(bookingId)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private Map<String, Object> createPaymentSchedulePayload(String bookingId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("bookingId", bookingId);

        Map<String, Object> breakdown1 = new HashMap<>();
        breakdown1.put("dueDate", "2024-06-26");
        breakdown1.put("amountPayable", 1000000);
        breakdown1.put("percentOfTotal", 0);
        breakdown1.put("details", "first payment");
        breakdown1.put("initiateBrokeragePayment", false);
        breakdown1.put("breakdown", createBreakdownMap(1000000, 0, 0));

        Map<String, Object> breakdown2 = new HashMap<>();
        breakdown2.put("dueDate", "2024-07-26");
        breakdown2.put("amountPayable", 500000);
        breakdown2.put("percentOfTotal", 0);
        breakdown2.put("details", "sdr");
        breakdown2.put("initiateBrokeragePayment", false);
        breakdown2.put("breakdown", createBreakdownMap(500000, 0, 0));

        Map<String, Object> breakdown3 = new HashMap<>();
        breakdown3.put("dueDate", "2024-08-26");
        breakdown3.put("amountPayable", 400000);
        breakdown3.put("percentOfTotal", 0);
        breakdown3.put("details", "10% payment");
        breakdown3.put("initiateBrokeragePayment", true);
        breakdown3.put("breakdown", createBreakdownMap(400000, 0, 0));

        payload.put("breakdown", List.of(breakdown1, breakdown2, breakdown3));
        return payload;
    }

    private Map<String, Object> createBreakdownMap(double baseAmount, double tds, double gst) {
        Map<String, Object> breakdown = new HashMap<>();
        breakdown.put("baseAmount", baseAmount);
        breakdown.put("tds", tds);
        breakdown.put("gst", gst);
        return breakdown;
    }

    private void authorizeBooking(String bookingId) {
        webClient.post()
                .uri("/authoriseBooking/" + bookingId + "/True")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private void updateInstallmentStatus(String bookingId) {
        String paymentId = "dummyPaymentId";
        String installmentId_1 = "dummyInstallmentId";

        webClient.post()
                .uri("/updateStatus/" + paymentId + "/installment/" + installmentId_1 + "/PAID")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private void generateInvoice(String bookingId, JSONModel model) {
        double invoiceAmount = model.getFinancialDetails().getGrossBloxRevenue();

        webClient.post()
                .uri("/generateInvoice")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(Map.of(
                        "invoiceAmount", invoiceAmount,
                        "bookingId", bookingId
                )))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
