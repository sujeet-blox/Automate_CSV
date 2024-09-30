package com.example.csvtodb;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

@Service
public class ApiService {

    private final WebClient webClient;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String BASE_URL = "https://api.qa.blox.co.in";

    @Autowired
    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public void insertData(JSONModel model) {
        String enquiryId = model.getProjectDetails().getEnquiryId();
        Map<String, Object> enquiryData = getEnquiryData(enquiryId);
        System.out.println(enquiryData);
        if (enquiryData != null) {
            // To be removed later
            String passbackType = "2";
            int collectionManagerId = 827;

            String projectId = String.valueOf(enquiryData.get("project_id"));
            String rmId = String.valueOf(enquiryData.get("rm_id"));
            String applicantName=String.valueOf(enquiryData.get(("name")));
            String applicantPhone=String.valueOf(enquiryData.get("mobile"));
            String applicantEmail=String.valueOf(enquiryData.get("email"));
            long passbackAmount= model.getFinancialDetails().getPassbackGiven();
            String brokerageApplicable = model.getFinancialDetails().getBrokerageApplicable();

            long agreementValue = model.getFinancialDetails().getAgreementValue();
            if (projectId == null || projectId.equals("null") || projectId.isEmpty() || projectId.equals("0")) {
                ApiLogger.log("Failed to find projectID" );
                return;
            }
            ApiLogger.log("Starting API sequence for RM ID: " + rmId + ", Enquiry ID: " + enquiryId);

            try{
                String token = signInAsRm(rmId, "88888").block();
                if (token == null) {
                    ApiLogger.log("Failed to obtain token for RM ID: " + rmId);
                    return;
                }
//                String jwt = getJwtToken(token).block();
                String jwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MjU5NDUxNzgsImV4cCI6MTcyODUzNzE3OCwidXNlcl9pZCI6MTg2MywibmFtZSI6InN1amVldCBLdW1hciIsImVtYWlsIjoic3VqZWV0Lmt1bWFyQGJsb3gueHl6IiwicGhvbmUiOiI3OTA2ODc1MjI3In0.LFvp5NeFRmOBqQyX3wyw3CPNI0kK9EKnMnZw-Jbag18";
                System.out.println(jwt);
                ApiLogger.log("Obtained JWT token for Enquiry ID: " + enquiryId);
                System.out.println(agreementValue);
                String costSheetUUID= createCostSheet(enquiryId,agreementValue,jwt).block();
                String bookingId = createBooking(model, enquiryId, projectId, rmId, costSheetUUID, jwt);
                if (bookingId.equals("null")) {
                    ApiLogger.log("Failed to create booking for Enquiry ID: " + enquiryId);
                    return;
                }
                updateBooking(bookingId, collectionManagerId, jwt).block();
                addCustomer(bookingId, applicantName, applicantPhone, applicantEmail, jwt).block();
//                createPassback(bookingId, passbackAmount, passbackType, jwt).block();
                setPaymentSchedule(bookingId, agreementValue, brokerageApplicable, jwt).block();

                ApiLogger.log("Completed API sequence successfully for RM ID: " + rmId + ", Enquiry ID: " + enquiryId);
            } catch (Exception e) {
                ApiLogger.log("Error occurred during API sequence for RM ID: " + rmId + ", Enquiry ID: " + enquiryId + ": " + e.getMessage());
                ApiLogger.log("***********************************");
                e.printStackTrace();
            }

        } else {
            System.out.println("Enquiry data not found for enquiryId: " + enquiryId);
        }
    }

