package HTTPServer.DataAccessLayer.Entity.DAOVO;

public class MemberVO {
    public static final long DATABASE_MEMBER_ROLE_HOST = 0;
    public static final long DATABASE_MEMBER_ROLE_COMMON = 1;

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getMemberRemark() {
        return memberRemark;
    }

    public void setMemberRemark(String memberRemark) {
        this.memberRemark = memberRemark;
    }

    public long getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(long memberRole) {
        this.memberRole = memberRole;
    }

    private long groupID;
    private long userID;
    private String memberRemark;
    private long memberRole;
}
