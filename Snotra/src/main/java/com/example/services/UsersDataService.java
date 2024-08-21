package com.example.services;

import com.example.models.UserMapper;
import com.example.models.UserModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

public class UsersDataService implements UserInterface<UserModel> {
    private final JdbcTemplate jdbcTemplate;

    public UsersDataService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public long addUser(UserModel newUser) {
        try {
            return jdbcTemplate.update("INSERT INTO kullanici (kullanici_adi, sifre, ad, soyad, email) VALUES (?,?,?,?,?)",
                    newUser.getKullaniciAdi(), newUser.getSifre(), newUser.getAd(), newUser.getSoyad(), newUser.getEmail());
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public UserModel findById(Long id) {
        try {
            List<UserModel> results = jdbcTemplate.query("SELECT * FROM kullanici WHERE id = ?",
                    new UserMapper(), id);
            return results.isEmpty() ? null : results.get(0);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserModel> getUsers() {
        try {
            return jdbcTemplate.query("SELECT * FROM kullanici", new UserMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            int result = jdbcTemplate.update("DELETE FROM kullanici WHERE id = ?", id);
            return result > 0;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public UserModel update(long idToUpdate, UserModel updateOrder) {
        try {
            int result = jdbcTemplate.update("UPDATE kullanici SET kullanici_adi = ?, sifre = ?, ad = ?, soyad = ?, email = ? WHERE id = ?",
                    updateOrder.getKullaniciAdi(), updateOrder.getSifre(), updateOrder.getAd(), updateOrder.getSoyad(), updateOrder.getEmail(), idToUpdate);
            return result > 0 ? updateOrder : null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<UserModel> searchUser(String searchTerm) {
        try {
            return jdbcTemplate.query("SELECT * FROM kullanici WHERE kullanici_adi LIKE ?",
                    new UserMapper(), "%" + searchTerm + "%");
        } catch (DataAccessException e) {
            return null;
        }    }

    @Override
    public UserModel authenticateUser(String email, String sifre) {
        try {
            UserModel user = jdbcTemplate.queryForObject(
                    "SELECT * FROM kullanici WHERE email = ?",
                    new Object[]{email},
                    new UserMapper()
            );
            if (user != null && user.getSifre().equals(sifre)) {
                return user;
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Kullanıcı doğrulama hatası. Lütfen tekrar deneyin.");
        }
        return null;
    }

    @Override
    public boolean check(UserModel user, String sifre) {
        try {
            String existingPassword = jdbcTemplate.queryForObject(
                    "SELECT sifre FROM kullanici WHERE id = ?",
                    new Object[]{user.getId()},
                    String.class
            );

            return existingPassword != null && existingPassword.equals(sifre);
        } catch (DataAccessException e) {
            return false;
        }
    }
}