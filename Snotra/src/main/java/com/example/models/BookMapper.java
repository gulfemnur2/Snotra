package com.example.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<BookModel> {
    @Override
    public BookModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookModel order = new BookModel();
        return order;
    }
}