package com.example.services;

import com.example.models.CommentEntity;

import java.util.List;

public interface CommentInterface<T> {
    T getById(long id);
    List<T> getYorumlar();
    List<T> ara(String searchTerm);
    long ekle(T newYorum);
    boolean sil(long id);
    T guncelle(long idToUpdate, T updateYorum);
    List<T> getYorumlarByKitapId(long kitapId);
    Long getAveragePuanByKitapId(long kitapId);
    void updateKitapPuanOrtalama(long kitapId);
    CommentEntity getYorumByKullaniciIdAndKitapId(Long id, Long id1);
}