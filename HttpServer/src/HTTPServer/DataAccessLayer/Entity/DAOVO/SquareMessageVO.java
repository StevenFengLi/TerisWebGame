package HTTPServer.DataAccessLayer.Entity.DAOVO;

import java.sql.Timestamp;

public class SquareMessageVO {
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

    public Timestamp getSquareMessageTime() {
        return squareMessageTime;
    }

    public void setSquareMessageTime(Timestamp squareMessageTime) {
        this.squareMessageTime = squareMessageTime;
    }

    private long squareMessageID;
    private long senderUserID;
    private String squareMessageText;
    private Timestamp squareMessageTime;
}
