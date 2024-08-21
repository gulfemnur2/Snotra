package com.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "kitaplar")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "baslik")
    private String baslik;

    @Column(name = "yazar")
    private String yazar;

    @Column(name = "sayfa")
    private int sayfa;

    @Column(name = "aciklama", length = 1000)
    private String aciklama;

    @Column(name = "yayinevi")
    private String yayinevi;

    @Column(name = "basim_yili")
    private Integer basimYili;

    @Column(name = "odunc")
    private boolean odunc;

    @Column(name = "odunc_alan_kullanici")
    private String oduncAlanKullanici;

    @Column(name = "ort")
    private Long ort;

    @Column(name = "odunc_alma_tarihi")
    private LocalDateTime oduncAlmaTarihi;

    @Column(name = "kategori")
    private String kategori;

    @Column(name = "kapak")
    private String kapak;

    public BookEntity() {
    }

    public BookEntity(Long id, String baslik, String yazar, int sayfa, String aciklama, String yayinevi, Integer basimYili, boolean odunc, String oduncAlanKullanici, Long ort, LocalDateTime oduncAlmaTarihi, String kategori, String kapak) {
        this.id = id;
        this.baslik = baslik;
        this.yazar = yazar;
        this.sayfa = sayfa;
        this.aciklama = aciklama;
        this.yayinevi = yayinevi;
        this.basimYili = basimYili;
        this.odunc = odunc;
        this.oduncAlanKullanici = oduncAlanKullanici;
        this.ort = ort;
        this.oduncAlmaTarihi = oduncAlmaTarihi;
        this.kategori = kategori;
        this.kapak = kapak;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getYazar() {
        return yazar;
    }

    public void setYazar(String yazar) {
        this.yazar = yazar;
    }

    public int getSayfa() {
        return sayfa;
    }

    public void setSayfa(int sayfa) {
        this.sayfa = sayfa;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getYayinevi() {
        return yayinevi;
    }

    public void setYayinevi(String yayinevi) {
        this.yayinevi = yayinevi;
    }

    public Integer getBasimYili() {
        return basimYili;
    }

    public void setBasimYili(Integer basim_yili) {
        this.basimYili = basim_yili;
    }

    public boolean isOdunc() {
        return odunc;
    }

    public void setOdunc(boolean odunc) {
        this.odunc = odunc;
    }

    public String getOduncAlanKullanici() {
        return oduncAlanKullanici;
    }

    public void setOduncAlanKullanici(String oduncAlanKullanici) {
        this.oduncAlanKullanici = oduncAlanKullanici;
    }

    public Long getOrt() {
        return ort;
    }

    public void setOrt(Long ort) {
        this.ort = ort;
    }

    public LocalDateTime getOduncAlmaTarihi() {
        return oduncAlmaTarihi;
    }

    public void setOduncAlmaTarihi(LocalDateTime oduncAlmaTarihi) {
        this.oduncAlmaTarihi = oduncAlmaTarihi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKapak() {
        return kapak;
    }

    public void setKapak(String kapak) {
        this.kapak = kapak;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "id=" + id +
                ", baslik='" + baslik + '\'' +
                ", yazar='" + yazar + '\'' +
                ", sayfa=" + sayfa +
                ", aciklama='" + aciklama + '\'' +
                ", yayinevi='" + yayinevi + '\'' +
                ", basimYili=" + basimYili +
                ", odunc=" + odunc +
                ", oduncAlanKullanici='" + oduncAlanKullanici + '\'' +
                ", ort=" + ort +
                ", oduncAlmaTarihi=" + oduncAlmaTarihi +
                ", kategori='" + kategori + '\'' +
                ", kapak='" + kapak + '\'' +
                '}';
    }
}