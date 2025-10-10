package app.persistence;

import app.entities.Word;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class WordMapper {


    public Word getWord(String language) throws DatabaseException {
    String sql="";
        Random rdm = new Random();

        if (language.equals("English"))
        {
             sql = "select word from words where id = ?";
        }else if (language.equals("French"))
        {
            sql = "select frenchword from frenchwords where id = ?";
        }
        try (
                Connection connection = ConnectionPool.getInstance().getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {

            ps.setInt(1, rdm.nextInt(getLengthOfList()));
            ResultSet rs = ps.executeQuery();
            rs.next();

            return new Word(rs.getString(1));

        } catch (SQLException e)
        {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }


    private int getLengthOfList() throws DatabaseException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        String sql = "select count(*) from words";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }
}
