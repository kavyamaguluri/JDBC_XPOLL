package com.crio.xpoll.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crio.xpoll.model.Choice;
import com.crio.xpoll.model.Poll;
import com.crio.xpoll.model.PollSummary;
import com.crio.xpoll.util.DatabaseConnection;


public class PollDAO {

    private final DatabaseConnection databaseConnection;

    /**
     * Constructs a PollDAO with the specified DatabaseConnection.
     *
     * @param databaseConnection The DatabaseConnection to be used for database operations.
     */
    public PollDAO(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    /**
     * Creates a new poll with the specified question and choices.
     *
     * @param userId   The ID of the user creating the poll.
     * @param question The question for the poll.
     * @param choices  A list of choices for the poll.
     * @return The created Poll object with its associated choices.
     * @throws SQLException If a database error occurs during poll creation.
     */
    public Poll createPoll(int userId, String question, List<String> choices) throws SQLException {
        String PollQuery = "INSERT INTO polls (user_id, question, is_closed) VALUES (?, ?, ?)";
        String ChoiceQuery = "INSERT INTO choices (poll_id, choice_text) VALUES (?, ?)";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pollStatement = conn.prepareStatement(PollQuery, Statement.RETURN_GENERATED_KEYS)) {

           
            pollStatement.setInt(1, userId);
            pollStatement.setString(2, question);
            pollStatement.setBoolean(3, false); 
            pollStatement.executeUpdate();

            
            try (ResultSet generatedKeys = pollStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int pollId = generatedKeys.getInt(1);

                    
                    try (PreparedStatement choiceStatement = conn.prepareStatement(ChoiceQuery)) {
                        for (String choice : choices) {
                            choiceStatement.setInt(1, pollId);
                            choiceStatement.setString(2, choice);
                            choiceStatement.addBatch();
                        }
                        choiceStatement.executeBatch();
                    }

                    
            List<Choice> choiceList = new ArrayList<>();
            String ChoicesSQL = "SELECT id, choice_text FROM choices WHERE poll_id = ?";
            try (PreparedStatement selectChoicesStatement = conn.prepareStatement(ChoicesSQL)) {
                selectChoicesStatement.setInt(1, pollId);
                try (ResultSet resultSet = selectChoicesStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int choiceId = resultSet.getInt("id");
                        String choiceText = resultSet.getString("choice_text");
                        choiceList.add(new Choice(choiceId, pollId, choiceText));
                    }
                }
            }

                    return new Poll(pollId, userId, question, choiceList, false);

                }
            }
        }
        return null; 
    }

    /**
     * Retrieves a poll by its ID.
     *
     * @param pollId The ID of the poll to retrieve.
     * @return The Poll object with its associated choices.
     * @throws SQLException If a database error occurs or the poll is not found.
     */
    public Poll getPoll(int pollId) throws SQLException {
        String pollSql = "SELECT * FROM polls WHERE id = ?";
        String choiceSql = "SELECT * FROM choices WHERE poll_id = ?";
        
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement pollStatement = connection.prepareStatement(pollSql);
             PreparedStatement choiceStatement = connection.prepareStatement(choiceSql)) {

            pollStatement.setInt(1, pollId);
            Poll poll = null;

            try (ResultSet pollResultSet = pollStatement.executeQuery()) {
                if (pollResultSet.next()) {
                    int userId = pollResultSet.getInt("user_id");
                    String question = pollResultSet.getString("question");
                    boolean isClosed = pollResultSet.getBoolean("is_closed");

                    choiceStatement.setInt(1, pollId);
                    List<Choice> choices = new ArrayList<>();

                    try (ResultSet choiceResultSet = choiceStatement.executeQuery()) {
                        while (choiceResultSet.next()) {
                            int choiceId = choiceResultSet.getInt("id");
                            String choiceText = choiceResultSet.getString("choice_text");
                            choices.add(new Choice(choiceId, pollId, choiceText));
                        }
                    }

                    poll = new Poll(pollId, userId, question, choices, isClosed);
                }
            }
            return poll;
        } catch (SQLException e) {
            throw new SQLException("Error retrieving poll: " + e.getMessage(), e);
        }
    }

    /**
     * Closes a poll by updating its status in the database.
     *
     * @param pollId The ID of the poll to close.
     * @throws SQLException If a database error occurs during the update.
     */
    public void closePoll(int pollId) throws SQLException {
        String sql = "UPDATE polls SET is_closed = ? WHERE id = ?";
        
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, true);
            statement.setInt(2, pollId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error closing poll: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a list of poll summaries for the specified poll.
     *
     * @param pollId The ID of the poll for which to retrieve summaries.
     * @return A list of PollSummary objects containing the poll question, choice text, and response count.
     * @throws SQLException If a database error occurs during the query.
     */
    
    public List<PollSummary> getPollSummaries(int pollId) throws SQLException {

        String sql = "select * from poll_summaries where poll_id =? ";
        List<PollSummary> pollList = new ArrayList<>();


        try (Connection connection = databaseConnection.getConnection()) {

            try (PreparedStatement pStatement = connection.prepareStatement(sql)) {

                pStatement.setInt(1, pollId);
                try (ResultSet resultSet = pStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String question = resultSet.getString(2);
                        String choiceText = resultSet.getString(3);
                        int responseCount = resultSet.getInt(4);


                        pollList.add(new PollSummary(question, choiceText, responseCount));
                    }

                }

            }
        }

        return pollList;
    }
}


