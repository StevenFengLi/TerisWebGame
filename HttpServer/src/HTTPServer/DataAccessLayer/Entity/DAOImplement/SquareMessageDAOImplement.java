package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.SquareMessageDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.SquareMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class SquareMessageDAOImplement implements SquareMessageDAO {
    private final String SQUARE_MESSAGE_TABLE_NAME = "`square_message`";

    private Connection connection = DatabaseConnection.getConnection();
    
    public boolean isSquareMessageExisted(long squareMessageID) throws DatabaseException {
        boolean squareMessageExisted;

        try {
            String SQL = "select 1 " +
                         "from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                         "where square_message_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, squareMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            squareMessageExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageExisted;
    }

    public SquareMessageVO getSquareMessageBySquareMessageID(long squareMessageID) throws DatabaseException {
        SquareMessageVO squareMessageVO = null;

        try {
            String SQL = "select * " +
                         "from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                         "where square_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, squareMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                squareMessageVO = new SquareMessageVO();
                squareMessageVO.setSquareMessageID(resultSet.getLong(1));
                squareMessageVO.setSenderUserID(resultSet.getLong(2));
                squareMessageVO.setSquareMessageText(resultSet.getString(3));
                squareMessageVO.setSquareMessageTime(resultSet.getTimestamp(4));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageVO;
    }

    public List<SquareMessageVO> getSquareMessageListBySenderID(long senderUserID) throws DatabaseException {
        List<SquareMessageVO> squareMessageList = new ArrayList<>();

        try {
            String SQL = "select * " +
                    "from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                    "where sender_user_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, senderUserID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                SquareMessageVO squareMessageVO = new SquareMessageVO();
                squareMessageVO.setSquareMessageID(resultSet.getLong(1));
                squareMessageVO.setSenderUserID(resultSet.getLong(2));
                squareMessageVO.setSquareMessageText(resultSet.getString(3));
                squareMessageVO.setSquareMessageTime(resultSet.getTimestamp(4));

                squareMessageList.add(squareMessageVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageList;
    }

    public List<SquareMessageVO> getTenSquareMessagesBeforeTime(Timestamp timestamp) throws DatabaseException {
        List<SquareMessageVO> squareMessageList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " +
                         "    (select * " +
                         "     from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                         "     where square_message_time < ?) as temp " +
                         "order by square_message_time desc " +
                         "limit 10;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setTimestamp(1, timestamp);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                SquareMessageVO squareMessageVO = new SquareMessageVO();
                squareMessageVO.setSquareMessageID(resultSet.getLong(1));
                squareMessageVO.setSenderUserID(resultSet.getLong(2));
                squareMessageVO.setSquareMessageText(resultSet.getString(3));
                squareMessageVO.setSquareMessageTime(resultSet.getTimestamp(4));

                squareMessageList.add(squareMessageVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageList;
    }

    public List<SquareMessageVO> getSquareMessageListAfterTime(Timestamp timestamp) throws DatabaseException {
        List<SquareMessageVO> squareMessageList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " +
                         "    (select * " +
                         "     from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                         "     where square_message_time > ?) as time " +
                         "order by square_message_time desc;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setTimestamp(1, timestamp);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                SquareMessageVO squareMessageVO = new SquareMessageVO();
                squareMessageVO.setSquareMessageID(resultSet.getLong(1));
                squareMessageVO.setSenderUserID(resultSet.getLong(2));
                squareMessageVO.setSquareMessageText(resultSet.getString(3));
                squareMessageVO.setSquareMessageTime(resultSet.getTimestamp(4));

                squareMessageList.add(squareMessageVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageList;
    }

    public SquareMessageVO addSquareMessage(long senderUserID, String squareMessageText,
                                            Timestamp squareMessageTime) throws DatabaseException {
        SquareMessageVO squareMessageVO = null;

        try {
            String SQL1 = "insert into " + SQUARE_MESSAGE_TABLE_NAME + " " +
                          "(sender_user_id, square_message_text, square_message_time) " +
                          "values (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setLong(1, senderUserID);
            preparedStatement.setString(2, squareMessageText);
            preparedStatement.setTimestamp(3, squareMessageTime);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();

            String SQL2 = "select * " +
                          "from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                          "where square_message_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                squareMessageVO = new SquareMessageVO();
                squareMessageVO.setSquareMessageID(resultSet.getLong(1));
                squareMessageVO.setSenderUserID(resultSet.getLong(2));
                squareMessageVO.setSquareMessageText(resultSet.getString(3));
                squareMessageVO.setSquareMessageTime(resultSet.getTimestamp(4));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return squareMessageVO;
    }

    public void deleteSquareMessageBySquareMessageID(long squareMessageID) throws DatabaseException {
        try {
            String SQL = "delete from " + SQUARE_MESSAGE_TABLE_NAME + " " +
                         "where square_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, squareMessageID);

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
