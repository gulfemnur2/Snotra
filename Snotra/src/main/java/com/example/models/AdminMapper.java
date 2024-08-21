package com.example.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper implements RowMapper<AdminModel> {
    @Override
    public AdminModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdminModel admin = new AdminModel();
        return admin;
    }
}