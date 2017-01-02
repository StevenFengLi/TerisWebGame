package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.GroupVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;

public interface GroupDAO {
    boolean isGroupIDExisted(long groupID) throws DatabaseException;

    GroupVO getGroupByGroupID(long groupID) throws DatabaseException;

    List<GroupVO> getGroupListByGroupName(String groupName) throws DatabaseException;

    GroupVO addGroup(String groupName) throws DatabaseException;

    void updateGroupName(long groupID, String groupName) throws DatabaseException;

    void updateGroupAnnouncement(long groupID, String groupAnnouncement) throws DatabaseException;

    void deleteGroupByGroupID(long groupID) throws DatabaseException;
}
