package com.example.services;

import com.example.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsersRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByKullaniciAdi(String searchTerm);
    UserEntity findByEmailAndSifre(String email, String sifre);
    UserEntity findByEmail(String email);
}