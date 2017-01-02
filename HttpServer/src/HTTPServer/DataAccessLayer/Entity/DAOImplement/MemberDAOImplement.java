package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.MemberDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.MemberVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class MemberDAOImplement implements MemberDAO {
    private final String MEMBER_TABLE_NAME = "`member`";

    private Connection connection = DatabaseConnection.getConnection();
    
    public boolean isMemberExistedInGroup(long groupID, long userID) throws DatabaseException {
        boolean memberExistedInGroup;

        try {
            String SQL = "select 1 " +
                         "from " + MEMBER_TABLE_NAME + " " +
                         "where group_id = ? and user_id = ? " +
                         "limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);
            preparedStatement.setLong(2, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            memberExistedInGroup = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return memberExistedInGroup;
    }

    public List<MemberVO> getMemberListByGroupID(long groupID) throws DatabaseException {
        List<MemberVO> memberVOList = new ArrayList<>();

        try {
            String SQL = "select * " +
                         "from " + MEMBER_TABLE_NAME + " " +
                         "where group_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                MemberVO memberVO = new MemberVO();
                memberVO.setGroupID(resultSet.getLong(1));
                memberVO.setUserID(resultSet.getLong(2));
                memberVO.setMemberRemark(resultSet.getString(3));
                memberVO.setMemberRole(resultSet.getLong(4));

                memberVOList.add(memberVO);
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return memberVOList;
    }

    public List<Long> getGroupIDListByUserID(long userID) throws DatabaseException {
        List<Long> groupIDList = new ArrayList<>();

        try {
            String SQL = "select group_id " +
                         "from " + MEMBER_TABLE_NAME + " " +
                         "where user_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()) {
                groupIDList.add(resultSet.getLong(1));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return groupIDList;
    }

    public MemberVO getMemberByGroupIDAndUserID(long groupID, long userID) throws DatabaseException {
        MemberVO memberVO = null;

        try {
            String SQL = "select * " +
                         "from " + MEMBER_TABLE_NAME + " " +
                         "where group_id = ? and user_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);
            preparedStatement.setLong(2, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                memberVO = new MemberVO();
                memberVO.setGroupID(resultSet.getLong(1));
                memberVO.setUserID(resultSet.getLong(2));
                memberVO.setMemberRemark(resultSet.getString(3));
                memberVO.setMemberRole(resultSet.getLong(4));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return memberVO;
    }

    public void addMemberByGroupIDAndUserID(long groupID, long userID, long memberRole) throws DatabaseException {
        try {
            String SQL = "insert into " + MEMBER_TABLE_NAME + " " +
                         "(group_id, user_id, member_role) " +
                         "values (?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);
            preparedStatement.setLong(2, userID);
            preparedStatement.setLong(3, memberRole);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }

    public void deleteMemberByGroupIDAndUserID(long groupID, long userID) throws DatabaseException {
        try {
            String SQL = "delete from " + MEMBER_TABLE_NAME + " " +
                         "where group_id = ? and user_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, groupID);
            preparedStatement.setLong(2, userID);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }

    public void updateMemberRemark(long groupID, long userID, String memberRemark) throws DatabaseException {
        updateMemberTableStringColumn(groupID, userID, "member_remark", memberRemark);
    }

    public void updateMemberRole(long groupID, long userID, long memberRole) throws DatabaseException {
        updateUserTableLongColumn(groupID, userID, "member_role", memberRole);
    }

    private void updateMemberTableStringColumn(long groupID, long userID, String columnName, String value) throws DatabaseException {
        try {
            String SQL = "update " + MEMBER_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where group_id = ? and user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, value);
            preparedStatement.setLong(2, groupID);
            preparedStatement.setLong(3, userID);

            if (preparedStatement.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }
    }

    private void updateUserTableLongColumn(long groupID, long userID, String columnName, Long value) throws DatabaseException {
        try {
            String SQL = "update " + MEMBER_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where group_id = ? and user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, value);
            preparedStatement.setLong(2, groupID);
            preparedStatement.setLong(3, userID);

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
