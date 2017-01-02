package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.GroupMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.sql.Timestamp;

public interface GroupMessageDAO {
    boolean isGroupMessageExisted(long groupMessageID) throws DatabaseException;

    GroupMessageVO getGroupMessageByGroupMessageID(long groupMessageID) throws DatabaseException;

    List<GroupMessageVO> getGroupMessageListByGroupID(long groupID) throws DatabaseException;

    GroupMessageVO addGroupMessage(long groupID, long senderUserID, String groupMessageText,
                                   Timestamp groupMessageTime) throws DatabaseException;

    void deleteGroupMessageByGroupMessageID(long groupMessageID) throws DatabaseException;
}
