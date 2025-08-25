package com.muneesh.controller;

import com.muneesh.dto.*;
import com.muneesh.security.Jwt;
import com.muneesh.service.*;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;//for 200 sucees valiadtion;

//ResponseEntity is used to wirite status code when we use json data

@Controller //for html views
@RequestMapping("/api")
public class UserController {
    @Autowired
    private Userservice service;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request, BindingResult bresult) throws Exception {
        if (bresult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bresult.getFieldErrors().
                    forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(400).body(errors);
        }


        try {
            String result = service.signup(request);
            if (result.equalsIgnoreCase("Email Already Exists")) {
                return ResponseEntity.status(409).body(Map.of("message", "Email Already Exist"));
            }
            return ResponseEntity.status(200).body("Signup successful");
        } catch (Exception e) {
            return ResponseEntity.status(501).body("signupfailed" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Loginresponse> login(@RequestBody LoginRequest request) throws Exception {
        try {

            Loginresponse response = service.login(request);
            if (response.getToken() == null) {
                return ResponseEntity.status(401).body(new Loginresponse("login failed",null));
            }
            return ResponseEntity.ok(response);//shortcut for 200 httpstatus
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Loginresponse(null,"Login Failed" + e.getMessage()));
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminacess(@RequestHeader("Authorization") String Authorization) throws Exception {
        try {
            if (Authorization == null || !Authorization.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body("Missing or invalid token");
            }
            String token = Authorization.substring(7);
            if (!Jwt.validateToken(token)) {
                return ResponseEntity.status(401).body("Invalid or Expired Token");
            }
            String role = Jwt.extractRole(token);
            if (!"Admin".equalsIgnoreCase(role)) {
                return ResponseEntity.status(403).body("Access denied: Admins Only");
            }
            List<UserResponse> users = service.getAllUsers();
            AdminResponse response = new AdminResponse("Admin login sucess", users);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("internal server error" + e.getMessage());
        }
    }

    @PostMapping("/adminlogin")
    public ResponseEntity<Loginresponse> adminlogin(@RequestBody LoginRequest request) throws Exception {
        try {
            Loginresponse response = service.adminlogin(request);
            if ("Mail not found".equalsIgnoreCase(response.getMessage())) {
                return ResponseEntity.status(400).body(response);
            }
            if ("Acess denied, not a Admin".equalsIgnoreCase(response.getMessage())) {
                return ResponseEntity.status(403).body(response);
            }
            if ("invalid credentials".equalsIgnoreCase(response.getMessage())) {
                return ResponseEntity.status(401).body(response);
            }
            return ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new Loginresponse("Admin Login Failed" + e.getMessage(), null));
        }


    }
}





