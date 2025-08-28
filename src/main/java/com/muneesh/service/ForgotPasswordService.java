package com.muneesh.service;

import com.muneesh.dto.ForgotPasswordRequest;
import com.muneesh.entity.ForgotPasswordTransistiom;
import com.muneesh.entity.Users;
import com.muneesh.repository.ForgotPasswordRepository;
import com.muneesh.repository.Userrepository;
import com.muneesh.security.Jwt;

import java.util.Optional;
import java.time.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.swing.text.StringContent;


@Service
public class ForgotPasswordService {
    @Autowired
    private ForgotPasswordRepository repository;
    @Autowired
    private Userrepository userRepository;
    @Autowired
    private Jwt jwt;
    @Autowired
    private JavaMailSender mail;

    public ResponseEntity<?> forgotpassword(ForgotPasswordRequest request) throws Exception {
        Optional<Users> user = userRepository.findByEmail(request.getEmail());
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body("Email not found in userdetails Data");
        }
        Optional<ForgotPasswordTransistiom> existingToken = repository.findByEmailAndStatus(request.getEmail(), "Active");
        if (existingToken.isPresent()) {
            ForgotPasswordTransistiom tokenEntity = existingToken.get();
            //this is for open the optional box and get the token entity
            //if the token is not expired then it will return the token

           if (tokenEntity.getExpired_at().isAfter(LocalDateTime.now())) {
                return ResponseEntity.status(400).body("Token is already active and not expired for this email");
            } else {
                tokenEntity.setStatus("Expired");
                repository.save(tokenEntity);
            }
        }

        String token = jwt.generateemailToken(request.getEmail());
        String resetLink = "http://localhost:8081/Resetpass.html?token=" + token;


        ForgotPasswordTransistiom transistiom = new ForgotPasswordTransistiom();
        transistiom.setUserid(user.get().getId());//GET().GETID IS USE TO OPEN THE OPTIONAL BOX FIST AND AFTER IT GETS THE ID
        transistiom.setEmail(request.getEmail());
        transistiom.setToken(token);
        transistiom.setCreated_at(LocalDateTime.now());
        transistiom.setExpired_at(LocalDateTime.now().plusMinutes(15));
        transistiom.setStatus("Active");

        repository.save(transistiom);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("To reset your password, click the link below:" + resetLink);
        mail.send(message);
        return ResponseEntity.ok().body("Token sent to your email");
    }
}

