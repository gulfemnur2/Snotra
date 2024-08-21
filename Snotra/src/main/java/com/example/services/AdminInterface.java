package com.example.services;

import com.example.models.AdminModel;


public interface AdminInterface {
    AdminModel authenticateAdmin(String adminEmail, String adminSifre);
}