We’ll now create a **Spring Boot REST API** demonstrating **all the 🔵 2. Spring Web (REST Controller) annotations** in a **single program**, with a **financial industry theme** again — this time, an **Online Banking REST Service**.

As before, the code will be **descriptive, beginner-friendly**, with **file-level comments** and **line-by-line explanations** written so that a **15-year-old can easily follow the flow**.

---

## 🔵 2. Spring Web (REST Controller) Annotations We’ll Demonstrate

* `@RestController` – build REST endpoints that return JSON.
* `@Controller` – for web controllers (we’ll add a small example for contrast).
* `@ResponseBody` – show explicitly returning raw data.
* `@RequestMapping` – map URLs to methods/classes.
* `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping` – handle HTTP methods.
* `@RequestParam` – capture **query parameters**.
* `@PathVariable` – capture **values from URL paths**.
* `@RequestBody` – bind incoming JSON to Java objects.
* `@RequestHeader` – read HTTP header values.
* `@CookieValue` – read cookies.
* `@CrossOrigin` – allow cross-origin requests (CORS).
* `@ExceptionHandler` – handle exceptions gracefully.
* `@ControllerAdvice` – global exception handling.
* `@ResponseStatus` – define custom HTTP status codes.

---

## 🏦 Project: “Bank REST API Service”

This app exposes endpoints to:

* **Create customer accounts**
* **Check balance**
* **Deposit & withdraw money**
* **List all accounts**

---

## 🗂️ Project Structure

```
bank-rest-api/
 ├─ src/main/java/com/example/bank/
 │   ├─ BankRestApiApplication.java       # Main starter class
 │   ├─ controller/
 │   │     ├─ AccountController.java      # REST endpoints using annotations
 │   │     ├─ WebPageController.java      # Example of @Controller
 │   │     └─ GlobalExceptionHandler.java # Handles API errors globally
 │   ├─ model/
 │   │     └─ Account.java                 # Account entity (no DB, in-memory)
 │   ├─ service/
 │   │     └─ AccountService.java          # Service logic
 └─ src/main/resources/
       └─ application.properties
```

---

### 1️⃣ **`BankRestApiApplication.java`**

```java
// File: src/main/java/com/example/bank/BankRestApiApplication.java

package com.example.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * This is the entry point of our REST API Banking Service.
 * 
 * @SpringBootApplication tells Spring Boot:
 *   - “I’m the main class.”
 *   - “Scan all other packages for controllers & services.”
 * 
 * When we run this, an embedded web server (Tomcat) starts,
 * and our REST endpoints become available on localhost:8080.
 * *************************************************************
 */
@SpringBootApplication
public class BankRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankRestApiApplication.class, args);
    }
}
```

---

### 2️⃣ **`Account.java` (Model)**

```java
// File: src/main/java/com/example/bank/model/Account.java

package com.example.bank.model;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * A simple Java class that represents a Bank Account.
 * We’ll use it as a data model for REST requests/responses.
 * *************************************************************
 */
public class Account {
    private String accountId;
    private String customerName;
    private double balance;

    // --- Constructors ---
    public Account() { }  // Needed for @RequestBody to work

    public Account(String accountId, String customerName, double balance) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.balance = balance;
    }

    // --- Getters & Setters ---
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
```

---

### 3️⃣ **`AccountService.java`**

```java
// File: src/main/java/com/example/bank/service/AccountService.java

package com.example.bank.service;

import com.example.bank.model.Account;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * A simple service class that stores accounts in memory
 * (using a HashMap instead of a database for simplicity).
 *
 * @Service makes it a Spring-managed service component.
 * *************************************************************
 */
@Service
public class AccountService {

    // Simulate DB with a HashMap
    private final Map<String, Account> accounts = new HashMap<>();

    // Create a new account
    public Account createAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }

    // Get account by ID
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    // Deposit money
    public Account deposit(String id, double amount) {
        Account acc = accounts.get(id);
        if (acc != null) {
            acc.setBalance(acc.getBalance() + amount);
        }
        return acc;
    }

    // List all accounts
    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }
}
```

---

### 4️⃣ **`AccountController.java` (Main REST Controller)**

