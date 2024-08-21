package com.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "kapaklar")
public class CoverEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "kitap_id")
    private Long bookId;

    @Column(name = "dosya_yolu")
    private String filePath;

    public CoverEntity() {
    }

    public CoverEntity(Long id, Long bookId, String filePath) {
        this.id = id;
        this.bookId = bookId;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "CoverModel{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", filePath='" + filePath + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
