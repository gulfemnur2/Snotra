package com.example.models;

import java.time.LocalDateTime;

public class UserModel {
    private String kullaniciAdi;
    private String sifre;
    private String ad;
    private String soyad;
    private String email;
    private Long id;
    private String oduncKitap;
    private Long oduncId;
    private LocalDateTime oduncAlmaTarihi;

    public UserModel() {
    }

    public UserModel(String kullaniciAdi, String sifre, String ad, String soyad, String email, Long id, String oduncKitap, Long oduncId, LocalDateTime oduncAlmaTarihi) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.id = id;
        this.oduncKitap = oduncKitap;
        this.oduncId = oduncId;
        this.oduncAlmaTarihi = oduncAlmaTarihi;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "kullaniciAdi='" + kullaniciAdi + '\'' +
                ", sifre='" + sifre + '\'' +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", oduncKitap='" + oduncKitap + '\'' +
                ", oduncId=" + oduncId +
                ", oduncAlmaTarihi=" + oduncAlmaTarihi +
                '}';
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOduncKitap() {
        return oduncKitap;
    }

    public void setOduncKitap(String oduncKitap) {
        this.oduncKitap = oduncKitap;
    }

    public Long getOduncId() {
        return oduncId;
    }

    public void setOduncId(Long oduncId) {
        this.oduncId = oduncId;
    }

    public LocalDateTime getOduncAlmaTarihi() {
        return oduncAlmaTarihi;
    }

    public void setOduncAlmaTarihi(LocalDateTime oduncAlmaTarihi) {
        this.oduncAlmaTarihi = oduncAlmaTarihi;
    }
}