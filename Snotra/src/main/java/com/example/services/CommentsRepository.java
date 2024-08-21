package com.example.services;

import com.example.models.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByKitapId(Long kitapId);
    List<CommentEntity> findByYorumContainingIgnoreCase(String searchTerm);
    CommentEntity findByKullaniciIdAndKitapId(Long kullaniciId, Long kitapId);
    @Query("SELECT AVG(y.puan) FROM CommentEntity y WHERE y.kitapId = :kitapId")
    Long findAveragePuanByKitapId(@Param("kitapId") Long kitapId);
}