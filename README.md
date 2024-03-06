# Application for getting a schedule of the group in BSUIR

This repository contains a simple REST API application that provides the ability to obtain group schedules.

## Table of Contents

- [Introduction](#introduction)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Note](#note)


## Introduction

This is a basic REST API application built using [Spring Boot](https://spring.io/projects/spring-boot) framework and [Maven](https://maven.apache.org). The application allows users to get a schedule of all groups in BSUIR by making HTTP requests, with help of Spring WebClient.

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Web framework for building the REST API.
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa): Data access framework for interacting with the database.
- [MySQL](https://www.mysql.com): Database for local and global use.
- [Spring WebFlux](https://spring.io/guides/gs/reactive-rest-service): WebClient is a class provided by the Spring Framework in the spring-webflux module. It is a non-blocking, reactive HTTP client that allows you to make HTTP requests to remote servers.
- [IIS API](https://iis.bsuir.by/api): External API of IIS BSUIR.

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java (version 17 or higher)
- Maven
- MySQL server
- MySQL driver
- JPA
- WebFlux
- Spring Boot

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/vanzoneway/ListOfGroupService.git
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    java -jar target/ListOfGroups-0.0.1-SNAPSHOT.jar
    ```

The application will start on `http://localhost:8080`.

## Usage

### Endpoints

- **Get a list of all employees in BSUIR in JSON and write it in Database MySQL:**

  ```http
  GET /getEmployees
  ```

  You have to do it as 1st step to get the schedule of group
- 
- **Get a schedule of group in BSUIR in JSON and write it in Database MySQL:**

  ```http
  GET /schedule/{groupNumber}
  ```

  Just write a group number in a body of HTTP-request.

  Example:
  ```http
  GET /getEmployees
  ```
  ```http
  GET /schedule/250502
  ```
  
## Note
Upon the first request to /getEmployees, all employees are saved to the MySQL Database. Subsequent requests will update the existing data. To retrieve the schedule of a group, the application fetches data from the IIS API during the initial request, and subsequently retrieves the data from the MySQL Database.


