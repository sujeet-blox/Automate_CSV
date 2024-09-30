package com.example.csvtodb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CsvtodbApplication implements CommandLineRunner {

	@Autowired
	private ApiService apiService;

	public static void main(String[] args) {
		SpringApplication.run(CsvtodbApplication.class, args);
	}


	public void run(String... args) throws Exception {
		System.out.println("Application Started");

		try {
			List<JSONModel> models = convertCsvToModels("src/main/resources/postsales.csv");
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(models);
			System.out.println("Converted JSON:");
			System.out.println(json);
			for (JSONModel model : models) {
				apiService.insertData(model);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<JSONModel> convertCsvToModels(String csvFilePath) throws IOException {
		Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

		CSVFormat format = CSVFormat.DEFAULT
				.withFirstRecordAsHeader()
				.withIgnoreHeaderCase()
				.withTrim()
				.withIgnoreEmptyLines();

		CSVParser csvParser;

		try {
			csvParser = new CSVParser(reader, format);
		} catch (IllegalArgumentException e) {
			reader = Files.newBufferedReader(Paths.get(csvFilePath));
			format = CSVFormat.DEFAULT.withIgnoreEmptyLines();
			csvParser = new CSVParser(reader, format);
		}

		List<JSONModel> models = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();

		for (CSVRecord csvRecord : csvParser) {
			JSONModel model = new JSONModel();

            model.setUniqueCode(csvRecord.get("Unique Code"));

			// Map CSV data to BookingInfo
			BookingInfo bookingInfo = new BookingInfo();
			bookingInfo.setSrNo(parseInteger(csvRecord.get("Sr No")));
			bookingInfo.setZone(csvRecord.get("Zone"));
			bookingInfo.setTypeOfBooking(csvRecord.get("Type of Booking"));
			bookingInfo.setDevelopersName(csvRecord.get("Developers Name"));
			bookingInfo.setProjectName(csvRecord.get("Project / Unit Name"));
			bookingInfo.setUnit(csvRecord.get("Unit"));
			bookingInfo.setCarpetArea(ParseLong(csvRecord.get("Carpet Area")));
			bookingInfo.setWingTower(csvRecord.get("Wing / Tower"));
			bookingInfo.setSeriesNo(csvRecord.get("Series No"));
			bookingInfo.setDateOfVisitRevisit(csvRecord.get("Date of Visit / Revisit"));
			bookingInfo.setDateOfBooking(csvRecord.get("Date of Booking"));
			bookingInfo.setCarParking(csvRecord.get("Car Parking  (yes/No)"));

			// Map CSV data to FinancialDetails
			FinancialDetails financialDetails = new FinancialDetails();
			financialDetails.setBoxPsf(ParseLong(csvRecord.get("Box psf")));
			financialDetails.setAgreementValue(Long.parseLong(csvRecord.get("Agreement Value")));
			financialDetails.setStampDuty(ParseLong(csvRecord.get("Stamp Duty")));
			financialDetails.setGst(ParseLong(csvRecord.get("GST")));
			financialDetails.setRegistration(ParseLong(csvRecord.get("Registration")));
			financialDetails.setTotalTaxes(ParseLong(csvRecord.get("Total Taxes")));
			financialDetails.setBilledToDeveloper(Long.parseLong(csvRecord.get("Billed To Developer")));
			financialDetails.setGmvAllInGovtTaxesCharges(ParseLong(csvRecord.get("GMV ( All in - Govt Taxes & Charges )")));
			financialDetails.setBrokerageApplicable(csvRecord.get("Brokerage Applicable"));
			financialDetails.setBrokerageKicker(ParseLong(csvRecord.get("Brokerage Kicker")));
			financialDetails.setGrossBloxRevenue(Double.parseDouble(csvRecord.get("Gross Blox Revenue")));
			financialDetails.setPassbackGiven(ParseLong(csvRecord.get("Passback Given")));
			financialDetails.setBloxNetRevenue(Double.parseDouble(csvRecord.get("Blox Net Revenue")));
			financialDetails.setAt1Percent(ParseLong(csvRecord.get("@1%")));
			financialDetails.setPaymentRecd(ParseLong(csvRecord.get("Payment Recd")));
			financialDetails.setPayRecdPercent(ParseLong(csvRecord.get("Payment Recd")));

			// Map CSV data to AgreementDetails
			AgreementDetails agreementDetails = new AgreementDetails();
			agreementDetails.setAgreementStatus(csvRecord.get("Agreement Status (Yes / No) (Insert Date)"));
			agreementDetails.setDateOfCriteriaOneOrTwo(csvRecord.get("Date of Criteria one or two whicever is earlier"));
			agreementDetails.setInvoiceIssued(csvRecord.get("Invoice issued (Yes/No)"));
			agreementDetails.setInvoiceDate(csvRecord.get("Invoice Date"));
			agreementDetails.setPaymentDueDate(csvRecord.get("Payment Due Date"));
			agreementDetails.setPaymentStatusRecd(csvRecord.get("Payment Status (Recd Yes / No)"));
			agreementDetails.setPassbackComments(csvRecord.get("Passback Comments"));

			// Map CSV data to SourcingCredits
			SourcingCredits sourcingCredits = new SourcingCredits();
			sourcingCredits.setSourcingCreditsDept(csvRecord.get("Sourcing Credits Dept"));
			sourcingCredits.setSourcingManager(csvRecord.get("Sourcing Manager"));
			sourcingCredits.setPortfolioRmClosingCredits(csvRecord.get("Portfolio RM & Closing Credits"));
			sourcingCredits.setRmTl(csvRecord.get("RM TL"));
			sourcingCredits.setRmHead(csvRecord.get("RM Head"));

			// Map CSV data to FollowUpDetails
			FollowUpDetails followUpDetails = new FollowUpDetails();
			followUpDetails.setRemarks(csvRecord.get("Remarks"));
			followUpDetails.setSpotReward(csvRecord.get("Spot Reward"));
			followUpDetails.setBaseIncentiveStatus(csvRecord.get("Base Incentive Status"));
			followUpDetails.setMasterCurrentStatus(csvRecord.get("Master Current Status"));
			followUpDetails.setNextFollowUpDate(csvRecord.get("Next Follow Up Date (DD/MM/YY)"));

//			// Map CSV data to FinanceApproval
			FinanceApproval financeApproval = new FinanceApproval();
			financeApproval.setHomeFinanceApprovedValue(ParseLong(csvRecord.get("Home Finance Approved Value")));
			financeApproval.setBanksName(csvRecord.get("Bank's Name"));
			financeApproval.setHomeLoanFinanceStatus(csvRecord.get("Home Loan Finance Status"));
			financeApproval.setAccrued(ParseLong(csvRecord.get("Accrued")));
//
//			// Map CSV data to FinalBillingDetails
			FinalBillingDetails finalBillingDetails = new FinalBillingDetails();
			finalBillingDetails.setFinalBilledToDeveloperValue(Long.parseLong(csvRecord.get("Final Billed to Developer Value ( As verifed by Collections )")));
			finalBillingDetails.setFinalEarningToBlox(Double.parseDouble(csvRecord.get("Final Earning To Blox")));
			finalBillingDetails.setRemarksForDifferenceValue(csvRecord.get("Remarks For Difference Value"));

//			// Map CSV data to BillingFollowUp
			BillingFollowUp billingFollowUp = new BillingFollowUp();
			billingFollowUp.setBilledStatusCollectionsTeam(csvRecord.get("Billed Status - Collections Team"));
			billingFollowUp.setBillingFollowUpDate(csvRecord.get("Billing Follow Up -Date DD/MM/YYYY"));
			billingFollowUp.setNoOfDaysLeftToRaiseInvoice(parseInteger(csvRecord.get("No of Days Left to Raise Invoice")));
			billingFollowUp.setRemarksTillBillNotSubmitted(csvRecord.get("Remarks till the bill is not submitted"));
//
//			// Map CSV data to InvoiceDetails
			InvoiceDetails invoiceDetails = new InvoiceDetails();
			invoiceDetails.setInvoiceSubmittedToDeveloperDate(csvRecord.get("Invoice Submitted to developer Date DD/MM/YYYY"));
			invoiceDetails.setInvoiceNumber(csvRecord.get("Invoice Number"));
			invoiceDetails.setTentativePaymentDate(csvRecord.get("Tentative Payment Date"));
			invoiceDetails.setMonthOfExpectedPayment(csvRecord.get("Month of Expected Payment"));
			invoiceDetails.setPaymentFollowUpDate(csvRecord.get("Payment Follow-up Date"));
			invoiceDetails.setRemarksTillPaymentNotReceived(csvRecord.get("Remarks till Payment is not received"));
//
//			// Map CSV data to PaymentDetails
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setReceivedAmount(ParseLong(csvRecord.get("Recieved amount")));
			paymentDetails.setActualPaymentReceivedDate(csvRecord.get("Actual Payment Received Date"));
			paymentDetails.setMonthOfReceivedPayment(csvRecord.get("Month of Received Payment"));
			paymentDetails.setPaymentReferenceId(csvRecord.get("Payment Reference ID"));
			paymentDetails.setValueOfReferenceId(ParseLong(csvRecord.get("Value of Reference ID [Collection Team to  only Fill this]")));
			paymentDetails.setAgeingDaysFromBookedToBilled(parseInteger(csvRecord.get("Ageing ( Days from Booked to Billed )")));
			paymentDetails.setAgeingTentativePaymentDaysFromBillSubmission(parseInteger(csvRecord.get("Ageing ( Tentative Payment Days from Bill Submission )")));
			paymentDetails.setNoOfDaysLeftToExpectPayment(parseInteger(csvRecord.get("No of Days Left to expect the payment")));
			paymentDetails.setAgeingDaysFromBilledToReceived(parseInteger(csvRecord.get("Ageing ( Days from Billed to Received )")));
//
//			// Map CSV data to ProjectDetails
			ProjectDetails projectDetails = new ProjectDetails();
			projectDetails.setNameOfDeveloper(csvRecord.get("Name of the Developer"));
			projectDetails.setZoneOfProject(csvRecord.get("Zone of the Project"));
			projectDetails.setBookingMonth(csvRecord.get("Booking Month"));
			projectDetails.setEnquiryId(csvRecord.get("Enquiry ID"));
			projectDetails.setCustomerName(csvRecord.get("Name of the Buyer"));

			// Populate the main model
			model.setBookingInfo(bookingInfo);
			model.setFinancialDetails(financialDetails);
			model.setAgreementDetails(agreementDetails);
			model.setSourcingCredits(sourcingCredits);
			model.setFollowUpDetails(followUpDetails);
			model.setFinanceApproval(financeApproval);
			model.setFinalBillingDetails(finalBillingDetails);
			model.setBillingFollowUp(billingFollowUp);
			model.setInvoiceDetails(invoiceDetails);
			model.setPaymentDetails(paymentDetails);
			model.setProjectDetails(projectDetails);

			models.add(model);
		}

		csvParser.close();
		reader.close();

		return models;
	}

	private static long ParseLong(String value) {
		try {
			if (value != null && !value.trim().isEmpty()) {
				return Long.parseLong(value.trim());
			}
		} catch (NumberFormatException e) {
			// Log the exception if necessary
			System.err.println("Invalid number format for value: " + value + ". Returning default value: 0");
		}
		return 0L;
	}

	private static Integer parseInteger(String value) {
		try {
			return value != null && !value.trim().isEmpty() ? Integer.parseInt(value.trim()) : null;
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
