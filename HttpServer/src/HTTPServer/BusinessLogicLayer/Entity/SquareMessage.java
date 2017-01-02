package HTTPServer.BusinessLogicLayer.Entity;

import HTTPServer.BusinessLogicLayer.Exception.*;

import HTTPServer.DataAccessLayer.Entity.DAOFactory.*;
import HTTPServer.DataAccessLayer.Entity.DAO.*;
import HTTPServer.DataAccessLayer.Entity.DAOVO.*;

import HTTPServer.DataAccessLayer.Exception.DatabaseException;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Timestamp;

public class SquareMessage {
    // 获取该广场消息的评论列表
    public List<Comment> getCommentList() throws DataAccessLayerException {
        List<Comment> commentList = new ArrayList<>();

        try {
            CommentDAO commentDAO = DAOFactory.getCommentInstance();

            List<CommentVO> commentVOList = commentDAO.getCommentListBySquareMessageID(this.squareMessageID);
            for (CommentVO commentVO : commentVOList) {
                commentList.add(changeCommentVOToComment(commentVO));
            }
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return commentList;
    }

    // 添加一条评论
    public Comment addComment(long senderUserID, String text, Date time) throws DataAccessLayerException {
        Comment comment = null;

        try {
            CommentDAO commentDAO = DAOFactory.getCommentInstance();

            CommentVO commentVO = commentDAO.addComment(this.squareMessageID, senderUserID, text, new Timestamp(time.getTime()));
            comment = changeCommentVOToComment(commentVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return comment;
    }

    // 添加一条回复
    public Comment addReply(long senderUserID, long commentRepliedID, String text, Date time)throws CommentIDNotExistedException, CommentRepliedIDNotIncludedException, DataAccessLayerException {
        Comment comment = null;

        try {
            CommentDAO commentDAO = DAOFactory.getCommentInstance();

            if (!commentDAO.isCommentExisted(commentRepliedID)) {
                throw new CommentIDNotExistedException();
            }

            CommentVO commentRepliedVO =  commentDAO.getCommentByCommentID(commentRepliedID);

            if (commentRepliedVO.getSquareMessageID() != this.squareMessageID) {
                throw new CommentRepliedIDNotIncludedException();
            }

            CommentVO commentVO = commentDAO.addReply(this.squareMessageID, commentRepliedID, senderUserID, text, new Timestamp(time.getTime()));
            comment = changeCommentVOToComment(commentVO);
        }
        catch (DatabaseException databaseException) {
            throw new DataAccessLayerException();
        }

        return comment;
    }

    public long getSquareMessageID() {
        return squareMessageID;
    }

    public void setSquareMessageID(long squareMessageID) {
        this.squareMessageID = squareMessageID;
    }

    public long getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(long senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getSquareMessageText() {
        return squareMessageText;
    }

    public void setSquareMessageText(String squareMessageText) {
        this.squareMessageText = squareMessageText;
    }

    public Date getSquareMessageTime() {
        return squareMessageTime;
    }

    public void setSquareMessageTime(Date squareMessageTime) {
        this.squareMessageTime = squareMessageTime;
    }

    private Comment changeCommentVOToComment(CommentVO commentVO) {
        Comment comment = new Comment();

        comment.setCommentID(commentVO.getCommentID());
        comment.setSquareMessageID(commentVO.getSquareMessageID());
        comment.setSenderID(commentVO.getSenderUserID());//modified(added) by lf
        if (commentVO.getCommentRepliedID() == CommentVO.DATABASE_COMMENT_NO_COMMENT_REPLIED_ID)
            comment.setCommentRepliedID(Comment.DATABASE_COMMENT_NO_COMMENT_REPLIED_ID);
        else
            comment.setCommentRepliedID(commentVO.getCommentRepliedID());
        comment.setCommentText(commentVO.getCommentText());
        comment.setCommentTime(commentVO.getCommentTime());

        return comment;
    }

    private long squareMessageID;
    private long senderUserID;
    private String squareMessageText;
    private Date squareMessageTime;
}
