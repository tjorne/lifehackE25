package app.persistence;

import app.entities.User;
import app.entities.gruppeE.MineScore;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class MineMapper {

    public static void addScore(String username, long score, String difficulty)
            throws DatabaseException
    {
        ConnectionPool connectionPool = ConnectionPool.getInstance();

        String sqlUser = "select users.user_id from public.users where users.username=?";

        String sql = "insert into public.scores (user_id, score_value, date, difficulty) values (?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                PreparedStatement psUser = connection.prepareStatement(sqlUser)
        )
        {
            int id;
            psUser.setString(1, username);
            ResultSet rs = psUser.executeQuery();
            if (!rs.next())
                throw new DatabaseException("addScore: No user with name "+username);
            id = rs.getInt(1);
            ps.setInt(1, id);
            ps.setLong(2, score);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setString(4, difficulty);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("addScore: Fejl ved indsættelse af information til scores");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static List<MineScore> getScores()
        throws DatabaseException
    {

        List<MineScore> scoreList = new ArrayList<>();
        String sql = "select users.username, scores.difficulty, scores.score_value, scores.date from public.scores join public.users on users.user_id=scores.user_id";

        ConnectionPool connectionPool = ConnectionPool.getInstance();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                MineScore score = new MineScore();
                score.username = rs.getString("username");
                score.score_value = rs.getInt("score_value");
                score.date = rs.getDate("date");
                score.difficulty = rs.getString("difficulty");
                scoreList.add(score);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("getScores: Fejl!!!!", e.getMessage());
        }
        return scoreList;
    }
}
