package com.example.backend.repository.person.password;

import com.example.backend.domain.model.person.password.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Integer> {
}

