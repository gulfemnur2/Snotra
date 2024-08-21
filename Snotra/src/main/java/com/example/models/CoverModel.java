package com.example.models;

import org.springframework.stereotype.Component;

@Component
public class CoverModel {
    private Long id;
    private Long bookId;
    private String filePath = "upload-dir";

    public CoverModel() {
    }

    public CoverModel(Long id, Long bookId, String filePath) {
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
