Here’s a **full beginner-friendly Spring Security example** demonstrating the most commonly used **🔴 5. Spring Security Annotations** in a **Financial Loan Microservice** context.

We’ll keep the **same protocol** as before:

* File-level headers with purpose and annotation list
* Line-by-line **plain-English comments** so even a 15-year-old can follow
* A small **Bank Loan API** protected by login & role-based authorization

---

```java
/**********************************************************************************************
 * File Path: src/main/java/com/finbank/LoanSecurityApplication.java
 * --------------------------------------------------------------------------------------------
 * PROJECT:   FinBank Loan Service – Spring Security Demo
 * PURPOSE:   Show how to secure REST APIs with Spring Security using common annotations.
 *
 * ANNOTATIONS COVERED:
 *   1. @EnableWebSecurity      → Turns ON Spring Security & lets us customize it
 *   2. @Configuration          → Declares a configuration class
 *   3. @Bean                   → Creates security-related beans
 *   4. @Service                → Marks authentication service as Spring bean
 *   5. @Autowired              → Injects beans automatically
 *   6. @RestController         → Exposes secured endpoints
 *   7. @RequestMapping         → Maps endpoints
 *   8. @PostMapping / @GetMapping → Endpoint definitions
 *   9. @PreAuthorize           → Method-level authorization based on roles
 *  10. @Secured                → Alternative way to restrict by roles
 *  11. @RolesAllowed           → (Jakarta) Another way to restrict by roles
 *  12. @AuthenticationPrincipal → Access currently logged-in user details
 *
 * FLOW:
 *   Users must log in → Spring Security authenticates → roles decide which endpoints allowed.
 **********************************************************************************************/

// 1. MAIN APP --------------------------------------------------------------------------------
package com.finbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoanSecurityApplication.class, args);
    }
}


// 2. SECURITY CONFIGURATION -----------------------------------------------------------------
package com.finbank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration                           // configuration class
@EnableWebSecurity                      // turns on Spring Security filter chain
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    // PasswordEncoder bean so passwords are stored securely (hashed)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security rules for HTTP requests
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())      // disable CSRF for demo (not recommended for prod)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()  // anyone can access public APIs
                .anyRequest().authenticated()                  // all others need login
            )
            .httpBasic();   // enable basic authentication for simplicity
        return http.build();
    }
}


// 3. USER MODEL -----------------------------------------------------------------------------
package com.finbank.model;

import java.util.Set;

public class UserAccount {
    private String username;
    private String password;
    private Set<String> roles;

    public UserAccount(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Set<String> getRoles() { return roles; }
}


// 4. USER DETAILS SERVICE -------------------------------------------------------------------
package com.finbank.service;

import com.finbank.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Fake in-memory user DB for demo
    private static final Map<String, UserAccount> USERS = new HashMap<>();
    static {
        USERS.put("alice", new UserAccount("alice","{noop}password",Set.of("ROLE_USER")));
        USERS.put("bob", new UserAccount("bob","{noop}password",Set.of("ROLE_ADMIN")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount account = USERS.get(username);
        if (account == null)
            throw new UsernameNotFoundException("User not found");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

        return new User(account.getUsername(), account.getPassword(), authorities);
    }
}


// 5. CONTROLLER -----------------------------------------------------------------------------
package com.finbank.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.annotation.Secured;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    // anyone can view this
    @GetMapping("/public/info")
    public String publicInfo() {
        return "Welcome to FinBank Loan Service – Public Info";
    }

    // only logged-in USER or ADMIN can access
    @GetMapping("/view")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")   // method-level security
    public String viewLoans(@AuthenticationPrincipal User user) {
        return "Loans for " + user.getUsername();
    }

    // only ADMIN can approve loan
    @PostMapping("/approve")
    @Secured("ROLE_ADMIN")                        // restrict to ADMIN
    public String approveLoan(@RequestParam String loanId) {
        return "Loan " + loanId + " approved by ADMIN";
    }

    // also only ADMIN but using @RolesAllowed (JSR-250)
    @DeleteMapping("/delete/{loanId}")
    @RolesAllowed("ROLE_ADMIN")
    public String deleteLoan(@PathVariable String loanId) {
        return "Loan " + loanId + " deleted by ADMIN";
    }
}
```

---

## 🟩 How it works (Plain English)

1. **`@EnableWebSecurity`** turns on Spring’s security filters.
2. We defined which URLs are public and which need authentication in **`filterChain`**.
3. **`CustomUserDetailsService`** supplies user details and roles to Spring Security.
4. Roles:

   * `alice` → ROLE_USER
   * `bob` → ROLE_ADMIN
5. **Login:**

   * Any secured endpoint requires basic authentication header.
6. **Role-based access:**

   * `/api/loan/view` → allowed for USER and ADMIN
   * `/api/loan/approve` & `/api/loan/delete` → only ADMIN
7. **Annotations:**

   * `@PreAuthorize` → check role before method runs
   * `@Secured` → similar but simpler role check
   * `@RolesAllowed` → JSR-250 standard alternative
   * `@AuthenticationPrincipal` → gives access to the currently logged-in user object

---

## 🔑 Security Annotation Cheatsheet

| Annotation                        | What it does (in simple words)                               |
| --------------------------------- | ------------------------------------------------------------ |
| **@EnableWebSecurity**            | Switches on web security and lets us configure login rules.  |
| **@EnableMethodSecurity**         | Allows use of method-level annotations like `@PreAuthorize`. |
| **@PreAuthorize("…")**            | Checks role/expression before running a method or endpoint.  |
| **@Secured("ROLE_X")**            | A simpler, older way to restrict method access by role.      |
| **@RolesAllowed("ROLE_X")**       | Standard JSR-250 equivalent of `@Secured`.                   |
| **@AuthenticationPrincipal**      | Injects the current logged-in user into your method.         |
| **@Service / @Autowired / @Bean** | Usual Spring annotations for bean management.                |

---

✅ **End Result:**
This code shows how to use **Spring Security annotations** to lock down APIs in a **loan service**:

* Users must log in.
* Roles decide which endpoints they can use.
* We can secure endpoints both at **HTTP URL** level and at **method level**.
