package com.example.services;

import com.example.models.AdminEntity;
import com.example.models.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminsRepository extends JpaRepository<AdminEntity, Long> {
    AdminEntity findByAdminEmail(String adminEmail);
}