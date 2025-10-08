package app.persistence.references;

import app.entities.references.Books;
import app.entities.references.DTO.BookWithTheorist;
import app.entities.references.Theorists;
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
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                bookList.add(new Books(id, title, description));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something something", e.getMessage());
        }
        return bookList;
    }

    public static List<BookWithTheorist> getBookByTheoristAndField(Integer selectedFieldId, Integer selectedTheoristId, ConnectionPool connectionPool) throws DatabaseException {
        List<BookWithTheorist> bookListById = new ArrayList<>();

        String sql = """
         SELECT t.name AS theorist_name,
                b.title,
                b.description
         FROM theorists t
         JOIN theorist_fields tf ON t.id = tf.theorist_id
         JOIN theorist_books tb ON t.id = tb.theorist_id
         JOIN books b ON tb.book_id = b.id
         WHERE tf.field_id = ?
           AND t.id = ?;
        """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, selectedFieldId);
            ps.setInt(2,selectedTheoristId );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String theoristName = rs.getString("theorist_name");
                String title = rs.getString("title");
                String description = rs.getString("description");

                bookListById.add(new BookWithTheorist(theoristName, title, description));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Something something", e.getMessage());
        }
        return bookListById;
    }
}