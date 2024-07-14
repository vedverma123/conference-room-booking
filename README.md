# Conference Room Booking

The set of APIs for managing conference room reservations within an organization. 
This system enables users to check room availability, book rooms for specific time slots, and manage existing reservations.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Build and Run](#build-and-run)
- [Liquibase Integration](#liquibase-integration)
- [Swagger](#swagger)
- [Scheduled Task](#scheduled-task)

## Features
- Check room availability
- Book rooms for specific time slots
- Manage existing reservations

## Prerequisites
- Java 17 or later
- Maven 3.6.3 or later
- H2 Database (in-memory for development)
- SpringBoot 3.3.1

## Build and Run

### How to Build
1. Clone the repository:
    ```sh
    git clone https://github.com/vedverma123/conference-room-booking.git
    cd conference-room-booking
    ```
2. Build the project using Maven:
    ```sh
    ./mvnw clean install
    ```

### How to Run
1. Run the application:
    ```sh
    ./mvnw spring-boot:run
    ```

## Liquibase Integration
Liquibase is used for database migrations. Ensure that Liquibase is enabled in your `application.properties`.

## Swagger
Swagger is available on http://localhost:8080/swagger-ui/index.html

## Scheduled Task
The application includes a scheduled task **ConferenceRoomReleaseScheduler** to automatically release conference rooms when their booking time has ended.
