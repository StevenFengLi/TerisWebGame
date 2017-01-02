package HTTPServer.BusinessLogicLayer.Entity;

import java.util.Date;

public class PersonalMessage {
    public long getPersonalMessageID() {
        return personalMessageID;
    }

    public void setPersonalMessageID(long personalMessageID) {
        this.personalMessageID = personalMessageID;
    }

    public long getSenderUserID() {
        return senderUserID;
    }

    public void setSenderUserID(long senderUserID) {
        this.senderUserID = senderUserID;
    }

    public long getReceiverUserID() {
        return receiverUserID;
    }

    public void setReceiverUserID(long receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public String getPersonalMessageText() {
        return personalMessageText;
    }

    public void setPersonalMessageText(String personalMessageText) {
        this.personalMessageText = personalMessageText;
    }

    public Date getPersonalMessageTime() {
        return personalMessageTime;
    }

    public void setPersonalMessageTime(Date personalMessageTime) {
        this.personalMessageTime = personalMessageTime;
    }

    private long personalMessageID;
    private long senderUserID;
    private long receiverUserID;
    private String personalMessageText;
    private Date personalMessageTime;
}
