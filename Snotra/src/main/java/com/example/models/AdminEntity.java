package com.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_sifre")
    private String adminSifre;

    public AdminEntity() {
    }

    public AdminEntity(Long id, String adminEmail, String adminSifre) {
        this.id = id;
        this.adminEmail = adminEmail;
        this.adminSifre = adminSifre;
    }

    @Override
    public String toString() {
        return "AdminEntity{" +
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