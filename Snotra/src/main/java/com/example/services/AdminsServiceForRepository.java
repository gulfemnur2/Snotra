package com.example.services;

import com.example.models.AdminEntity;
import com.example.models.AdminModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminsServiceForRepository implements AdminInterface {

    @Autowired
    private AdminsRepository adminsRepository;
    @Override
    public AdminModel authenticateAdmin(String adminEmail, String adminSifre) {
        AdminEntity adminEntity = adminsRepository.findByAdminEmail(adminEmail);
        if (adminEntity == null) {
            return null;
        }
        if (adminEntity.getAdminSifre().equals(adminSifre)) {
            return new AdminModel(
                    adminEntity.getId(),
                    adminEntity.getAdminEmail(),
                    adminEntity.getAdminSifre()
            );
        }
        return null;
    }
}