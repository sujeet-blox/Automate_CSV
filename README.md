# CSV to DB Integration

This project demonstrates how to integrate and manage a series of API calls using Spring Boot and WebClient. The goal is to process data from a JSON model, create bookings, manage customers, handle payments, and generate invoices through various API endpoints.

## Table of Contents
-[Screen Recording](#screenrecording)
- [Setup](#setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Contributing](#contributing)

## ScreenRecording
[Screencast from 06-08-24 01:19:57 AM IST.webm](https://github.com/user-attachments/assets/5706bf05-3e81-47c7-b202-493a1a25385e)

## Setup

1. **Clone the Repository**

    ```bash
    git clone https://github.com/your-repo/csv-to-db.git
    cd csv-to-db
    ```

2. **Install Dependencies**

    Ensure you have Maven installed. Run the following command to install dependencies:

    ```bash
    mvn install
    ```

3. **Configure Application**

    Update the `application.properties` file with the appropriate configuration settings for your API endpoints.

4. **Run the Application**

    Start the Spring Boot application with:

    ```bash
    mvn spring-boot:run
    ```

## Usage

The `ApiService` class handles various API calls. It uses the WebClient from Spring WebFlux to interact with the specified endpoints.

1. **Insert Data**

    Call the `insertData` method from the `ApiService` class, passing in an instance of `JSONModel`. This will trigger a series of API calls:

    ```java
    @Autowired
    private ApiService apiService;

    public void processData() {
        JSONModel model = new JSONModel(); // Populate this model with data
        apiService.insertData(model);
    }
    ```

## API Endpoints

1. **`/createBooking`**

    Creates a booking and returns a `bookingId`.

2. **`/createCustomers`**

    Creates customer records associated with the `bookingId`.

3. **`/updateBooking`**

    Updates the booking with additional details.

4. **`/createPassback`**

    Creates a passback entry associated with the `bookingId`.

5. **`/updatePassback`**

    Updates the passback entry.

6. **`/setPaymentSchedule`**

    Sets the payment schedule for the booking.

7. **`/authoriseBooking`**

    Authorizes the booking with a specified status.

8. **`/updateStatus`**

    Updates the status of a payment installment.

9. **`/generateInvoice`**

    Generates an invoice for the specified `bookingId` and amount.

## Project Structure

- `com.example.csvtodb`: Contains the main application and service classes.
  - `ApiService.java`: Handles API calls and integrates the API endpoints.
  - `JSONModel.java`: Represents the data model.
  - `BookingInfo.java`: Represents booking-related information.
