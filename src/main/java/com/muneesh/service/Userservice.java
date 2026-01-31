package com.muneesh.service;

import com.muneesh.dto.*;
import com.muneesh.entity.Users;
import com.muneesh.repository.*;
import com.muneesh.security.*;
import com.muneesh.project1.*;
import jodd.typeconverter.impl.StringArrayConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Userservice {
    @Autowired
    private Userrepository userrepository;
    @Autowired
    private Jwt jwt;

    public String signup(SignupRequest request) throws Exception {
        if (userrepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exist";

        }
        String salt = PasswordUtil.generateSalt();
        String hash = PasswordUtil.hashPassword(request.getPassword(), salt);

        Users user = new Users();
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setRole(request.getRole());
        user.setEmail(request.getEmail());
        user.setPasswordhash(hash);
        user.setSalt(salt);
        user.setToken_Expiry(System.currentTimeMillis() + (1000 * 60 * 60));
        userrepository.save(user);
        return "Signup succesfull";
    }

    public Loginresponse login(LoginRequest request) throws Exception {
        Optional<Users> useropt = userrepository.findByEmail(request.getEmail());
        if (!useropt.isPresent()) {
            return new Loginresponse("Email Not Found", null);
        }
        Users user = useropt.get();
        String hashedInput = PasswordUtil.hashPassword(request.getPassword(), user.getSalt());
        if (!hashedInput.equals(user.getPasswordhash())) {
            return new Loginresponse("Credentials do not match", null);
        }
        long  expiry = System.currentTimeMillis() + (15 * 60 *1000); //fresh time expiry for new token
        user.setToken_Expiry(expiry);
        userrepository.save(user);

       String token= jwt.generateToken(user.getEmail(), user.getRole(), expiry);
        return new Loginresponse("login succesful", token);

    }

    public Loginresponse adminlogin(LoginRequest request) throws Exception {
        Optional<Users> useropt = userrepository.findByEmail(request.getEmail());
        if (!useropt.isPresent()) {
            return new Loginresponse("Mail not found", null);
        }
        Users user = useropt.get();
        if (!"Admin".equalsIgnoreCase(user.getRole())) {
            return new Loginresponse("Acess denied,not a Admin", null);
        }
        String hashedInput = PasswordUtil.hashPassword(request.getPassword(), user.getSalt());
        if (!hashedInput.equals(user.getPasswordhash())) {
            return new Loginresponse("invalid credentials", null);
        }
        long expiry = System.currentTimeMillis() + (10 * 60 * 1000);
        user.setToken_Expiry(expiry);
        userrepository.save(user);
        String token = jwt.generateToken(user.getEmail(), user.getRole(), expiry);
        return new Loginresponse("Admin login successful", token);

    }

    public List<UserResponse> getAllUsers() {
        return userrepository.findAll()
                .stream()
                .map(users -> new UserResponse(
                        users.getId(),
                        users.getFirstName(),
                        users.getLastName(),
                        users.getEmail(),
                        users.getRole()
                ))
                .collect(Collectors.toList());


    }
}
