# 💰 Finance Dashboard Backend

> **ZORVYN BACKEND – BACKEND DEVELOPER INTERN**
> Finance Data Processing and Access Control Backend


---

## 🌍 Live URL

```
https://finance-dashboard-backend1.onrender.com
```

> ⚠️ Free tier — first request may take up to 50 seconds to wake up.

---

## 📌 Project Overview

This backend system manages:

- Users with different roles (ADMIN, ANALYST, VIEWER)
- Financial records (Income & Expense)
- Dashboard summaries (Total, Monthly, Weekly)
- Role-Based Access Control using Spring Security + JWT

---

## 🛠️ Tech Stack

| Technology      | Details                  |
|-----------------|--------------------------|
| Backend         | Spring Boot (Java)       |
| Security        | Spring Security + JWT    |
| Database        | H2 (In-Memory)           |
| ORM             | Hibernate / JPA          |
| API Testing     | Postman                  |
| Build Tool      | Maven                    |
| Deployment      | Render (Docker)          |

---

## 👥 User Roles & Permissions

| Role     | Permissions                          |
|----------|--------------------------------------|
| ADMIN    | Full CRUD (Create, Update, Delete)   |
| ANALYST  | View records + analytics             |
| VIEWER   | Only view dashboard                  |

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/arunmseccse25-sketch/finance-dashboard-backend.git
cd finance-dashboard-backend/dashboard
```

### 2. Run the Application

```bash
mvn clean install -DskipTests
java -jar target/*.jar
```

### 3. Open Postman for Testing

---

## 📡 API Endpoints

### 👤 Register

**Register ADMIN**
```
POST https://finance-dashboard-backend1.onrender.com/auth/register
```
```json
{
  "username": "kumar_manager",
  "fullName": "Arun Kumar Manager",
  "email": "arun_manager@gmail.com",
  "password": "Manager@2026",
  "role": "ADMIN",
  "status": "ACTIVE"
}
```

**Register ANALYST**
```
POST https://finance-dashboard-backend1.onrender.com/auth/register
```
```json
{
  "username": "arun_analyst",
  "fullName": "Arun Analyst",
  "email": "arun_analyst@gmail.com",
  "password": "Analyst@2025",
  "role": "ANALYST"
}
```

**Register VIEWER**
```
POST https://finance-dashboard-backend1.onrender.com/auth/register
```
```json
{
  "username": "arun_viewer",
  "fullName": "Arun Viewer",
  "email": "arun_viewer@gmail.com",
  "password": "Viewer@2025",
  "role": "VIEWER"
}
```

---

### 🔐 Login (Get JWT Token)

**ADMIN Login**
```
POST https://finance-dashboard-backend1.onrender.com/auth/login
```
```json
{
  "email": "arun_manager@gmail.com",
  "password": "Manager@2026"
}
```

**ANALYST Login**
```
POST https://finance-dashboard-backend1.onrender.com/auth/login
```
```json
{
  "email": "arun_analyst@gmail.com",
  "password": "Analyst@2025"
}
```

**VIEWER Login**
```
POST https://finance-dashboard-backend1.onrender.com/auth/login
```
```json
{
  "email": "arun_viewer@gmail.com",
  "password": "Viewer@2025"
}
```

---

### 🔑 Add JWT Token in Header

After login, copy the token and add to every request:

```
Authorization: Bearer <your_token>
```

| Scenario    | Response         |
|-------------|------------------|
| No token    | 401 Unauthorized |
| Wrong role  | 403 Forbidden    |

---

### 💰 Financial Record Operations

**Add Record (ADMIN)**
```
POST https://finance-dashboard-backend1.onrender.com/records
```
```json
{
  "amount": 5000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "description": "April Month Salary",
  "userId": 1
}
```

**Update Record (ADMIN)**
```
PUT https://finance-dashboard-backend1.onrender.com/records/1
```
```json
{
  "amount": 6000.00,
  "type": "INCOME",
  "category": "Salary",
  "date": "2026-04-01",
  "description": "Updated Salary Amount",
  "userId": 1
}
```

**Delete Record (ADMIN)**
```
DELETE https://finance-dashboard-backend1.onrender.com/records/3
```

**View All Records (ANALYST / ADMIN)**
```
GET https://finance-dashboard-backend1.onrender.com/records
```

**Filter By Category**
```
GET https://finance-dashboard-backend1.onrender.com/records/category?category=Food
```

**Filter By Type**
```
GET https://finance-dashboard-backend1.onrender.com/records/type?type=INCOME
```

---

### 📊 Dashboard APIs

**Total Summary**
```
GET https://finance-dashboard-backend1.onrender.com/api/dashboard/summary
```
```json
{
  "income": 10000.00,
  "balance": 10000.00,
  "expense": 0
}
```

**Monthly Summary**
```
GET https://finance-dashboard-backend1.onrender.com/api/dashboard/trends/monthly?type=EXPENSE&year=2026
```

**Weekly Summary**
```
GET https://finance-dashboard-backend1.onrender.com/api/dashboard/trends/weekly
```

---

## ❗ Error Handling

| Error              | Response                                                        |
|--------------------|-----------------------------------------------------------------|
| Unauthorized       | `{"message": "Unauthorized. Please login first."}`             |
| Access Denied      | `{"message": "Access denied. You are not authorized."}`        |
| Not Found          | `{"message": "Cannot delete: User not found with id: 9"}`      |

---

## ✅ Features Implemented

- ✅ Role-Based Access Control (ADMIN, ANALYST, VIEWER)
- ✅ JWT Authentication
- ✅ CRUD Operations on Financial Records
- ✅ Dashboard Summary APIs
- ✅ Validation & Error Handling
- ✅ Clean API Structure
- ✅ Deployed on Render using Docker

---

## ⚠️ Important Notes

- Always **register first**, then **login**
- Use **Bearer Token** in Authorization header
- Follow **role permissions** strictly
- H2 in-memory DB: **data resets** on every app restart
- Free tier: app **sleeps after inactivity** — first request takes ~50 seconds

---

## 👨‍💻 Developer

**Arun Kumar** — Backend Developer Intern @ Zorvyn
