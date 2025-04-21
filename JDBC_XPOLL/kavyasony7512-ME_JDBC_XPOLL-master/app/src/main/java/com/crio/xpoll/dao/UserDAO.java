package com.crio.xpoll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.crio.xpoll.model.User;
import com.crio.xpoll.util.DatabaseConnection;

import java.sql.*;

public class UserDAO {
    private final DatabaseConnection dbConnection;

    public UserDAO(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    
    public User createUser(String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
             
            prepstmt.setString(1, username);
            prepstmt.setString(2, password);
            int affectedRows = prepstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new User(generatedKeys.getInt(1), username, password);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

   
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement prepstatement = conn.prepareStatement(sql)) {
             
            prepstatement.setInt(1, userId);
            ResultSet resultSet = prepstatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet.getInt("user_id"), resultSet.getString("username"), resultSet.getString("password"));
            }
            return null;
        }
    }

}
