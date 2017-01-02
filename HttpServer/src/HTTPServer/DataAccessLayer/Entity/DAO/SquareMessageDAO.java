package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.SquareMessageVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.sql.Timestamp;

public interface SquareMessageDAO {
    boolean isSquareMessageExisted(long squareMessageID) throws DatabaseException;

    SquareMessageVO getSquareMessageBySquareMessageID(long squareMessageID) throws DatabaseException;

    List<SquareMessageVO> getSquareMessageListBySenderID(long senderID) throws DatabaseException;

    List<SquareMessageVO> getTenSquareMessagesBeforeTime(Timestamp timestamp) throws DatabaseException;

    List<SquareMessageVO> getSquareMessageListAfterTime(Timestamp timestamp) throws DatabaseException;

    SquareMessageVO addSquareMessage(long senderUserID, String squareMessageText,
                                     Timestamp squareMessageTime) throws DatabaseException;

    void deleteSquareMessageBySquareMessageID(long squareMessageID) throws DatabaseException;
}
