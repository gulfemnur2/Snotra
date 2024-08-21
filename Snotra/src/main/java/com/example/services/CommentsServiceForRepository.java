package com.example.services;

import com.example.models.*;
import com.example.models.CommentEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentsServiceForRepository implements CommentInterface<CommentEntity> {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private BooksRepository booksRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Override
    public CommentEntity getById(long id) {
        CommentEntity entity = commentsRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Yorum bulunamadı");
        }
        return entity;
    }

    @Override
    public List<CommentEntity> getYorumlar() {
        Iterable<CommentEntity> yorumEntities = commentsRepository.findAll();
        List<CommentEntity> models = new ArrayList<>();
        for (CommentEntity item : yorumEntities) {
            models.add(item);
        }
        return models;
    }

    @Override
    public List<CommentEntity> ara(String searchTerm) {
        Iterable<CommentEntity> entities = commentsRepository.findByYorumContainingIgnoreCase(searchTerm);
        List<CommentEntity> yorumlar = new ArrayList<>();
        for (CommentEntity entity : entities) {
            yorumlar.add(entity);
        }
        return yorumlar;
    }

    @Override
    public long ekle(CommentEntity newYorum) {
        newYorum.setYorumTarihi(new Date());
        CommentEntity result = commentsRepository.save(newYorum);
        return result != null ? result.getId() : 0;
    }

    @Override
    public boolean sil(long id) {
        commentsRepository.deleteById(id);
        return true;
    }

    @Override
    public CommentEntity guncelle(long idToUpdate, CommentEntity updateYorum) {
        updateYorum.setId(idToUpdate);
        return commentsRepository.save(updateYorum);
    }

    @Override
    public List<CommentEntity> getYorumlarByKitapId(long kitapId) {
        return commentsRepository.findByKitapId(kitapId);
    }

    @Override
    public Long getAveragePuanByKitapId(long kitapId) {
        return commentsRepository.findAveragePuanByKitapId(kitapId);
    }

    @Override
    public void updateKitapPuanOrtalama(long kitapId) {
        Long ortalamaPuan = getAveragePuanByKitapId(kitapId);
        if (ortalamaPuan == null) {
            ortalamaPuan = null;
        }

        BookEntity kitap = booksRepository.findById(kitapId).orElse(null);
        if (kitap != null) {
            kitap.setOrt(ortalamaPuan);
            booksRepository.save(kitap);
        }
    }

    @Override
    public CommentEntity getYorumByKullaniciIdAndKitapId(Long kullaniciId, Long kitapId) {
        return commentsRepository.findByKullaniciIdAndKitapId(kullaniciId, kitapId);
    }
}