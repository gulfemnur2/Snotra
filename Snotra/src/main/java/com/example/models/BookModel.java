package com.example.models;

import java.time.LocalDateTime;

public class BookModel {
    private Long id;
    private String baslik;
    private String yazar;
    private int sayfa;
    private String aciklama;
    private String yayinevi;
    private int basimYili;
    private boolean odunc;
    private String oduncAlanKullanici;
    private Long ort;
    private LocalDateTime oduncAlmaTarihi;
    private String kategori;
    private String kapak;

    public BookModel() {
    }

    public BookModel(Long id, String baslik, String yazar, int sayfa, String aciklama, String yayinevi, int basimYili, boolean odunc, String oduncAlanKullanici, Long ort, LocalDateTime oduncAlmaTarihi, String kategori, String kapak) {
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
    public int getBasimYili() {
        return basimYili;
    }
    public void setBasimYili(int basimYili) {
        this.basimYili = basimYili;
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
        return "BookModel{" +
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