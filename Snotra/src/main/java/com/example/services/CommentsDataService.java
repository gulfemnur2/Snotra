package com.example.services;

import com.example.models.CommentEntity;
import com.example.models.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommentsDataService implements CommentInterface<CommentEntity> {
    @Autowired
    private CommentsRepository commentsRepository;
    private final JdbcTemplate jdbcTemplate;

    public CommentsDataService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CommentEntity getById(long id) {
        try {
            List<CommentEntity> results = jdbcTemplate.query("SELECT * FROM yorumlar WHERE id = ?",
                    new CommentMapper(), id);
            return results.isEmpty() ? null : results.get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CommentEntity> getYorumlar() {
        try {
            return jdbcTemplate.query("SELECT * FROM yorumlar", new CommentMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CommentEntity> ara(String searchTerm) {
        try {
            return jdbcTemplate.query("SELECT * FROM yorumlar WHERE yorum LIKE ?",
                    new CommentMapper(), "%" + searchTerm + "%");
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public long ekle(CommentEntity newYorum) {
        try {
            return jdbcTemplate.update("INSERT INTO yorumlar (kitap_id, kullanici_id, yorum, puan) VALUES (?,?,?,?)",
                    newYorum.getKitapId(), newYorum.getKullaniciId(), newYorum.getYorum(), newYorum.getPuan());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    public long ekleWithRepository(CommentEntity newYorum) {
        CommentEntity result = commentsRepository.save(newYorum);
        return result != null ? result.getId() : 0;
    }

    @Override
    public boolean sil(long id) {
        try {
            int result = jdbcTemplate.update("DELETE FROM yorumlar WHERE id = ?", id);
            return result > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public CommentEntity guncelle(long idToUpdate, CommentEntity updateYorum) {
        try {
            int result = jdbcTemplate.update("UPDATE yorumlar SET kitap_id = ?, kullanici_id = ?, yorum = ?, puan = ? WHERE id = ?",
                    updateYorum.getKitapId(), updateYorum.getKullaniciId(), updateYorum.getYorum(), updateYorum.getPuan(), idToUpdate);
            return result > 0 ? updateYorum : null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CommentEntity> getYorumlarByKitapId(long kitapId) {
        try {
            return jdbcTemplate.query("SELECT * FROM yorumlar WHERE kitap_id = ?", new CommentMapper(), kitapId);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Long getAveragePuanByKitapId(long kitapId) {
        try {
            String sql = "SELECT AVG(puan) FROM yorumlar WHERE kitap_id = ?";
            return jdbcTemplate.queryForObject(sql, Long.class, kitapId);
        } catch (DataAccessException e) {
            return null;
        }
    }
    @Override
    public void updateKitapPuanOrtalama(long kitapId) {
        try {
            String avgSql = "SELECT AVG(puan) FROM yorumlar WHERE kitap_id = ?";
            Double ortalamaPuan = jdbcTemplate.queryForObject(avgSql, Double.class, kitapId);

            if (ortalamaPuan == null) {
                ortalamaPuan = 0.0;
            }

            String updateSql = "UPDATE kitaplar SET ort = ? WHERE id = ?";
            jdbcTemplate.update(updateSql, ortalamaPuan, kitapId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CommentEntity getYorumByKullaniciIdAndKitapId(Long kullaniciId, Long kitapId) {
        try {
            List<CommentEntity> results = jdbcTemplate.query(
                    "SELECT * FROM yorumlar WHERE kullanici_id = ? AND kitap_id = ?",
                    new CommentMapper(), kullaniciId, kitapId);
            return results.isEmpty() ? null : results.get(0);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}