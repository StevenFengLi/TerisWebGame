package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.PersonalMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.sql.Timestamp;

public interface PersonalMessageDAO {
    boolean isPersonalMessageExisted(long personalMessageID) throws DatabaseException;

    PersonalMessageVO getPersonalMessageByPersonalMessageID(long personalMessageID) throws DatabaseException;

    List<PersonalMessageVO> getPersonalMessageListByTwoUserIDs(long userID1, long userID2) throws DatabaseException;

    PersonalMessageVO addPersonalMessage(long senderUserID, long receiverUserID, String personalMessageText,
                                         Timestamp personalMessageTime) throws DatabaseException;

    void deletePersonalMessageByPersonalMessageID(long personalMessageID) throws DatabaseException;
}
