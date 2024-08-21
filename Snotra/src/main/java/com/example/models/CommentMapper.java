package com.example.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<CommentEntity> {
    @Override
    public CommentEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommentEntity yorum = new CommentEntity();
        yorum.setId(rs.getLong("id"));
        yorum.setKitapId(rs.getLong("kitap_id"));
        yorum.setKullaniciId(rs.getLong("kullanici_id"));
        yorum.setYorum(rs.getString("yorum"));
        yorum.setPuan(rs.getInt("puan"));
        yorum.setYorumTarihi(rs.getTimestamp("yorum_tarihi"));
        return yorum;
    }
}