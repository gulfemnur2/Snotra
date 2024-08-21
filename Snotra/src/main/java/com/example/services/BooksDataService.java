package com.example.services;

import com.example.models.BookModel;
import com.example.models.BookMapper;
import com.example.models.CoverModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Repository
public class BooksDataService implements BookInterface<BookModel> {
    private final JdbcTemplate jdbcTemplate;

    public BooksDataService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BookModel getById(long id) {
        try {
            List<BookModel> results = jdbcTemplate.query("SELECT * FROM kitaplar WHERE id = ?",
                    new BookMapper(), id);
            return results.isEmpty() ? null : results.get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> getOrders() {
        try {
            return jdbcTemplate.query("SELECT * FROM kitaplar", new BookMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> searchOrders(String searchTerm) {
        try {
            return jdbcTemplate.query("SELECT * FROM kitaplar WHERE baslik LIKE ?",
                    new BookMapper(), "%" + searchTerm + "%");
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public long addOne(BookModel newOrder) {
        try {
            return jdbcTemplate.update("INSERT INTO kitaplar (baslik, yazar, sayfa, kapak) VALUES (?,?,?,?)",
                    newOrder.getBaslik(), newOrder.getYazar(), newOrder.getSayfa(), newOrder.getKapak());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public boolean deleteOne(long id) {
        try {
            int result = jdbcTemplate.update("DELETE FROM kitaplar WHERE id = ?", id);
            return result > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public BookModel updateOne(long idToUpdate, BookModel updateOrder) {
        try {
            int result = jdbcTemplate.update("UPDATE kitaplar SET baslik = ?, yazar = ?, sayfa = ?, kapak = ?, aciklama = ?, yayinevi = ?, basim_yili = ? WHERE id = ?",
                    updateOrder.getBaslik(), updateOrder.getYazar(), updateOrder.getSayfa(), updateOrder.getKapak(), updateOrder.getAciklama(), updateOrder.getYayinevi(), updateOrder.getBasimYili(), idToUpdate);
            return result > 0 ? updateOrder : null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> sortOrders(String sortField, boolean ascending) {
        String sortDirection = ascending ? "ASC" : "DESC";
        String sql = "";

        switch (sortField) {
            case "baslik":
                sql = "SELECT * FROM kitaplar ORDER BY baslik " + sortDirection;
                break;
            case "basimYili":
                sql = "SELECT * FROM kitaplar ORDER BY basim_yili " + sortDirection;
                break;
            case "ort":
                sql = "SELECT * FROM kitaplar ORDER BY ort " + sortDirection;
                break;
            case "sayfa":
                sql = "SELECT * FROM kitaplar ORDER BY sayfa " + sortDirection;
                break;
            default:
                return getOrders();
        }

        try {
            return jdbcTemplate.query(sql, new BookMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean isOduncSuresiGecti(BookModel kitap) {
        try {
            String sql = "SELECT odunc_alma_tarihi FROM kitaplar WHERE id = ?";
            LocalDateTime oduncAlmaTarihi = jdbcTemplate.queryForObject(sql, new Object[]{kitap.getId()}, LocalDateTime.class);

            if (oduncAlmaTarihi == null) {
                return false;
            }

            LocalDateTime now = LocalDateTime.now();

            long daysBetween = ChronoUnit.DAYS.between(oduncAlmaTarihi, now);
            return daysBetween > 14;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public List<BookModel> getByKategori(String kategori) {
        try {
            List<BookModel> results = jdbcTemplate.query("SELECT * FROM kitaplar WHERE kategori = ?",
                    new BookMapper(), kategori);
            return results;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> getBySayfa(int min, int max) {
        try {
            String sql = "SELECT * FROM kitaplar WHERE sayfa BETWEEN ? AND ?";
            return jdbcTemplate.query(sql, new BookMapper(), min, max);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> getByMinSayfa(int min) {
        try {
            String sql = "SELECT * FROM kitaplar WHERE sayfa >= ?";
            return jdbcTemplate.query(sql, new BookMapper(), min);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BookModel> getByMaxSayfa(int max) {
        try {
            String sql = "SELECT * FROM kitaplar WHERE sayfa <= ?";
            return jdbcTemplate.query(sql, new BookMapper(), max);
        } catch (DataAccessException e) {
            return null;
        }
    }
}