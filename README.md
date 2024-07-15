# Conference Room Booking

The set of APIs for managing conference room reservations within an organization. 
This system enables users to check room availability, book rooms for specific time slots, and manage existing reservations.

## Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Build and Run](#build-and-run)
- [In Memory Database](#in-memory-database)
- [Liquibase Integration](#liquibase-integration)
- [Swagger](#swagger)
- [Scheduled Task](#scheduled-task)
- [Postman Collection](#postman-collection)
- [Further Improvements](#further-improvements)

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

## In Memory Database
Currently, application is configured to run with H2 database accessible at url [http://localhost:8080/h2-console/login.jsp]() 
with following properties.

```
   JDBC URL : jdbc:h2:mem:conference_room_booking
   User name : sa
```   


## Liquibase Integration
Liquibase is used for database migrations. Ensure that Liquibase is enabled in your `application.properties`.
During the startup, we setup some test data to play with the APIs. 

Test data is defined in **data-setup.sql** file.


## Swagger
Swagger is available on http://localhost:8080/swagger-ui/index.html

## Scheduled Task
The application includes a scheduled task **ConferenceRoomReleaseScheduler** to automatically release conference rooms 
when their booking time has ended.

The scheduler finds out all the expired booking from now to an hour ago, this is configurable by the property

`app.scheduler.roomRelease.timeIntervalHour=1`

Also, this scheduler runs every minute, this is also configurable by the property

`app.scheduler.roomRelease.fixedRate=60000`

## Postman Collection

Please find the Postman collection in the repository `/resources/postmanCollection`.

## Further Improvements
1. Make APIs secure using OAuth2.
2. Background task or a job to prune the previous bookings, similar to **ConferenceRoomReleaseScheduler**. 
3. Deploy the application on the cloud.
4. User Interface to access the APIs by the internal employees.
