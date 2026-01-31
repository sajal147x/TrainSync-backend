# TrainSync – Backend (Spring Boot)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

This repository contains the **backend** for **<App Name>**, built using **Java Spring Boot** and following a clean **Controller → Service → Repository** architecture.

The backend exposes REST APIs consumed by the **React Native + Expo frontend**, which is maintained in a separate repository.

 **Frontend repository:**  
🔗 https://github.com/sajal147x/-TrainSync-react-native

---

## Features

- RESTful APIs using **Spring Boot**
- Clean **Controller–Service–Repository** architecture
- **Lombok** for reduced boilerplate
- **Spring Data JPA** for persistence
- Configurable environments (dev / prod)
- Designed for mobile-first API consumption

---

## Tech Stack

- Java 21+
- Spring Boot
- Spring Data JPA
- Lombok
- PostgreSQL (configurable)
- Maven

---

##  Architecture Overview

### Layer Responsibilities

- **Controller**
  - Handles HTTP requests and responses
  - Performs request validation
  - Delegates business logic to services

- **Service**
  - Contains business logic
  - Transaction management
  - Orchestrates repository calls

- **Repository**
  - Data access layer
  - Uses Spring Data JPA
  - No business logic




