package com.example.services;

import com.example.models.AdminMapper;
import com.example.models.AdminModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminsDataService implements AdminInterface {
    private JdbcTemplate jdbcTemplate;

    @Override
    public AdminModel authenticateAdmin(String adminEmail, String adminSifre) {
        try {
            AdminModel user = jdbcTemplate.queryForObject(
                    "SELECT * FROM kullanici WHERE admin_email = ?",
                    new Object[]{adminEmail},
                    new AdminMapper()
            );
            if (user != null && user.getAdminSifre().equals(adminSifre)) {
                return user;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Kullanıcı doğrulama hatası. Lütfen tekrar deneyin.");
        }
        return null;
    }
}