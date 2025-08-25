# **Order Microservice (Order MS)**

## **Project / Thesis Topic**
**Development of an e-commerce application based on microservices architecture**

## **Description**
This microservice is part of a **master's thesis project**. It manages all order-related operations for the web shop application, including creation, updates, retrieval, and tracking of orders. It uses **Hibernate** for database interactions and is secured with **Keycloak** using **JWT-based authentication** and role-based access control.  
Order MS communicates asynchronously with other services via **Apache Kafka**, receiving events from Product MS and other services.

---

## **Technologies**
- **Java 17+**  
- **Spring Boot**  
- **Hibernate / JPA**  
- **Keycloak** (JWT authentication & authorization)  
- **Apache Kafka**  
- **MySQL**  
- **Maven**

---

## **Prerequisites**
- Java 17+ installed  
- Maven installed  
- Docker & Docker Compose (for Keycloak, Kafka, Zookeeper, and MySQL)

---

## **Ports**
- **Order MS:** 8082  
- **Keycloak:** 8084  
- **Kafka:** 9092  
- **Zookeeper:** 2181  
- **MySQL:** 3307 (or configured in `application.properties`)

---

## **Installation & Running**
1. **Clone the repository:**  
```bash
git clone <repository-url>
cd order-ms
```
2. **Run with Maven:**
```bash
mvn spring-boot:run
```

# **Order Microservice (Order MS) - API Endpoints**

## **Authentication**
- `POST /auth/login` – Login user, returns JWT and refresh token  
- `POST /auth/register` – Register new user  
- `POST /auth/logout` – Logout user, invalidate token  
- `POST /auth/refreshToken` – Refresh JWT token  

## **Items**
- `GET /item` – List all items  
- `GET /item/{id}` – Get item by ID  
- `POST /item` – Create new item (**ADMIN role required**)  
- `PUT /item` – Update item (**ADMIN role required**)  
- `DELETE /item/{id}` – Delete item (**ADMIN role required**)  

## **Shopping Cart**
- `GET /shoppingCart` – List all shopping carts (**USER or ADMIN role required**)  
- `GET /shoppingCart/{id}` – Get shopping cart by ID (**USER or ADMIN role required**)  
- `POST /shoppingCart` – Create new shopping cart (**USER or ADMIN role required**)  
- `PUT /shoppingCart` – Update shopping cart (**USER or ADMIN role required**)  
- `DELETE /shoppingCart/{id}` – Delete shopping cart (**USER or ADMIN role required**)  
- `POST /shoppingCart/checkout/{id}` – Checkout shopping cart (**USER or ADMIN role required**)  

## **Order Events**
- `GET /events/pending` – List all pending events  
- `POST /events/{id}/complete` – Mark event as completed  

## **PayPal Accounts**
- `GET /paypalaccount` – List all PayPal accounts  
- `GET /paypalaccount/{id}` – Get PayPal account by ID  
- `POST /paypalaccount` – Create new PayPal account (**ADMIN role required**)  
- `PUT /paypalaccount` – Update PayPal account (**ADMIN role required**)  
- `DELETE /paypalaccount/{id}` – Delete PayPal account (**ADMIN role required**)  

## **Roles**
- `GET /role` – List all roles  
- `GET /role/{id}` – Get role by ID  
- `POST /role` – Create new role (**ADMIN role required**)  
- `PUT /role/update` – Update role (**ADMIN role required**)  
- `DELETE /role/{id}` – Delete role (**ADMIN role required**)  

## **Users**
- `GET /user` – List all users  
- `GET /user/{id}` – Get user by ID  
- `POST /user` – Create new user (**ADMIN role required**)  
- `PUT /user` – Update user (**ADMIN role required**)  
- `DELETE /user/{id}` – Delete user (**ADMIN role required**)  
- `POST /user/add-new-role` – Add role to user  


## **Authentication**
- Secured via **Keycloak**  
- **JWT token** required for creating, updating, and deleting products and categories  

## **Kafka Integration**
- Order MS receives **events from Product MS** via Kafka  
