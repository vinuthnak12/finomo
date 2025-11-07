# Finomo Spring Boot Application

This project is a **Spring Boot application** connected to a **MySQL database**, deployed on **Kubernetes**. It demonstrates CRUD operations on financial data and exposes a **REST API** with **Swagger documentation**.

---

## Table of Contents

- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Environment Configuration](#environment-configuration)
- [Kubernetes Deployment](#kubernetes-deployment)
- [Accessing the Application](#accessing-the-application)
- [Swagger API Documentation](#swagger-api-documentation)
- [Logs & Monitoring](#logs--monitoring)
- [License](#license)

---

## Architecture

- **Spring Boot** - REST API backend  
- **MySQL** - Database  
- **Docker** - Containerization  
- **Kubernetes** - Deployment and Service management  
- **Swagger** - API documentation  

The application uses **HikariCP connection pool** to connect to MySQL.

---

## Prerequisites

- Docker  
- Kubernetes (Minikube / EKS / GKE / AKS)  
- kubectl CLI  
- Java 17+  

---

## Project Structure

