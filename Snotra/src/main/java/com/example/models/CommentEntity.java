package com.example.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "yorumlar")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kitap_id", nullable = false)
    private Long kitapId;

    @Column(name = "kullanici_id", nullable = false)
    private Long kullaniciId;

    @Column(name = "yorum", nullable = false)
    private String yorum;

    @Column(name = "puan", nullable = false)
    private int puan;

    @Column(name = "yorum_tarihi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date yorumTarihi;

    @Column(name = "yazar_ad")
    private String yazarAd;

    @Column(name = "yazar_soyad")
    private String yazarSoyad;

    public CommentEntity() {
    }

    public CommentEntity(Long id, Long kitapId, Long kullaniciId, String yorum, int puan, Date yorumTarihi, String yazarAd, String yazarSoyad) {
        this.id = id;
        this.kitapId = kitapId;
        this.kullaniciId = kullaniciId;
        this.yorum = yorum;
        this.puan = puan;
        this.yorumTarihi = yorumTarihi;
        this.yazarAd = yazarAd;
        this.yazarSoyad = yazarSoyad;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", kitapId=" + kitapId +
                ", kullaniciId=" + kullaniciId +
                ", yorum='" + yorum + '\'' +
                ", puan=" + puan +
                ", yorumTarihi=" + yorumTarihi +
                ", yazarAd='" + yazarAd + '\'' +
                ", yazarSoyad='" + yazarSoyad + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKitapId() {
        return kitapId;
    }

    public void setKitapId(Long kitapId) {
        this.kitapId = kitapId;
    }

    public Long getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(Long kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public Date getYorumTarihi() {
        return yorumTarihi;
    }

    public void setYorumTarihi(Date yorumTarihi) {
        this.yorumTarihi = yorumTarihi;
    }

    public String getYazarAd() {
        return yazarAd;
    }

    public void setYazarAd(String yazarAd) {
        this.yazarAd = yazarAd;
    }

    public String getYazarSoyad() {
        return yazarSoyad;
    }

    public void setYazarSoyad(String yazarSoyad) {
        this.yazarSoyad = yazarSoyad;
    }
}
