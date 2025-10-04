Here’s a **beginner-friendly, financial-industry example** that demonstrates the main **🟣 Spring Dependency Injection (Bean Management) Annotations**.
I’ve written the code in a way that even a 15-year-old can follow: every step is **commented in plain English**, and I’ve tied it to a simple **Bank Loan Service** example.

---

```java
/**********************************************************************************************
 * File Path: src/main/java/com/finbank/LoanApplication.java
 * --------------------------------------------------------------------------------------------
 * PROJECT:   FinBank Loan Processing Microservice
 * PURPOSE:   Demonstrate core Spring Dependency-Injection & Bean-Management annotations.
 *
 * ANNOTATIONS COVERED:
 *      1. @Configuration      → tells Spring this class holds bean definitions
 *      2. @Bean               → explicitly creates a bean managed by Spring
 *      3. @Component          → marks a normal class as a Spring-managed bean
 *      4. @Service            → marks a class as a “Service-layer” bean
 *      5. @Repository         → marks a class as a “Data-access” bean
 *      6. @Controller / @RestController → (optional here) entry point to expose features
 *      7. @Autowired          → asks Spring to automatically give us a ready bean
 *      8. @Qualifier          → tells Spring *which* bean to pick if there are several of same type
 *      9. @Primary            → marks one bean as the “default choice” when multiple beans exist
 *     10. @Lazy               → delays bean creation until it’s first needed
 *     11. @Scope              → tells Spring how many bean copies to create (singleton/prototype…)
 *
 * NOTE:
 *  - “Bean” simply means an object that Spring creates & manages for us.
 *  - Dependency Injection means: instead of us using “new” to build objects, we “ask” Spring
 *    to supply ready-to-use objects (dependencies) wherever we need them.
 **********************************************************************************************/

// 1. MAIN APP -------------------------------------------------------------------------------
package com.finbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication → combo of @Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class LoanApplication {
    public static void main(String[] args) {
        // Boots up the entire Spring container
        SpringApplication.run(LoanApplication.class, args);
    }
}


// 2. CONFIGURATION CLASS ---------------------------------------------------------------------
package com.finbank.config;

import com.finbank.service.InterestCalculator;
import com.finbank.service.SimpleInterestCalculator;
import com.finbank.service.CompoundInterestCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration  // Says: “I hold bean definitions manually created by developer”
public class LoanConfig {

    // @Bean → Spring will call this method & keep the returned object as a managed bean
    // @Primary → if more than one InterestCalculator exists, use this one by default
    @Bean
    @Primary
    public InterestCalculator simpleInterestCalculator() {
        // We return an implementation of InterestCalculator
        return new SimpleInterestCalculator();
    }

    // Another bean of the same type but not primary
    // @Lazy → don’t create it at startup, only when first needed
    // @Scope("prototype") → create a *new object* each time it’s requested
    @Bean
    @Lazy
    @Scope("prototype")
    public InterestCalculator compoundInterestCalculator() {
        return new CompoundInterestCalculator();
    }
}


// 3. SERVICE INTERFACES & IMPLEMENTATIONS ----------------------------------------------------
package com.finbank.service;

// A simple contract (interface) for interest calculation
public interface InterestCalculator {
    double calculateInterest(double principal, double rate, int years);
}

package com.finbank.service;

import org.springframework.stereotype.Service;

// @Service → marks this class as a Service-layer bean
@Service
public class SimpleInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(double principal, double rate, int years) {
        // Simple Interest = P × R × T / 100
        return (principal * rate * years) / 100;
    }
}

package com.finbank.service;

import org.springframework.stereotype.Service;

// @Service → also a Service-layer bean, but NOT @Primary
@Service
public class CompoundInterestCalculator implements InterestCalculator {

    @Override
    public double calculateInterest(double principal, double rate, int years) {
        // Compound Interest formula
        return principal * Math.pow(1 + rate / 100, years) - principal;
    }
}


// 4. REPOSITORY LAYER ------------------------------------------------------------------------
package com.finbank.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

// @Repository → data-access bean; in real apps, talks to DB
@Repository
public class LoanRepository {

    private final Map<String, Double> loanDB = new HashMap<>();

    public void saveLoan(String customer, double amount) {
        loanDB.put(customer, amount);
    }

    public double findLoanAmount(String customer) {
        return loanDB.getOrDefault(customer, 0.0);
    }
}


// 5. CONTROLLER LAYER ------------------------------------------------------------------------
package com.finbank.controller;

import com.finbank.service.InterestCalculator;
import com.finbank.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController   // exposes REST endpoints
@RequestMapping("/loan")
public class LoanController {

    // @Autowired → asks Spring to inject ready beans
    @Autowired
    private LoanRepository loanRepository;

    // There are two beans of type InterestCalculator → tell Spring which one to pick
    @Autowired
    @Qualifier("simpleInterestCalculator") // use the simple one
    private InterestCalculator calculator;

    @PostMapping("/apply")
    public String applyLoan(@RequestParam String customer, @RequestParam double amount) {
        loanRepository.saveLoan(customer, amount);
        return "Loan of ₹" + amount + " approved for " + customer;
    }

    @GetMapping("/interest")
    public String getInterest(@RequestParam String customer,
                              @RequestParam double rate,
                              @RequestParam int years) {
        double amount = loanRepository.findLoanAmount(customer);
        double interest = calculator.calculateInterest(amount, rate, years);
        return "Interest for " + customer + " over " + years + " years: ₹" + interest;
    }
}
```

---

## 🟩 Step-by-Step Flow (Plain English)

1. **App starts** ➜ `SpringApplication.run()` wakes Spring up.
2. Spring scans packages for classes with `@Component`, `@Service`, `@Repository`, `@Controller`.
3. Whenever it finds these, it **creates objects (beans)** and keeps them in a “bean container”.
4. `LoanConfig` class tells Spring to **manually create two extra beans**:

   * `simpleInterestCalculator` (marked as `@Primary`)
   * `compoundInterestCalculator` (marked as `@Lazy` + `@Scope("prototype")`)
5. When a request hits `/loan/apply`, Spring routes it to `LoanController.applyLoan()`.
6. The controller already has a ready **`LoanRepository` bean injected** via `@Autowired`.
7. Later, `/loan/interest` uses the injected `calculator` bean to calculate interest.
8. We used `@Qualifier` to specify **which** InterestCalculator bean to inject
   (since we have both simple & compound versions).
9. The user gets a calculated interest based on their stored loan amount.

---

## 🔑 Why these annotations matter

| Annotation                              | Human-friendly meaning                                                                               |
| --------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| **@Configuration**                      | “This class describes how to create some beans.”                                                     |
| **@Bean**                               | “Spring, whenever you start up, run this method & keep the returned object ready for others to use.” |
| **@Component / @Service / @Repository** | “I’m a normal class — please create & manage an object of me automatically.”                         |
| **@Autowired**                          | “Please give me the ready-to-use bean of this type — don’t make me call `new`.”                      |
| **@Qualifier**                          | “I know there are many beans of this type — give me this particular one.”                            |
| **@Primary**                            | “If there’s a tie between beans, pick me as default.”                                                |
| **@Lazy**                               | “Don’t create me at startup; only create me when I’m first requested.”                               |
| **@Scope**                              | “Should I be created once for the whole app (`singleton`) or fresh every time (`prototype`)? ”       |

---

✅ **End Result:**
This example shows how **Spring’s Dependency Injection** saves us from manually wiring objects.
In a real financial microservice, the same pattern is used to wire payment gateways, audit loggers, fraud-check services, etc.
