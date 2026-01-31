package com.muneesh.service;

import com.muneesh.dto.ResetPasswordRequest;
import com.muneesh.entity.ForgotPasswordTransistiom;
import com.muneesh.entity.Users;
import com.muneesh.repository.ForgotPasswordRepository;

import java.util.Optional;

import com.muneesh.repository.Userrepository;
import com.muneesh.security.PasswordUtil;
import jakarta.transaction.Status;

import java.time.*;

import org.hibernate.persister.entity.mutation.AttributeAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.StringContent;

@Service
public class ResetPasswordService {
    @Autowired
    private ForgotPasswordRepository repository;
    @Autowired
    private PasswordUtil util;
    @Autowired
    private Userrepository userrepository;


    public String resetPassword(ResetPasswordRequest request) throws Exception {


        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return "password do not match";
        }
        Optional<ForgotPasswordTransistiom> token = repository.findByToken(request.getToken());
        if (token.isEmpty()) {
            return "Token is not present";
        }
        ForgotPasswordTransistiom entity = token.get();
        if (!"ACTIVE".equalsIgnoreCase(entity.getStatus())) {
            return "token is not activated";
        }
        if (entity.getExpired_at().isBefore(LocalDateTime.now())) {
            return "token is expired";

        }
        String salt = util.generateSalt();
        String hashPassword = util.hashPassword(request.getNewPassword(), salt);


        Users user = entity.getUsers();
        user.setSalt(salt);
        user.setPasswordhash(hashPassword);
        userrepository.save(user);


        entity.setStatus("USED"); // or TokenStatus.USED if using Enummy
        repository.save(entity); // save the updated entity

        return "password reset done";
    }
}
