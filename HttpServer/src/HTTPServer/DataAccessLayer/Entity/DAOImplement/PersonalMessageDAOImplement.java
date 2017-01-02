package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.PersonalMessageDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.PersonalMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class PersonalMessageDAOImplement implements PersonalMessageDAO {
    private final String PERSONAL_MESSAGE_TABLE_NAME = "`personal_message`";

    private Connection connection = DatabaseConnection.getConnection();

    public boolean isPersonalMessageExisted(long personalMessageID) throws DatabaseException {
        boolean personalMessageExisted;

        try {
            String SQL = "select 1 " +
                         "from " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                         "where personal_message_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, personalMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            personalMessageExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return personalMessageExisted;
    }

    public PersonalMessageVO getPersonalMessageByPersonalMessageID(long personalMessageID)
            throws DatabaseException {
        PersonalMessageVO personalMessageVO = null;

        try {
            String SQL = "select * " +
                         "from " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                         "where personal_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, personalMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                personalMessageVO = new PersonalMessageVO();
                personalMessageVO.setPersonalMessageID(resultSet.getLong(1));
                personalMessageVO.setSenderUserID(resultSet.getLong(2));
                personalMessageVO.setReceiverUserID(resultSet.getLong(3));
                personalMessageVO.setPersonalMessageText(resultSet.getString(4));
                personalMessageVO.setPersonalMessageTime(resultSet.getTimestamp(5));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return personalMessageVO;
    }

    public List<PersonalMessageVO> getPersonalMessageListByTwoUserIDs(long userID1, long userID2)
            throws DatabaseException {
        List<PersonalMessageVO> personalMessageList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                         "where (sender_user_id = ? and receiver_user_id = ?) or (sender_user_id = ? and receiver_user_id = ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID1);
            preparedStatement.setLong(2, userID2);
            preparedStatement.setLong(3, userID2);
            preparedStatement.setLong(4, userID1);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                PersonalMessageVO personalMessageVO = new PersonalMessageVO();
                personalMessageVO.setPersonalMessageID(resultSet.getLong(1));
                personalMessageVO.setSenderUserID(resultSet.getLong(2));
                personalMessageVO.setReceiverUserID(resultSet.getLong(3));
                personalMessageVO.setPersonalMessageText(resultSet.getString(4));
                personalMessageVO.setPersonalMessageTime(resultSet.getTimestamp(5));

                personalMessageList.add(personalMessageVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return personalMessageList;
    }

    public PersonalMessageVO addPersonalMessage(long senderUserID, long receiverUserID, String personalMessageText,
                                                Timestamp personalMessageTime) throws DatabaseException {
        PersonalMessageVO personalMessageVO = null;

        try {
            String SQL1 = "insert into " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                          "(sender_user_id, receiver_user_id, personal_message_text, personal_message_time) " +
                          "values (?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setLong(1, senderUserID);
            preparedStatement.setLong(2, receiverUserID);
            preparedStatement.setString(3, personalMessageText);
            preparedStatement.setTimestamp(4, personalMessageTime);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();

            String SQL2 = "select * " +
                          "from " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                          "where personal_message_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                personalMessageVO = new PersonalMessageVO();
                personalMessageVO.setPersonalMessageID(resultSet.getLong(1));
                personalMessageVO.setSenderUserID(resultSet.getLong(2));
                personalMessageVO.setReceiverUserID(resultSet.getLong(3));
                personalMessageVO.setPersonalMessageText(resultSet.getString(4));
                personalMessageVO.setPersonalMessageTime(resultSet.getTimestamp(5));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return personalMessageVO;
    }

    public void deletePersonalMessageByPersonalMessageID(long personalMessageID) throws DatabaseException {
        try {
            String SQL = "delete from " + PERSONAL_MESSAGE_TABLE_NAME + " " +
                         "where personal_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, personalMessageID);

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
