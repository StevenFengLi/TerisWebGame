package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.GroupDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.GroupVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class GroupDAOImplement implements GroupDAO {
    private final String GROUP_TABLE_NAME = "`group`";

    private Connection connection = DatabaseConnection.getConnection();

    public boolean isGroupIDExisted(long groupID) throws DatabaseException {
        boolean groupIDExisted = false;

        try {
            String SQL = "select 1 " +
                         "from " + GROUP_TABLE_NAME + " " +
                         "where group_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            groupIDExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupIDExisted;
    }

    public GroupVO getGroupByGroupID(long groupID) throws DatabaseException {
        GroupVO groupVO = null;

        try {
            String SQL = "select * " +
                         "from " + GROUP_TABLE_NAME + " " +
                         "where group_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                groupVO = new GroupVO();
                groupVO.setGroupID(resultSet.getLong(1));
                groupVO.setGroupName(resultSet.getString(2));
                groupVO.setGroupAnnouncement(resultSet.getString(3));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupVO;
    }

    public List<GroupVO> getGroupListByGroupName(String groupName) throws DatabaseException {
        List<GroupVO> groupVOList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " + GROUP_TABLE_NAME + " " +
                         "where group_name = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, groupName);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                GroupVO groupVO = new GroupVO();
                groupVO.setGroupID(resultSet.getLong(1));
                groupVO.setGroupName(resultSet.getString(2));
                groupVO.setGroupAnnouncement(resultSet.getString(3));

                groupVOList.add(groupVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupVOList;
    }

    public GroupVO addGroup(String groupName) throws DatabaseException {
        GroupVO groupVO = null;

        try {
            String SQL1 = "insert into " + GROUP_TABLE_NAME + " " +
                          "(group_name) " +
                          "values (?);";

            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL1, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement1.setString(1, groupName);

            if (preparedStatement1.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement1.close();

            String SQL2 = "select * " +
                          "from " + GROUP_TABLE_NAME + " " +
                          "where group_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                groupVO = new GroupVO();
                groupVO.setGroupID(resultSet.getLong(1));
                groupVO.setGroupName(resultSet.getString(2));
                groupVO.setGroupAnnouncement(resultSet.getString(3));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupVO;
    }

    public void deleteGroupByGroupID(long groupID) throws DatabaseException {
        try {
            String SQL = "delete from " + GROUP_TABLE_NAME + " " +
                         "where group_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }

    public void updateGroupName(long groupID, String groupName) throws DatabaseException {
        updateGroupTableStringColumn(groupID, "group_name", groupName);
    }

    public void updateGroupAnnouncement(long groupID, String groupAnnouncement) throws DatabaseException {
        updateGroupTableStringColumn(groupID, "group_announcement", groupAnnouncement);
    }

    private void updateGroupTableStringColumn(long groupID, String columnName,
                                              String value) throws DatabaseException {
        try {
            String SQL = "update " + GROUP_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where group_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, value);
            preparedStatement.setLong(2, groupID);

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
