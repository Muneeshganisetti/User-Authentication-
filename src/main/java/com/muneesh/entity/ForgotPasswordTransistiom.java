package com.muneesh.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.*;


@Entity
@Data
@Table(name = "ForgotPasswordTransistion")
public class ForgotPasswordTransistiom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userid;// THIS FILED IS FOR INSERT AND UPDATE
    @Column(length = 20)
    private String email;
    @Column(length = 512)
    private String token;
    private LocalDateTime created_at;
    private LocalDateTime expired_at;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;
}
