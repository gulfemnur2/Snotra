package com.example.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoverMapper implements RowMapper<CoverModel> {
    @Override
    public CoverModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        CoverModel cover = new CoverModel();
        cover.setId(rs.getLong("id"));
        cover.setBookId(rs.getLong("kitap_id"));
        cover.setFilePath(rs.getString("dosya_yolu"));
        return cover;
    }
}
