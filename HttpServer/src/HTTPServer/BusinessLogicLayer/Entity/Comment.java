package HTTPServer.BusinessLogicLayer.Entity;

import java.util.Date;

public class Comment {
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

    public long getSenderID() {
        return this.senderID;
    }

    public void setSenderID(long senderID) {
        this.senderID = senderID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    private long commentID;
    private long squareMessageID;
    private long commentRepliedID;
    private long senderID;
    private String commentText;
    private Date commentTime;
}
