package com.muneesh.repository;

import com.muneesh.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface Userrepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmail(String email);
}
