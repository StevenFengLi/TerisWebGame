package HTTPServer.DataAccessLayer.Entity.DAOVO;

import java.sql.Date;

public class UserVO {
    public static final long DATABASE_USER_SEX_FEMALE = 0;
    public static final long DATABASE_USER_SEX_MALE = 1;
    public static final long DATABASE_USER_SEX_UNKNOWN = 2;

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserHeadShowURL() {
        return userHeadShowURL;
    }

    public void setUserHeadShowURL(String userHeadShowURL) {
        this.userHeadShowURL = userHeadShowURL;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public long getUserSex() {
        return userSex;
    }

    public void setUserSex(long userSex) {
        this.userSex = userSex;
    }

    public String getUserAreaProvince() {
        return userAreaProvince;
    }

    public void setUserAreaProvince(String userAreaProvince) {
        this.userAreaProvince = userAreaProvince;
    }

    public String getUserAreaCity() {
        return userAreaCity;
    }

    public void setUserAreaCity(String userAreaCity) {
        this.userAreaCity = userAreaCity;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    private long userID;
    private String userNickname;
    private String userPhoneNumber;
    private String userPassword;
    private String userHeadShowURL;
    private Date userBirthday;
    private long userSex;
    private String userAreaProvince;
    private String userAreaCity;
    private String userDescription;
}
