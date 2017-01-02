package HTTPServer.DataAccessLayer.Entity.DAOVO;

import java.sql.Timestamp;

public class CommentVO {
    public static final long DATABASE_COMMENT_NO_COMMENT_REPLIED_ID = 0;

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public long getSquareMessageID() {
        return squareMessageID;
    }

    public void setSquareMessageID(long squareMessageID) {
        this.squareMessageID = squareMessageID;
    }

    public long getCommentRepliedID() {
        return commentRepliedID;
    }

    public void setCommentRepliedID(long commentRepliedID) {
        this.commentRepliedID = commentRepliedID;
    }

    public long getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(long senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    private long commentID;
    private long squareMessageID;
    private long commentRepliedID;
    private long senderUserID;
    private String commentText;
    private Timestamp commentTime;
}
