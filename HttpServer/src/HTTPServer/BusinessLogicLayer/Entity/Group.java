package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class Group {
    // 获取成员列表
    public List<Member> getMemberList() throws DataAccessLayerException {
        List<Member> memberList = new ArrayList<>();

        try {
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            List<MemberVO> memberVOList = memberDAO.getMemberListByGroupID(this.groupID);
            for (MemberVO memberVO : memberVOList) {
                memberList.add(changeMemberVOToMember(memberVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return memberList;
    }

    // 添加一个成员
    public void addMemberByMemberUserID(long memberUserID, Member.MemberRole memberRole)
            throws UserIDNotExistedException, MembershipExistedException, DataAccessLayerException {
        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!userDAO.isUserIDExisted(memberUserID)) {
                throw new UserIDNotExistedException();
            }

            if (memberDAO.isMemberExistedInGroup(this.groupID, memberUserID)) {
                throw new MembershipExistedException();
            }

            switch (memberRole) {
                case MEMBER_ROLE_HOST:
                    memberDAO.addMemberByGroupIDAndUserID(this.groupID, memberUserID, MemberVO.DATABASE_MEMBER_ROLE_HOST);
                    break;
                case MEMBER_ROLE_COMMON:
                    memberDAO.addMemberByGroupIDAndUserID(this.groupID, memberUserID, MemberVO.DATABASE_MEMBER_ROLE_COMMON);
                    break;
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 根据成员的用户标识符删除群成员
    public void deleteMemberByMemberUserID(long memberUserID)
            throws UserIDNotExistedException, MembershipNotExistedException, DataAccessLayerException {
        try {
            UserDAO userDAO = DAOFactory.getUserDAOInstance();
            MemberDAO memberDAO = DAOFactory.getMemberInstance();

            if (!userDAO.isUserIDExisted(memberUserID)) {
                throw new UserIDNotExistedException();
            }

            if (!memberDAO.isMemberExistedInGroup(this.groupID, memberUserID)) {
                throw new MembershipNotExistedException();
            }

            memberDAO.deleteMemberByGroupIDAndUserID(groupID, memberUserID);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }
    }

    // 更新群名称
    public void updateGroupName(String groupName) throws OutOfLengthException, DataAccessLayerException {
        GroupDAO groupDAO = DAOFactory.getGroupInstance();

        if (groupName.length() > 30)
            throw new OutOfLengthException();

        try {
            groupDAO.updateGroupName(this.groupID, groupName);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.groupName = groupName;
    }

    // 更新群公告
    public void updateGroupAnnouncement(String groupAnnouncement)
            throws OutOfLengthException, DataAccessLayerException {
        GroupDAO groupDAO = DAOFactory.getGroupInstance();

        if (groupName.length() > 100)
            throw new OutOfLengthException();

        try {
            groupDAO.updateGroupAnnouncement(this.groupID, groupAnnouncement);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        this.groupAnnouncement = groupAnnouncement;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAnnouncement() {
        return groupAnnouncement;
    }

    public void setGroupAnnouncement(String groupAnnouncement) {
        this.groupAnnouncement = groupAnnouncement;
    }

    private Member changeMemberVOToMember(MemberVO memberVO) {
        Member member = new Member();

        member.setGroupID(memberVO.getGroupID());
        member.setUserID(memberVO.getUserID());
        member.setMemberRemark(memberVO.getMemberRemark());
        if (memberVO.getMemberRole() == MemberVO.DATABASE_MEMBER_ROLE_HOST)
            member.setMemberRole(Member.MemberRole.MEMBER_ROLE_HOST);
        else if (memberVO.getMemberRole() == MemberVO.DATABASE_MEMBER_ROLE_COMMON)
            member.setMemberRole(Member.MemberRole.MEMBER_ROLE_COMMON);

        return member;
    }

    private long groupID;
    private String groupName;
    private String groupAnnouncement;
}
