Here’s a **complete, beginner-friendly demo** for **🟢 7. Cloud & Distributed System Annotations** that are commonly used in a **Spring-Cloud-based microservices** setup.

We’ll keep **the same protocol**:

* A **Bank Loan Service** that registers with a **Service Registry (Eureka)**
* A **Gateway** that routes requests to the Loan Service
* **Cloud/Distributed-system annotations** explained in **plain English** with **line-by-line comments**
* Designed so even a **15-year-old can follow the flow**

---

## 📦 What We’ll Build

A **simple cloud-native loan microservice system** with two parts:

1. **Eureka Server** – central directory where microservices register themselves.
2. **Loan Service** – registers itself with Eureka and exposes loan APIs.
3. (Optional) **API Gateway** – routes external requests to Loan Service (we show config & annotations).

---

```java
/**********************************************************************************************
 * PROJECT:   FinBank Cloud Microservice Demo
 * PURPOSE:   Show how common Spring Cloud annotations help microservices communicate
 *            in a distributed system.
 *
 * CLOUD / DISTRIBUTED SYSTEM ANNOTATIONS COVERED:
 *   1. @EnableEurekaServer        → Turns this app into a Eureka Service Registry
 *   2. @EnableEurekaClient        → Lets a microservice register itself to Eureka
 *   3. @EnableDiscoveryClient     → Enables service-to-service discovery
 *   4. @EnableFeignClients        → Enables Feign clients for easy REST calls to other services
 *   5. @EnableCircuitBreaker / @EnableResilience4jCircuitBreaker → Add circuit-breaker pattern
 *   6. @LoadBalanced              → Makes a RestTemplate or WebClient resolve service names
 *   7. @RefreshScope              → Reloads bean properties dynamically without restarting service
 *   8. @EnableConfigServer        → Enables a central config server for distributed config mgmt
 *   9. @EnableZuulProxy / @EnableGateway → API gateway / router (Spring Cloud Gateway)
 *  10. @CloudComponent (rare)     → Marks a component that relies on cloud infrastructure
 *
 * FLOW:
 *   1. Start Eureka Server
 *   2. Loan Service registers itself with Eureka
 *   3. API Gateway routes external traffic to Loan Service by service name
 *   4. Loan Service calls other services using Feign with service discovery
 **********************************************************************************************/


// ===========================================================================================
// 1. EUREKA SERVER – Central Service Registry
// ===========================================================================================

/* FILE: src/main/java/com/finbank/eureka/EurekaServerApplication.java */

package com.finbank.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer   // This annotation makes the app act as Eureka registry
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

/*
 * 🔑 EXPLANATION:
 * - We just created a Eureka Server using @EnableEurekaServer.
 * - When it runs, it becomes a central directory for all services.
 * - Think of it as a phonebook where each service registers its phone number (URL).
 */


// ===========================================================================================
// 2. LOAN SERVICE – Registers Itself with Eureka
// ===========================================================================================

/* FILE: src/main/java/com/finbank/loan/LoanServiceApplication.java */

package com.finbank.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient        // Registers this Loan Service with Eureka
@EnableDiscoveryClient     // Allows it to discover other services
@EnableFeignClients        // Enables using Feign to call other services
public class LoanServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanServiceApplication.class, args);
    }
}

/*
 * 🔑 EXPLANATION:
 * - @EnableEurekaClient → This microservice registers itself to the Eureka registry.
 * - @EnableDiscoveryClient → Lets it discover other registered services.
 * - @EnableFeignClients → Allows us to define interfaces for calling other services easily.
 */


// ===========================================================================================
// 3. LOAN CONTROLLER – A simple cloud-enabled REST API
// ===========================================================================================

/* FILE: src/main/java/com/finbank/loan/controller/LoanController.java */

package com.finbank.loan.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RestController
@RequestMapping("/api/loan")
@RefreshScope    // Allows this controller to reload updated properties from Config Server dynamically
public class LoanController {

    // value injected from application.properties or Config Server
    @Value("${loan.service.greeting:Welcome to Loan Service}")
    private String greeting;

    @GetMapping("/greet")
    public String greet() {
        return greeting;
    }

    @GetMapping("/status")
    public String status() {
        return "Loan Service is UP and registered in Eureka";
    }
}

/*
 * 🔑 EXPLANATION:
 * - @RefreshScope lets us change the greeting in the Config Server and refresh without restarting the service.
 */


// ===========================================================================================
// 4. LOAN CLIENT USING FEIGN – Calls another microservice (e.g., Credit Score Service)
// ===========================================================================================

/* FILE: src/main/java/com/finbank/loan/client/CreditScoreClient.java */

package com.finbank.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "credit-service")  // service name as registered in Eureka
public interface CreditScoreClient {

    @GetMapping("/api/credit/score")
    String getCreditScore();
}

/*
 * 🔑 EXPLANATION:
 * - @FeignClient lets us call another service by its Eureka name (credit-service) without worrying about its IP/port.
 * - Feign makes REST calls feel like calling a normal Java method.
 */


// ===========================================================================================
// 5. CIRCUIT BREAKER EXAMPLE – Prevents cascading failures
// ===========================================================================================

/* FILE: src/main/java/com/finbank/loan/service/CreditScoreService.java */

package com.finbank.loan.service;

import com.finbank.loan.client.CreditScoreClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreService {

    private final CreditScoreClient client;

    public CreditScoreService(CreditScoreClient client) {
        this.client = client;
    }

    @CircuitBreaker(name = "creditServiceCB", fallbackMethod = "fallbackScore")
    public String fetchCreditScore() {
        return client.getCreditScore(); // may fail if credit-service is down
    }

    // Fallback method if above call fails
    public String fallbackScore(Throwable t) {
        return "Credit service unavailable – using default score 600";
    }
}

/*
 * 🔑 EXPLANATION:
 * - @CircuitBreaker prevents our Loan Service from breaking if Credit Service is down.
 * - If call fails, it immediately returns a fallback response instead of waiting too long or crashing.
 */


// ===========================================================================================
// 6. GATEWAY – Routes external traffic to the Loan Service
// ===========================================================================================

/* FILE: src/main/java/com/finbank/gateway/ApiGatewayApplication.java */

package com.finbank.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient    // Gateway also registers with Eureka and discovers services
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}

/*
 * 🔑 EXPLANATION:
 * - The Gateway uses Eureka to find Loan Service by its name.
 * - It lets external clients call http://gateway/api/loan/greet → which routes internally to Loan Service.
 */


// ===========================================================================================
// 7. LOAD-BALANCED REST TEMPLATE BEAN
// ===========================================================================================

/* FILE: src/main/java/com/finbank/loan/config/AppConfig.java */

package com.finbank.loan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced      // makes RestTemplate resolve service names via Eureka
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

/*
 * 🔑 EXPLANATION:
 * - @LoadBalanced lets us call http://credit-service/api/credit/score inside the Loan Service without hardcoding IP/port.
 */
```

