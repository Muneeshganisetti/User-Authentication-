package com.muneesh.entity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="userdetails")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    private String role;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "Password_hash")
    private String passwordhash;
    private String salt;
    @Column(name = "Token_Expiry")
    private Long token_Expiry;
}
