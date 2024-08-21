package com.example.models;

public class AdminModel {
    private Long id;
    private String adminEmail;
    private String adminSifre;

    public AdminModel() {
    }

    public AdminModel(Long id, String adminEmail, String adminSifre) {
        this.id = id;
        this.adminEmail = adminEmail;
        this.adminSifre = adminSifre;
    }

    @Override
    public String toString() {
        return "AdminModel{" +
                "id=" + id +
                ", adminEmail='" + adminEmail + '\'' +
                ", adminSifre='" + adminSifre + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminSifre() {
        return adminSifre;
    }

    public void setAdminSifre(String adminSifre) {
        this.adminSifre = adminSifre;
    }
}