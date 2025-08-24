package com.muneesh.controller;

import com.muneesh.dto.ForgotPasswordRequest;
import com.muneesh.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ForgotRequestController {
    @Autowired
    private ForgotPasswordService service;

    @PostMapping("/forgotpass")
    public ResponseEntity<?> forgotpassword(@RequestBody ForgotPasswordRequest request) throws Exception {
        try {
            return service.forgotpassword(request);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error" + e.getMessage());
        }
    }

}
