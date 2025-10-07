package app.persistence.references;

import app.entities.references.Books;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BooksMapper {

    public static List<Books> getAllBooks(ConnectionPool connectionPool) throws DatabaseException {
        List<Books> bookList = new ArrayList<>();

        String sql = "SELECT id, title, description FROM books " +
                "ORDER BY title DESC;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                bookList.add(new Books(id, name, description));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something something", e.getMessage());
        }
        return bookList;
    }
}
