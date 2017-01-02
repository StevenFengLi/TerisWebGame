package HTTPServer.DataAccessLayer.Entity.DAOImplement;

import HTTPServer.DataAccessLayer.Entity.DAO.UserDAO;
import HTTPServer.DataAccessLayer.Entity.DAODatabaseConnection.DatabaseConnection;
import HTTPServer.DataAccessLayer.Entity.DAOVO.UserVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Date;

public class UserDAOImplement implements UserDAO {
    private final String USER_TABLE_NAME = "`user`";

    private Connection connection = DatabaseConnection.getConnection();

    public boolean isUserIDExisted(long userID) throws DatabaseException {
        boolean userIDExisted = false;

        try {
            String SQL = "select 1 " +
                         "from " + USER_TABLE_NAME + " " +
                         "where user_id = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            userIDExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return userIDExisted;
    }

    public boolean isUserPhoneNumberExisted(String userPhoneNumber) throws DatabaseException {
        boolean userPhoneNumberExisted = false;

        try {
            String SQL = "select 1 " +
                         "from " + USER_TABLE_NAME + " " +
                         "where user_phone_number = ? limit 1;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, userPhoneNumber);

            ResultSet resultSet =  preparedStatement.executeQuery();
            userPhoneNumberExisted = resultSet.next();

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return userPhoneNumberExisted;
    }

    public UserVO getUserByUserID(long userID) throws DatabaseException {
        UserVO userVO = null;

        try {
            String SQL = "select * " +
                         "from " + USER_TABLE_NAME + " " +
                         "where user_id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, userID);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                userVO = new UserVO();
                userVO.setUserID(resultSet.getLong(1));
                userVO.setUserNickname(resultSet.getString(2));
                userVO.setUserPhoneNumber(resultSet.getString(3));
                userVO.setUserPassword(resultSet.getString(4));
                userVO.setUserHeadShowURL(resultSet.getString(5));
                userVO.setUserBirthday(resultSet.getDate(6));
                userVO.setUserSex(resultSet.getLong(7));
                userVO.setUserAreaProvince(resultSet.getString(8));
                userVO.setUserAreaCity(resultSet.getString(9));
                userVO.setUserDescription(resultSet.getString(10));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return userVO;
    }

    public UserVO getUserByUserPhoneNumber(String userPhoneNumber) throws DatabaseException {
        UserVO userVO = null;

        try {
            String SQL = "select * " +
                         "from " + USER_TABLE_NAME + " " +
                         "where user_phone_number = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, userPhoneNumber);

            ResultSet resultSet =  preparedStatement.executeQuery();
            if (resultSet.next()) {
                userVO = new UserVO();
                userVO.setUserID(resultSet.getLong(1));
                userVO.setUserNickname(resultSet.getString(2));
                userVO.setUserPhoneNumber(resultSet.getString(3));
                userVO.setUserPassword(resultSet.getString(4));
                userVO.setUserHeadShowURL(resultSet.getString(5));
                userVO.setUserBirthday(resultSet.getDate(6));
                userVO.setUserSex(resultSet.getLong(7));
                userVO.setUserAreaProvince(resultSet.getString(8));
                userVO.setUserAreaCity(resultSet.getString(9));
                userVO.setUserDescription(resultSet.getString(10));
            }

            preparedStatement.close();
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return userVO;
    }

    public UserVO addUser(String userPhoneNumber, String userPassword, String userNickname)
            throws DatabaseException{
        UserVO userVO = null;

        try {
            String SQL1 = "insert into " + USER_TABLE_NAME + " " +
                          "(user_phone_number, user_password, user_nickname) " +
                          "values (?, ?, ?);";

            PreparedStatement preparedStatement1 = connection.prepareStatement(SQL1, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement1.setString(1, userPhoneNumber);
            preparedStatement1.setString(2, userPassword);
            preparedStatement1.setString(3, userNickname);

            if (preparedStatement1.executeUpdate() < 1) {
                throw new DatabaseException();
            }

            preparedStatement1.close();

            String SQL2 = "select * " +
                          "from " + USER_TABLE_NAME + " " +
                          "where user_id = last_insert_id();";

            PreparedStatement preparedStatement2 = connection.prepareStatement(SQL2, PreparedStatement.RETURN_GENERATED_KEYS);
            ResultSet resultSet =  preparedStatement2.executeQuery();
            if (resultSet.next()) {
                userVO = new UserVO();
                userVO.setUserID(resultSet.getLong(1));
                userVO.setUserNickname(resultSet.getString(2));
                userVO.setUserPhoneNumber(resultSet.getString(3));
                userVO.setUserPassword(resultSet.getString(4));
                userVO.setUserHeadShowURL(resultSet.getString(5));
                userVO.setUserBirthday(resultSet.getDate(6));
                userVO.setUserSex(resultSet.getLong(7));
                userVO.setUserAreaProvince(resultSet.getString(8));
                userVO.setUserAreaCity(resultSet.getString(9));
                userVO.setUserDescription(resultSet.getString(10));
            }
        }
        catch (SQLException SQLexception) {
            throw new DatabaseException();
        }

        return userVO;
    }

    public void updateUserNickname(long userID, String userNickname) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_nickname", userNickname);
    }

    public void updateUserPhoneNumber(long userID, String userPhoneNumber) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_phone_number", userPhoneNumber);
    }

    public void updateUserPassword(long userID, String userPhoneNumber) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_password", userPhoneNumber);
    }

    public void updateUserHeadShowURL(long userID, String userHeadShowURL) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_head_show_url", userHeadShowURL);
    }

    public void updateUserBirthday(long userID, Date userBirthday) throws DatabaseException {
        updateUserTableDateColumn(userID, "user_birthday", userBirthday);
    }

    public void updateUserSex(long userID, long userSex) throws DatabaseException {
        updateUserTableLongColumn(userID, "user_sex", userSex);
    }

    public void updateUserAreaProvince(long userID, String userAreaProvince) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_area_province", userAreaProvince);
    }

    public void updateUserAreaCity(long userID, String userAreaCity) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_area_city", userAreaCity);
    }

    public void updateUserDescription(long userID, String userDescription) throws DatabaseException {
        updateUserTableStringColumn(userID, "user_description", userDescription);
    }

    private void updateUserTableStringColumn(long userID, String columnName, String value)
            throws DatabaseException {
        try {
            String SQL = "update " + USER_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, value);
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

    private void updateUserTableLongColumn(long userID, String columnName, Long value)
            throws DatabaseException {
        try {
            String SQL = "update " + USER_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setLong(1, value);
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

    private void updateUserTableDateColumn(long userID, String columnName, Date value)
            throws DatabaseException {
        try {
            String SQL = "update " + USER_TABLE_NAME + " " +
                         "set " + columnName + " = ? " +
                         "where user_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setDate(1, value);
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
}
