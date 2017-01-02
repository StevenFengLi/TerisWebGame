package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.MemberVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;

public interface MemberDAO {
    boolean isMemberExistedInGroup(long groupID, long userID) throws DatabaseException;

    List<MemberVO> getMemberListByGroupID(long groupID) throws DatabaseException;

    List<Long> getGroupIDListByUserID(long userID) throws DatabaseException;

    MemberVO getMemberByGroupIDAndUserID(long groupID, long userID) throws DatabaseException;

    void addMemberByGroupIDAndUserID(long groupID, long userID, long memberRole) throws DatabaseException;

    void updateMemberRemark(long groupID, long userID, String memberRemark) throws DatabaseException;

    void updateMemberRole(long groupID, long userID, long memberRole) throws DatabaseException;

    void deleteMemberByGroupIDAndUserID(long groupID, long userID) throws DatabaseException;
}