---

## 🟩 How It Works (Simple Story)

1. **Eureka Server** starts first → acts like a phonebook of services.
2. **Loan Service** starts → registers its address in Eureka with the name `loan-service`.
3. **Gateway** starts → also registers and knows how to route requests to `loan-service`.
4. Client hits `http://gateway/api/loan/greet` → Gateway looks up `loan-service` in Eureka → forwards the request.
5. If Loan Service needs **credit score**, it uses **Feign** to call `credit-service` by name, no IP needed.
6. If `credit-service` is down, **CircuitBreaker** ensures Loan Service still responds with a fallback message.
7. Any config changes like greeting message can be updated in Config Server and refreshed live using `@RefreshScope`.

---

## 🔑 Cloud & Distributed System Annotations Cheat-Sheet

| Annotation                                  | Role in Cloud / Distributed System                               |
| ------------------------------------------- | ---------------------------------------------------------------- |
| **@EnableEurekaServer**                     | Turns an app into Eureka registry (service directory).           |
| **@EnableEurekaClient**                     | Registers the microservice with Eureka automatically.            |
| **@EnableDiscoveryClient**                  | Lets a service discover others by name.                          |
| **@EnableFeignClients**                     | Enables Feign REST clients for inter-service calls.              |
| **@FeignClient(name)**                      | Declares a proxy client to talk to another service.              |
| **@EnableCircuitBreaker / @CircuitBreaker** | Enables circuit-breaker to handle service failures gracefully.   |
| **@LoadBalanced**                           | Makes RestTemplate/WebClient resolve service names via Eureka.   |
| **@RefreshScope**                           | Lets a bean reload configuration dynamically from Config Server. |
| **@EnableConfigServer**                     | Makes an app act as central configuration server.                |
| **@EnableGateway / @EnableZuulProxy**       | Marks an app as API gateway to route requests.                   |

---

✅ **End Result:**
We now have a **cloud-native loan microservice system** using all **key distributed-system annotations**:

* automatic **service registration & discovery**,
* **gateway routing**,
* **dynamic config refresh**, and
* **circuit-breaker for resilience** — all made simple by annotations.
