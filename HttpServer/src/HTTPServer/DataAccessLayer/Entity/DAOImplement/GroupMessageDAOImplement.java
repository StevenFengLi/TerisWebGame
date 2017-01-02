package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.GroupMessageDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.GroupMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class GroupMessageDAOImplement implements GroupMessageDAO {
    private final String GROUP_MESSAGE_TABLE_NAME = "`group_message`";

    private Connection connection = DatabaseConnection.getConnection();
    
    public boolean isGroupMessageExisted(long groupMessageID) throws DatabaseException {
        boolean groupMessageExisted;

        try {
            String SQL = "select 1 " +
                         "from " + GROUP_MESSAGE_TABLE_NAME + " " +
                         "where group_message_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            groupMessageExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupMessageExisted;
    }

    public GroupMessageVO getGroupMessageByGroupMessageID(long groupMessageID) throws DatabaseException {
        GroupMessageVO groupMessageVO = null;

        try {
            String SQL = "select * " +
                         "from " + GROUP_MESSAGE_TABLE_NAME + " " +
                         "where group_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupMessageID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupMessageVO = new GroupMessageVO();
                groupMessageVO.setGroupMessageID(resultSet.getLong(1));
                groupMessageVO.setGroupID(resultSet.getLong(2));
                groupMessageVO.setSenderUserID(resultSet.getLong(3));
                groupMessageVO.setGroupMessageText(resultSet.getString(4));
                groupMessageVO.setGroupMessageTime(resultSet.getTimestamp(5));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupMessageVO;
    }

    public List<GroupMessageVO> getGroupMessageListByGroupID(long groupID) throws DatabaseException {
        List<GroupMessageVO> groupMessageList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " + GROUP_MESSAGE_TABLE_NAME + " " +
                         "where group_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                GroupMessageVO groupMessageVO = new GroupMessageVO();
                groupMessageVO.setGroupMessageID(resultSet.getLong(1));
                groupMessageVO.setGroupID(resultSet.getLong(2));
                groupMessageVO.setSenderUserID(resultSet.getLong(3));
                groupMessageVO.setGroupMessageText(resultSet.getString(4));
                groupMessageVO.setGroupMessageTime(resultSet.getTimestamp(5));

                groupMessageList.add(groupMessageVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupMessageList;
    }

    public GroupMessageVO addGroupMessage(long groupID, long senderUserID, String groupMessageText,
                                          Timestamp groupMessageTime) throws DatabaseException {
        GroupMessageVO groupMessageVO = null;

        try {
            String SQL1 = "insert into " + GROUP_MESSAGE_TABLE_NAME + " " +
                          "(group_id, sender_user_id, group_message_text, group_message_time) " +
                          "values (?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL1);
            preparedStatement.setLong(1, groupID);
            preparedStatement.setLong(2, senderUserID);
            preparedStatement.setString(3, groupMessageText);
            preparedStatement.setTimestamp(4, groupMessageTime);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();

            String SQL2 = "select * " +
                          "from " + GROUP_MESSAGE_TABLE_NAME + " " +
                          "where group_message_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                groupMessageVO = new GroupMessageVO();
                groupMessageVO.setGroupMessageID(resultSet.getLong(1));
                groupMessageVO.setGroupID(resultSet.getLong(2));
                groupMessageVO.setSenderUserID(resultSet.getLong(3));
                groupMessageVO.setGroupMessageText(resultSet.getString(4));
                groupMessageVO.setGroupMessageTime(resultSet.getTimestamp(5));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupMessageVO;
    }

    public void deleteGroupMessageByGroupMessageID(long groupMessageID) throws DatabaseException {
        try {
            String SQL = "delete from " + GROUP_MESSAGE_TABLE_NAME + " " +
                         "where group_message_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupMessageID);

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
