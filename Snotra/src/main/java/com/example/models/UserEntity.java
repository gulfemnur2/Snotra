package com.example.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "kullanici")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kullanici_adi", nullable = false)
    private String kullaniciAdi;

    @NotBlank(message = "Şifre boş olamaz.")
    @Column(name = "sifre", nullable = false)
    private String sifre;

    @NotBlank(message = "Ad alanı boş olamaz.")
    @Column(name = "ad", nullable = false)
    private String ad;

    @NotBlank(message = "Soyad alanı boş olamaz.")
    @Column(name = "soyad", nullable = false)
    private String soyad;

    @NotBlank(message = "Email alanı boş olamaz.")
    @Email(message = "Geçerli bir email adresi girin.")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "odunc_kitap")
    private String oduncKitap;

    @Column(name = "odunc_id")
    private Long oduncId;
    @Column(name = "odunc_alma_tarihi")
    private LocalDateTime oduncAlmaTarihi;

    public UserEntity() {
    }

    public UserEntity(Long id, String kullaniciAdi, String sifre, String ad, String soyad, String email, String oduncKitap, Long oduncId, LocalDateTime oduncAlmaTarihi) {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.oduncKitap = oduncKitap;
        this.oduncId = oduncId;
        this.oduncAlmaTarihi = oduncAlmaTarihi;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", kullaniciAdi='" + kullaniciAdi + '\'' +
                ", sifre='" + sifre + '\'' +
                ", ad='" + ad + '\'' +
                ", soyad='" + soyad + '\'' +
                ", email='" + email + '\'' +
                ", oduncKitap='" + oduncKitap + '\'' +
                ", oduncId=" + oduncId +
                ", oduncAlmaTarihi=" + oduncAlmaTarihi +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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