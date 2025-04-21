package com.crio.xpoll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.crio.xpoll.model.Response;
import com.crio.xpoll.util.DatabaseConnection;


public class ResponseDAO {
    private final DatabaseConnection databaseConnection;

    public ResponseDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Response createResponse(int pollId, int choiceId, int userId) throws SQLException {
        String sql = "INSERT INTO responses (poll_id,choice_id,user_id) VALUES (?,?, ?)";

        try(Connection connection=databaseConnection.getConnection();
                    PreparedStatement prepStatement=connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
                        prepStatement.setInt(1, pollId);
                        prepStatement.setInt(2, choiceId);
                        prepStatement.setInt(3, userId);
                        prepStatement.executeUpdate();
                        return new Response(pollId, choiceId, userId);
                     }
    
    }
    
}


