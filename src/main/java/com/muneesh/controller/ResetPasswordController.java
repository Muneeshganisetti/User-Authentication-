package com.muneesh.controller;

import com.muneesh.dto.ForgotPasswordRequest;
import com.muneesh.dto.ResetPasswordRequest;
import com.muneesh.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService service;
@PostMapping("/resetpass")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request)throws Exception {
        try {
            String response = service.resetPassword(request);
            return ResponseEntity.ok().body("rest password successful"+ response);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("reset password failed"+ e.getMessage());
        }
    }
    }

