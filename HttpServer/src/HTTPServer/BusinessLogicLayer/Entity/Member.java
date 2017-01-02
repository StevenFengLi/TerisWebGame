package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

public class Member {
    // 更新群成员备注
    public void updateMemberRemark(String memberRemark) throws OutOfLengthException, DataAccessLayerException {
        if (memberRemark.length() > 30)
            throw new OutOfLengthException();

        MemberDAO memberDAO = DAOFactory.getMemberInstance();

        try {
            memberDAO.updateMemberRemark(this.groupID, this.userID, memberRemark);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.memberRemark = memberRemark;
    }

    // 更新群成员角色
    public void updateMemberRole(MemberRole memberRole) throws DataAccessLayerException {
        MemberDAO memberDAO = DAOFactory.getMemberInstance();

        try {
            switch (memberRole) {
                case MEMBER_ROLE_HOST:
                    memberDAO.updateMemberRole(this.groupID, this.userID, MemberVO.DATABASE_MEMBER_ROLE_HOST);
                    break;
                case MEMBER_ROLE_COMMON:
                    memberDAO.updateMemberRole(this.groupID, this.userID, MemberVO.DATABASE_MEMBER_ROLE_COMMON);
                    break;
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.memberRole = memberRole;
    }

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

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }

    public enum MemberRole {
        MEMBER_ROLE_HOST, MEMBER_ROLE_COMMON
    }

    private long groupID;
    private long userID;
    private String memberRemark;
    private MemberRole memberRole;
}
