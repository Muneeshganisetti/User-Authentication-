package com.muneesh.repository;

import com.muneesh.entity.ForgotPasswordTransistiom;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordTransistiom, Integer> {
    Optional<ForgotPasswordTransistiom> findByemail(String email);

    Optional<ForgotPasswordTransistiom> findByToken(String token);
    Optional<ForgotPasswordTransistiom> findByEmailAndStatus(String email, String status);
}
