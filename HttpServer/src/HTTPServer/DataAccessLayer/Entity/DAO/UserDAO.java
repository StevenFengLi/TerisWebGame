package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.UserVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.sql.Date;

public interface UserDAO {
    boolean isUserIDExisted(long userID) throws DatabaseException;

    boolean isUserPhoneNumberExisted(String userPhoneNumber) throws DatabaseException;

    UserVO getUserByUserID(long userID) throws DatabaseException;

    UserVO getUserByUserPhoneNumber(String userPhoneNumber) throws DatabaseException;

    UserVO addUser(String userPhoneNumber, String userPassword, String userNickname) throws DatabaseException;

    void updateUserNickname(long userID, String userNickname) throws DatabaseException;

    void updateUserPhoneNumber(long userID, String userPhoneNumber) throws DatabaseException;

    void updateUserPassword(long userID, String userPhoneNumber) throws DatabaseException;

    void updateUserHeadShowURL(long userID, String userHeadShowURL) throws DatabaseException;

    void updateUserBirthday(long userID, Date userBirthday) throws DatabaseException;

    void updateUserSex(long userID, long userSex) throws DatabaseException;

    void updateUserAreaProvince(long userID, String userAreaProvince) throws DatabaseException;

    void updateUserAreaCity(long userID, String userAreaCity) throws DatabaseException;

    void updateUserDescription(long userID, String userDescription) throws DatabaseException;
}