```java
// File: src/main/java/com/example/bank/controller/AccountController.java

package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * This REST controller exposes various HTTP endpoints
 * for managing bank accounts.
 *
 * @RestController: marks the class as a REST controller
 * returning JSON responses instead of web pages.
 *
 * @RequestMapping("/api/accounts"):
 * Sets the base URL for all account-related APIs.
 * *************************************************************
 */
@RestController
@RequestMapping("/api/accounts") // Base path for all endpoints here
@CrossOrigin(origins = "http://localhost:3000") // allow requests from frontend running on another port
public class AccountController {

    private final AccountService accountService;

    // Constructor Injection
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create a new account
     * - @PostMapping: listens to HTTP POST /api/accounts/create
     * - @RequestBody: maps JSON from request to Account object
     */
    @PostMapping("/create")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = accountService.createAccount(account);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    /**
     * Get account by ID
     * - @GetMapping: handles GET request
     * - @PathVariable: captures {id} from URL
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable String id) {
        Account acc = accountService.getAccount(id);
        if (acc == null)
            throw new RuntimeException("Account not found!");
        return ResponseEntity.ok(acc);
    }

    /**
     * Deposit money
     * - @PutMapping: used for updating resources
     * - @RequestParam: reads ?amount= in query string
     * Example: PUT /api/accounts/deposit/101?amount=500
     */
    @PutMapping("/deposit/{id}")
    public ResponseEntity<Account> deposit(
            @PathVariable String id,
            @RequestParam double amount,
            @RequestHeader(value = "X-User-Agent", defaultValue = "Unknown") String userAgent) {

        System.out.println("Deposit request came from client: " + userAgent);
        Account updated = accountService.deposit(id, amount);
        if (updated == null)
            throw new RuntimeException("Account not found for deposit!");
        return ResponseEntity.ok(updated);
    }

    /**
     * Get all accounts
     * - @GetMapping: returns list of all accounts
     */
    @GetMapping
    public Collection<Account> listAllAccounts() {
        return accountService.getAllAccounts();
    }

    /**
     * Example of returning plain text using @ResponseBody explicitly
     */
    @GetMapping("/welcome")
    @ResponseBody
    public String welcomeMessage(@CookieValue(value = "username", defaultValue = "Guest") String user) {
        return "Welcome back, " + user + "! Manage your accounts here.";
    }
}
```

---

### 5️⃣ **`WebPageController.java` (Example of @Controller)**

```java
// File: src/main/java/com/example/bank/controller/WebPageController.java

package com.example.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * This class shows the difference between @Controller and
 * @RestController. It returns a view name (like a webpage).
 * 
 * In a real app, you would have templates to render.
 * *************************************************************
 */
@Controller
public class WebPageController {

    @GetMapping("/home")
    public String homePage() {
        // Instead of JSON, it returns the view name "home"
        return "home"; // Suppose we have home.html template
    }
}
```

---

### 6️⃣ **`GlobalExceptionHandler.java`**

```java
// File: src/main/java/com/example/bank/controller/GlobalExceptionHandler.java

package com.example.bank.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * *************************************************************
 * FILE-LEVEL COMMENT
 * -------------------------------------------------------------
 * This class handles exceptions thrown by all controllers.
 *
 * @ControllerAdvice: makes it global.
 * @ExceptionHandler: handles specific exceptions.
 * @ResponseStatus: defines custom status code.
 * *************************************************************
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle all RuntimeExceptions globally
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
```

---

### 7️⃣ **`application.properties`**

```properties
# Default Spring Boot config
server.port=8080
spring.main.banner-mode=console
```

---

## ▶️ **How It Works — Step-by-Step**

1. **App Starts:**

   * Run `BankRestApiApplication`.
   * Spring Boot scans for components and starts an embedded web server on port 8080.

2. **REST Endpoints Become Available:**

   * `POST /api/accounts/create` → create an account (uses `@RequestBody`).
   * `GET /api/accounts/{id}` → get account details (uses `@PathVariable`).
   * `PUT /api/accounts/deposit/{id}?amount=500` → deposit money (uses `@RequestParam` & `@RequestHeader`).
   * `GET /api/accounts` → list all accounts.
   * `GET /api/accounts/welcome` → uses `@CookieValue`.

3. **Global Error Handling:**

   * If you try `GET /api/accounts/999` and it doesn’t exist, the exception handler returns a **404 error** with a friendly message.

4. **Cross-Origin:**

   * `@CrossOrigin` allows requests from frontends hosted on other domains/ports (like React app on port 3000).

---

## ✨ KEY TAKEAWAYS

* **@RestController vs @Controller:**
  REST returns **JSON/data**, Controller returns **web pages/views**.

* **Mapping Annotations:**
  `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`, `@PatchMapping` make code cleaner than old `@RequestMapping(method=…)`.

* **Parameter Binding:**

  * `@RequestParam` → query string `?amount=100`
  * `@PathVariable` → part of URL `/deposit/{id}`
  * `@RequestBody` → JSON body → Java object
  * `@RequestHeader` → read headers like `User-Agent`
  * `@CookieValue` → extract cookie data

* **Error Handling:**
  `@ControllerAdvice` + `@ExceptionHandler` improve user experience with neat error messages.

---

