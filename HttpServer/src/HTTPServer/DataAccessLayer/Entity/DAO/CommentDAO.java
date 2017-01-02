package HTTPServer.DataAccessLayer.Entity.DAO;

import HTTPServer.DataAccessLayer.Entity.DAOVO.CommentVO;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.sql.Timestamp;

public interface CommentDAO {
    boolean isCommentExisted(long commentID) throws DatabaseException;

    CommentVO getCommentByCommentID(long commentID) throws DatabaseException;

    List<CommentVO> getCommentListBySquareMessageID(long squareMessageID) throws DatabaseException;

    CommentVO addComment(long squareMessageID, long senderUserID,
                         String commentText, Timestamp commentTime) throws DatabaseException;

    CommentVO addReply(long squareMessageID, long commentRepliedID, long senderUserID,
                       String commentText, Timestamp commentTime) throws DatabaseException;

    void deleteCommentByCommentID(long commentID) throws DatabaseException;
}
