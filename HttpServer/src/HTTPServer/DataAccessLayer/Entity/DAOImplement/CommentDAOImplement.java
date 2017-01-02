package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.CommentDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.CommentVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class CommentDAOImplement implements CommentDAO {
    private final String COMMENT_TABLE_NAME = "`comment`";

    private Connection connection = DatabaseConnection.getConnection();
    
    public boolean isCommentExisted(long commentID) throws DatabaseException {
        boolean commentExisted;

        try {
            String SQL = "select 1 " +
                         "from " + COMMENT_TABLE_NAME + " " +
                         "where comment_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, commentID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            commentExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return commentExisted;
    }

    public CommentVO getCommentByCommentID(long commentID) throws DatabaseException {
        CommentVO commentVO = null;

        try {
            String SQL = "select * " +
                         "from " + COMMENT_TABLE_NAME + " " +
                         "where comment_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, commentID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                commentVO = new CommentVO();
                commentVO.setCommentID(resultSet.getLong(1));
                commentVO.setSquareMessageID(resultSet.getLong(2));
                commentVO.setCommentRepliedID(resultSet.getLong(3));
                commentVO.setSenderUserID(resultSet.getLong(4));
                commentVO.setCommentText(resultSet.getString(5));
                commentVO.setCommentTime(resultSet.getTimestamp(6));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return commentVO;
    }

    public List<CommentVO> getCommentListBySquareMessageID(long squareMessageID) throws DatabaseException {
        List<CommentVO> commentList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " + COMMENT_TABLE_NAME + " " +
                         "where square_message_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, squareMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentVO commentVO = new CommentVO();
                commentVO.setCommentID(resultSet.getLong(1));
                commentVO.setSquareMessageID(resultSet.getLong(2));
                commentVO.setCommentRepliedID(resultSet.getLong(3));

                commentVO.setSenderUserID(resultSet.getLong(4));
                commentVO.setCommentText(resultSet.getString(5));
                commentVO.setCommentTime(resultSet.getTimestamp(6));

                commentList.add(commentVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return commentList;
    }

    public CommentVO addComment(long squareMessageID, long senderUserID, String commentText, Timestamp commentTime)
            throws DatabaseException {
        CommentVO commentVO = null;

        try {
            String SQL1 = "insert into " + COMMENT_TABLE_NAME + " " +
                          "(square_message_id, sender_user_id, comment_text, comment_time) " +
                          "values (?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setLong(1, squareMessageID);
            preparedStatement.setLong(2, senderUserID);
            preparedStatement.setString(3, commentText);
            preparedStatement.setTimestamp(4, commentTime);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();

            String SQL2 = "select * " +
                          "from " + COMMENT_TABLE_NAME + " " +
                          "where comment_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                commentVO = new CommentVO();
                commentVO.setCommentID(resultSet.getLong(1));
                commentVO.setSquareMessageID(resultSet.getLong(2));
                commentVO.setCommentRepliedID(resultSet.getLong(3));
                commentVO.setSenderUserID(resultSet.getLong(4));
                commentVO.setCommentText(resultSet.getString(5));
                commentVO.setCommentTime(resultSet.getTimestamp(6));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return commentVO;
    }

    public CommentVO addReply(long squareMessageID, long commentRepliedID,
                              long senderUserID, String commentText, Timestamp commentTime)
            throws DatabaseException {
        CommentVO commentVO = null;

        try {
            String SQL1 = "insert into " + COMMENT_TABLE_NAME + " " +
                          "(square_message_id, comment_replied_id, sender_user_id, comment_text, comment_time) " +
                          "values (?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setLong(1, squareMessageID);
            preparedStatement.setLong(2, commentRepliedID);
            preparedStatement.setLong(3, senderUserID);
            preparedStatement.setString(4, commentText);
            preparedStatement.setTimestamp(5, commentTime);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();

            String SQL2 = "select * " +
                          "from " + COMMENT_TABLE_NAME + " " +
                          "where comment_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                commentVO = new CommentVO();
                commentVO.setCommentID(resultSet.getLong(1));
                commentVO.setSquareMessageID(resultSet.getLong(2));
                commentVO.setCommentRepliedID(resultSet.getLong(3));
                commentVO.setSenderUserID(resultSet.getLong(4));
                commentVO.setCommentText(resultSet.getString(5));
                commentVO.setCommentTime(resultSet.getTimestamp(6));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return commentVO;
    }

    public void deleteCommentByCommentID(long commentID) throws DatabaseException {
        try {
            String SQL = "delete from " + COMMENT_TABLE_NAME + " " +
                         "where comment_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, commentID);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }
}
