package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.util.ArrayList;

public class GroupList {
    // 根据群名称查找群列表
    public List<Group> getGroupListByGroupName(String groupName) throws DataAccessLayerException {
        List<Group> groupList = new ArrayList<>();

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();

            List<GroupVO> groupVOList = groupDAO.getGroupListByGroupName(groupName);
            for (GroupVO groupVO : groupVOList) {
                groupList.add(changeGroupVOToGroup(groupVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return groupList;
    }

    // 根据群标识符得到群
    public Group getGroupByGroupID(long groupID)
            throws GroupIDNotExistedException, DataAccessLayerException {
        Group group;

        try {
            GroupDAO groupDAO = DAOFactory.getGroupInstance();

            if (!groupDAO.isGroupIDExisted(groupID)) {
                throw new GroupIDNotExistedException();
            }

            GroupVO groupVO =  groupDAO.getGroupByGroupID(groupID);
            group = changeGroupVOToGroup(groupVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return group;
    }

    private Group changeGroupVOToGroup(GroupVO groupVO) {
        Group group = new Group();

        group.setGroupID(groupVO.getGroupID());
        group.setGroupName(groupVO.getGroupName());
        group.setGroupAnnouncement(groupVO.getGroupAnnouncement());

        return group;
    }
}
