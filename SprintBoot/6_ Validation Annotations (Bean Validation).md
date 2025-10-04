Here’s a **complete beginner-friendly demo** for **🟤 6. Validation Annotations (Bean Validation)** in a **Financial Loan Service**.
We’ll stick to the **same protocol**:

* A realistic **Loan Application API**
* **Descriptive file-level headers**
* **Line-by-line comments** so even a 15-year-old can follow the logic
* Demonstrates the most common **Jakarta Bean Validation annotations**

---

```java
/**********************************************************************************************
 * File Path: src/main/java/com/finbank/LoanValidationApplication.java
 * --------------------------------------------------------------------------------------------
 * PROJECT:   FinBank Loan Service – Validation Demo
 * PURPOSE:   Show how to validate API request data automatically using Bean Validation.
 *
 * ANNOTATIONS COVERED:
 *   1. @Valid              → Triggers validation of an object on method parameters (e.g., @RequestBody)
 *   2. @Validated          → Class-level alternative to @Valid for method validations
 *   3. @NotNull            → Field cannot be null
 *   4. @NotBlank           → Field cannot be blank or just spaces
 *   5. @Size               → Enforce min and/or max length on strings/collections
 *   6. @Min / @Max         → Numeric range validation
 *   7. @Positive           → Value must be positive
 *   8. @Email              → Must be a valid email address
 *   9. @Pattern            → Enforce a custom regular expression (e.g., phone number)
 *  10. @Past / @PastOrPresent → Date must be in the past or today
 *  11. @Future             → Date must be in the future
 *
 * FLOW:
 *   1. User submits a loan application JSON to the API.
 *   2. @Valid checks each field against its annotation rules.
 *   3. If all checks pass → data goes to service.
 *      Otherwise → Spring automatically returns 400 Bad Request with error details.
 **********************************************************************************************/

// 1. MAIN APP --------------------------------------------------------------------------------
package com.finbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanValidationApplication.class, args);
    }
}


// 2. LOAN APPLICATION DTO -------------------------------------------------------------------
package com.finbank.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class LoanApplicationRequest {

    // Applicant’s full name – cannot be null or blank, length between 3 and 50
    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be 3-50 characters long")
    private String fullName;

    // Applicant email – must be valid email format
    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    // Loan amount – must be positive and at least 10,000
    @Positive(message = "Amount must be positive")
    @Min(value = 10000, message = "Minimum loan amount is 10,000")
    private double amount;

    // Applicant’s age – cannot be less than 18 or more than 70
    @Min(value = 18, message = "You must be at least 18 years old")
    @Max(value = 70, message = "Maximum eligible age is 70")
    private int age;

    // Date of birth – must be in the past
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    // Employment start date – must be in the past or today
    @PastOrPresent(message = "Employment start date cannot be in the future")
    private LocalDate employmentStartDate;

    // EMI start date – must be a future date
    @Future(message = "EMI start date must be in the future")
    private LocalDate emiStartDate;

    // Contact number – must follow pattern of 10 digits
    @NotBlank(message = "Contact number required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
    private String contactNumber;

    // Constructors, getters, and setters
    public LoanApplicationRequest() {}

    public LoanApplicationRequest(String fullName, String email, double amount, int age,
                                  LocalDate dateOfBirth, LocalDate employmentStartDate,
                                  LocalDate emiStartDate, String contactNumber) {
        this.fullName = fullName;
        this.email = email;
        this.amount = amount;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.employmentStartDate = employmentStartDate;
        this.emiStartDate = emiStartDate;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public LocalDate getEmploymentStartDate() { return employmentStartDate; }
    public void setEmploymentStartDate(LocalDate employmentStartDate) { this.employmentStartDate = employmentStartDate; }

    public LocalDate getEmiStartDate() { return emiStartDate; }
    public void setEmiStartDate(LocalDate emiStartDate) { this.emiStartDate = emiStartDate; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}


// 3. CONTROLLER -------------------------------------------------------------------------------
package com.finbank.controller;

import com.finbank.dto.LoanApplicationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/loan")
@Validated  // allows method-level validation if needed
public class LoanController {

    // POST endpoint to submit loan application
    @PostMapping("/apply")
    public ResponseEntity<String> applyForLoan(@Valid @RequestBody LoanApplicationRequest request) {
        // If @Valid passes all checks, this code executes
        String message = "Loan Application submitted successfully for " + request.getFullName();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}


// 4. GLOBAL EXCEPTION HANDLER -----------------------------------------------------------------
package com.finbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice   // applies to all controllers
public class GlobalExceptionHandler {

    // Handles validation errors and returns a readable JSON response
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
```

---

## 🟩 How It Works (Easy Explanation)

1. A **client (like Postman)** sends a JSON request to `/api/loan/apply`.
2. We put `@Valid` before `@RequestBody` in the controller → it activates validation.
3. The fields inside `LoanApplicationRequest` each have **rules** (like “age ≥ 18”, “email must be valid”).
4. Spring automatically checks all these rules **before running the method**.
5. If everything is fine → the API proceeds.
6. If any field fails → Spring throws `MethodArgumentNotValidException`, which is caught by our `GlobalExceptionHandler` and returned as **HTTP 400** with a list of field-wise error messages.

---

## 🔑 Validation Annotation Cheatsheet

| Annotation                           | What It Means (Easy Words)                                                                 |
| ------------------------------------ | ------------------------------------------------------------------------------------------ |
| **@Valid**                           | Tells Spring: “Check this object’s fields against their validation rules before using it.” |
| **@Validated**                       | Similar to `@Valid`, often used on class or method level.                                  |
| **@NotNull / @NotBlank**             | Field cannot be missing or empty.                                                          |
| **@Size(min, max)**                  | Text/collection must be within a certain length.                                           |
| **@Min / @Max / @Positive**          | Numeric value must be in a valid range or positive.                                        |
| **@Email**                           | Must look like an email address.                                                           |
| **@Pattern(regexp)**                 | Field must match a custom regex pattern (like phone numbers).                              |
| **@Past / @Future / @PastOrPresent** | Date must be before/after/today.                                                           |

---

✅ **End Result:**
This program demonstrates how **Spring Validation annotations** automatically check incoming request data for correctness before processing a loan application, making APIs more reliable and secure.