    public Mono<String> signInAsRm(String rmId, String otp) {
        String mobileNumber = getMobileNumberForRmId(rmId);
        if (mobileNumber != null) {

            Map<String, Object> signInRequest = Map.of(
                    "mobile", mobileNumber,
                    "country_code", "91",
                    "device", Map.of(
                            "deviceToken", "xxxxxxxxxxxxxxxxx",
                            "deviceId", "9BDD22D9-54B8-4BF8-B3AD-A20314D5C84E",
                            "deviceInfo", "iPhone 12 mini (iOS 15.4.1)",
                            "deviceType", "IOS"
                    )
            );

            return webClient.post()
                    .uri("/rm/v5/rmsignin")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYXV0aGFwaS5xYWFscGhhLmJsb3guY28uaW4vdjEvZ2V0X3Rva2VuIiwiaWF0IjoxNzE4NjgyMjEzLCJleHAiOjQ4NDA3NDYyMTMsIm5iZiI6MTcxODY4MjIxMywianRpIjoiTkJvYmUzMGlLUmE4VWpXWiIsInN1YiI6IjIiLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.otM6q-F7T_X7OSnssxwtAe19WgUK8xZMbSfx1_mMtS8")
                    .body(BodyInserters.fromValue(signInRequest))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .flatMap(response -> {
                        System.out.println("Response received: " + response);
                        if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                            System.out.println("RM Signing in...");
                            return verifyOtp(mobileNumber, otp);
                        } else {
                            System.out.println("Error signing in: " + response.get("errorMessage"));
                            return Mono.empty();
                        }
                    })
                    .doOnError(e -> System.out.println("Error: " + e.getMessage()));
        } else {
            ApiLogger.log("Mobile number not found for RM ID: " + rmId);
            return Mono.empty();
        }
    }

    public Mono<String> verifyOtp(String mobileNumber, String otp) {

        Map<String, Object> otpRequest = Map.of(
                "otp", otp,
                "mobile", mobileNumber,
                "country_code", "91",
                "device_id", "9BDD22D9-54B8-4BF8-B3AD-A20314D5C84E"
        );

        return webClient.post()
                .uri("/rm/v5/rmverify_otp")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vYXV0aGFwaS5xYWFscGhhLmJsb3guY28uaW4vdjEvZ2V0X3Rva2VuIiwiaWF0IjoxNzE4NjgyMjEzLCJleHAiOjQ4NDA3NDYyMTMsIm5iZiI6MTcxODY4MjIxMywianRpIjoiTkJvYmUzMGlLUmE4VWpXWiIsInN1YiI6IjIiLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.otM6q-F7T_X7OSnssxwtAe19WgUK8xZMbSfx1_mMtS8")
                .body(BodyInserters.fromValue(otpRequest))
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    if (response != null && Boolean.TRUE.equals(response.get("success"))) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> data = (Map<String, Object>) response.get("data");
                        String ctoken = (String) data.get("ctoken");
                        if (ctoken != null) {
                            return Mono.just(ctoken);
                        } else {
                            return Mono.empty();
                        }
                    } else {
                        ApiLogger.log("Error verifying OTP: " + response.get("message"));
                        return Mono.empty();
                    }
                });
    }

    private String getMobileNumberForRmId(String rmId) {
        String sql = "SELECT phone FROM admins WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{rmId}, String.class);
        } catch (Exception e) {
            System.err.println("Error retrieving mobile number: " + e.getMessage());
            return null;
        }
    }

    private Map<String, Object> getEnquiryData(String enquiryId) {
        System.out.println(enquiryId);
        String sql = "SELECT * FROM enquiries WHERE enquiry_no_id = ?";
        try {
            return jdbcTemplate.queryForMap(sql, enquiryId);
        } catch (Exception e) {
            ApiLogger.log("Error retrieving enquiry data: " + e.getMessage());
            return null;
        }
    }

    public Mono<String> getJwtToken(String ctoken) {
        return webClient.get()
                .uri("https://authentication-qa.blox.co.in/admin/jwt?token=" + ctoken)
                .retrieve()
                .bodyToMono(String.class)  // Assuming the JWT is returned as plain text
                .doOnNext(jwt -> System.out.println("Received JWT: " + jwt))
                .doOnError(error -> ApiLogger.log("Error fetching JWT: " + error.getMessage()));
    }

    public Mono<String> createCostSheet(String enquiryNoId, long agreementValue, String jwt) {
        Map<String, Object> payload = Map.of(
                "agreementValue",  agreementValue
        );

        return webClient.post()
                .uri("/crm/costSheet/" + enquiryNoId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + jwt)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error during createCostSheet request: " + clientResponse.statusCode() + " - " + errorBody);
                                return Mono.error(new RuntimeException("Create cost sheet failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("Create Cost Sheet Response: " + response);

                    if (Boolean.TRUE.equals(response.get("operationSuccess"))) {
                        ApiLogger.log("CostSheetID Created Successfully");
                        return (String) response.get("costSheetId");
                    } else {
                        ApiLogger.log("Error creating cost sheet: " + response.get("errorMessage"));
                        return null;
                    }
                })
                .doOnError(error ->  ApiLogger.log("Error during createCostSheet request: " + error.getMessage()))
                .onErrorReturn("Fallback response due to error");

    }

    public String createBooking(JSONModel model,String enquiryId, String projectId, String rmId,String costSheetId, String jwt) {
        BookingInfo bookingInfo = model.getBookingInfo();
        ProjectDetails projectDetails = model.getProjectDetails();
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = null;

        try {
            Date date = inputDateFormat.parse(bookingInfo.getDateOfBooking());
            formattedDate = outputDateFormat.format(date);
        } catch (ParseException e) {
            System.err.println("Failed to parse date: " + e.getMessage());
            return null;
        }

        Map<String, Object> payload = Map.of(
                "costSheetId", costSheetId,
                "dateOfBooking", formattedDate,
                "rmId", rmId,
                "projectId", projectId,
                "propertyName", bookingInfo.getProjectName(),
                "enquiryId", enquiryId
        );

        if (bookingInfo != null && projectDetails != null) {
            System.out.println("Payload: " + payload);

            return webClient.post()
                    .uri("/crm/createBooking")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + jwt)
                    .body(BodyInserters.fromValue(payload))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                        // Log the status code and error response
                        return clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.err.println("Error during createBooking request: " + clientResponse.statusCode() + " - " + errorBody);
                                    return Mono.error(new RuntimeException("Create booking failed with status: " + clientResponse.statusCode()));
                                });
                    })
                    .bodyToMono(Map.class)
                    .map(response -> {
                        // Log the response for debugging purposes
//                        System.out.println("Create Booking Response: " + response);

                        if (Boolean.TRUE.equals(response.get("status"))) {
                            ApiLogger.log("Booking created successfully");
                            return (String) response.get("bookingId");
                        } else {
                            // Log error message
                            ApiLogger.log("Error creating booking: " + response.get("errorMessage"));
                            return null;
                        }
                    })
                    .doOnError(error ->  ApiLogger.log("Error during createBooking request: " + error.getMessage()))
                    .onErrorReturn("Fallback response due to error")
                    .block();
        } else {
            ApiLogger.log("BookingInfo or ProjectDetails is missing in the provided JSON model");
            return null;
        }
    }

    public Mono<String> addCustomer(String bookingId, String applicantName, String applicantPhone, String applicantEmail, String jwt) {
        Map<String, Object> customer = Map.of(
                "name", applicantName,
                "phone", applicantPhone,
                "emailId", applicantEmail,
                "address", "Address"
        );

        Map<String, Object> payload = Map.of(
                "bookingId", bookingId,
                "customersList", List.of(customer)
        );

        return webClient.post()
                .uri("/crm/createCustomers")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + jwt)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error during addCustomer request: " + clientResponse.statusCode() + " - " + errorBody);
                                return Mono.error(new RuntimeException("Add customer failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("Add Customer Response: " + response);
                    if (Boolean.TRUE.equals(response.get("operationSuccess"))) {
                        ApiLogger.log("Customer added successfully");
                        return "";
                    } else {
                        String errorMessage = (String) response.get("errorMessage");
                        ApiLogger.log("Error adding customer: " + errorMessage);
                        return "Error adding customer: " + errorMessage;
                    }
                })
                .doOnError(error ->  ApiLogger.log("Error during addCustomer request: " + error.getMessage()))
                .onErrorReturn("Fallback response due to error");
    }

    public Mono<String> createPassback(String bookingId, double passbackAmount, String passbackType, String jwt) {
        Map<String, Object> payload = Map.of(
                "bookingId", bookingId,
                "amount", passbackAmount,
                "passbackType", passbackType,
                "details", "Added"
        );

        return webClient.post()
                .uri("/crm/createPassback")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + jwt)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error during createPassback request: " + clientResponse.statusCode() + " - " + errorBody);
                                return Mono.error(new RuntimeException("Create passback failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("Create Passback Response: " + response);
                    if (Boolean.TRUE.equals(response.get("operationSuccess"))) {
                        ApiLogger.log("Passback created successfully");
                        return "Passback created successfully";
                    } else {
                        String errorMessage = (String) response.get("errorMessage");
                        ApiLogger.log("Error creating passback: " + errorMessage);
                        return "Error creating passback: " + errorMessage;
                    }
                })
                .doOnError(error ->  ApiLogger.log("Error during createPassback request: " + error.getMessage()))
                .onErrorReturn("Fallback response due to error");
    }
    public Mono<String> updateBooking(String bookingId, int collectionManagerId, String jwt) {
        Map<String, Object> payload = Map.of(
                "collectionManagerId", collectionManagerId
        );

        return webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/crm/updateBooking/{bookingId}")
                        .build(bookingId))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + jwt)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error during updateBooking request: " + clientResponse.statusCode() + " - " + errorBody);
                                return Mono.error(new RuntimeException("Update booking failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("Update Booking Response: " + response);
                    if (Boolean.TRUE.equals(response.get("operationSuccess"))) {
                        ApiLogger.log("Update Booking Successfully");
                        return "Booking updated successfully";
                    } else {
                        String errorMessage = (String) response.get("errorMessage");
                        ApiLogger.log("Error updating booking: " + errorMessage);
                        return "Error updating booking: " + errorMessage;
                    }
                })
                .doOnError(error ->  ApiLogger.log("Error during updateBooking request: " + error.getMessage()))
                .onErrorReturn("Fallback response due to error");
    }

    public Mono<String> setPaymentSchedule(String bookingId, long amount,String brokerageApplicable, String jwt) {
        long firstPayment = amount / 2;
        long secondPayment = (amount - firstPayment) / 2;
        long thirdPayment = amount - firstPayment - secondPayment;
        String numericValue = brokerageApplicable.replace("%", "");
        double brokeragePercentage = Double.parseDouble(numericValue);
        LocalDate today = LocalDate.now();
        LocalDate firstDueDate = today.plusMonths(1);
        LocalDate secondDueDate = today.plusMonths(2);
        LocalDate thirdDueDate = today.plusMonths(3);

        Map<String, Object> payload = Map.of(
                "bookingId", bookingId,
                "userExpectedBrokeragePercent", brokeragePercentage,
                "breakdown", List.of(
                        createBreakdownMap(firstDueDate, firstPayment, "First payment", false,amount),
                        createBreakdownMap(secondDueDate, secondPayment, "Second payment", false,amount),
                        createBreakdownMap(thirdDueDate, thirdPayment, "Third payment", false,amount)
                )
        );
        System.out.println(payload);

        return webClient.post()
                .uri("/crm/setPaymentSchedule")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + jwt)
                .body(BodyInserters.fromValue(payload))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error during createPaymentBreakdown request: " + clientResponse.statusCode() + " - " + errorBody);
                                return Mono.error(new RuntimeException("Create payment breakdown failed with status: " + clientResponse.statusCode()));
                            });
                })
                .bodyToMono(Map.class)
                .map(response -> {
//                    System.out.println("Create Payment Breakdown Response: " + response);
                    if (Boolean.TRUE.equals(response.get("operationSuccess"))) {
                        ApiLogger.log("Payment breakdown created successfully");
                        return "Payment breakdown created successfully";
                    } else {
                        String errorMessage = (String) response.get("errorMessage");
                        ApiLogger.log("Error creating payment breakdown: " + errorMessage);
                        return "Error creating payment breakdown: " + errorMessage;
                    }
                })
                .doOnError(error -> System.err.println("Error during createPaymentBreakdown request: " + error.getMessage()))
                .onErrorReturn("Fallback response due to error");
    }

    private Map<String, Object> createBreakdownMap(LocalDate dueDate, long amount, String details, boolean initiateBrokeragePayment,long totalAmount) {
        double percentOfTotal = (amount * 100.0) / totalAmount;
        percentOfTotal = Math.round(percentOfTotal * 100.0) / 100.0;

        return Map.of(
                "dueDate", dueDate.toString(),
                "amountPayable", amount,
                "percentOfTotal", percentOfTotal,
                "details", details,
                "initiateBrokeragePayment", initiateBrokeragePayment,
                "breakdown", Map.of(
                        "baseAmount", amount,
                        "tds", 0,
                        "gst", 0
                )
        );
    }

}
