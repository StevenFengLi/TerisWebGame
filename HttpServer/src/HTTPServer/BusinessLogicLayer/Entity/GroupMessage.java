package HTTPServer.BusinessLogicLayer.Entity;

import java.util.Date;

public class GroupMessage {
    public long getGroupMessageID() {
        return groupMessageID;
    }

    public void setGroupMessageID(long groupMessageID) {
        this.groupMessageID = groupMessageID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
    }

    public long getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(long senderUserID) {
        this.senderUserID = senderUserID;
    }

    public String getGroupMessageText() {
        return groupMessageText;
    }

    public void setGroupMessageText(String groupMessageText) {
        this.groupMessageText = groupMessageText;
    }

    public Date getGroupMessageTime() {
        return groupMessageTime;
    }

    public void setGroupMessageTime(Date groupMessageTime) {
        this.groupMessageTime = groupMessageTime;
    }

    private long groupMessageID;
    private long groupID;
    private long senderUserID;
    private String groupMessageText;
    private Date groupMessageTime;
}
