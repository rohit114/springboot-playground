### Introduction
* Welcome to the Car Service Scheduler Spring Boot application! This application allows customers to book appointments for car services and enables operators to manage these appointments efficiently. The system is built with Spring Boot, PostgreSQL, Liquibase for database migrations, Lombok and MapStruct for object mapping, and includes comprehensive error handling mechanisms.

* The Car Service Scheduler application is designed to streamline the process of booking and managing car service appointments. It provides the following features:
    * Book Appointment
    * Reschedule appointment
    * Cancel appointment
    * View booked slots
    * View available slots
  
### Tech stack used:
* Java 21+
* PostgreSQL V14.1.0+
* Maven V3.6+
* IDE (e.g., IntelliJ IDEA, Eclipse)

### Setting up project:
```
1.  clone the repo: https://github.com/rohit114/springboot-playground.git
2.  cd bspringboot-playground
3. switch git branch to car-service-scheduler
4. create datbase "car_service_scheduler" in postgres
5. add postgres sql url in application.properties |  spring.datasource.url= jdbc:postgresql://localhost:5432/car_service_scheduler
6. build and run
7. liquibase will created the initial data and seed the customer and operator with sample data

```

### DB schema
``
https://drive.google.com/file/d/1WPi7E44YWaDxL66QnNLxvtRlSuq6KaZq/view?usp=sharing
``

### API Documentation:

1. Book appointment:
    * METHOD: `POST`
    * URL: `{{BASE_URL}}/api/appointments/book`
    * BODY: `{"customer_id":2,"appointment_date":"2024-10-08","start_time":"18:00","operator_id":1}`
    * api returns booked response

2. Reschedule appointment:
    * METHOD: `PUT`
    * URL: `{{BASE_URL}}/api/appointments/reschedule/{appointmentId}`
    * BODY: `{"new_date":"2024-10-08","new_start_time":"17:00","new_operator_id":1}`
    * api returns rescheduled response

3. Cancel appointment:
    * METHOD: `DELETE`
    * URL: `{{BASE_URL}}/api/appointments/cancel/{appointmentId}`
    * api returns cancel acknowledgement

4. List Booked appointments:
    * METHOD: `GET`
    * URL: `{{BASE_URL}}/api/appointments/booked?operator_id=1&date=2024-10-08`
    * Api will return list of booked slots

5. List Open Slots:
    * METHOD: `GET`
    * HEADER: Authorization
    * URL: `{{BASE_URL}}/api/appointments/open-slots?operator_id=1&date=2024-10-08`
    * Api will return list of open slots

* You can find the all curl of the apis in the postman collection `car-service-scheduler.postman_collection.json` inside the car-service-scheduler repo

## Stay in touch
- email me at rohitkumardas114@gmail.com for support or reporting any issues
- Linkedin - [Rohit Kumar](https://www.linkedin.com/in/rohit-kumar-das/)
