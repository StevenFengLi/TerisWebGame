package HTTPServer.BusinessLogicLayer.Entity;

import java.util.Date;

public class Friend {
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

    public FriendUserSex getUserSex() {
        return userSex;
    }

    public void setUserSex(FriendUserSex userSex) {
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

    public enum FriendUserSex {
        FRIEND_USER_SEX_FEMALE, FRIEND_USER_SEX_MALE, FRIEND_USER_SEX_UNKNOWN
    }

    private long userID;
    private String userNickname;
    private String userPhoneNumber;
    private String userHeadShowURL;
    private Date userBirthday;
    private FriendUserSex userSex;
    private String userAreaProvince;
    private String userAreaCity;
    private String userDescription;
}
