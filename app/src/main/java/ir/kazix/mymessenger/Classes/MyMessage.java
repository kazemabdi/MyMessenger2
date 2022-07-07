package ir.kazix.mymessenger.Classes;

import androidx.annotation.Nullable;

public class MyMessage {

    private String messageText;

    @Nullable
    String messageSrcID;

    @Nullable
    private String messageDstID;

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageSrcID(@Nullable String messageSrcID) {
        this.messageSrcID = messageSrcID;
    }

    @Nullable
    public String getMessageSrcID() {
        return messageSrcID;
    }

    public void setMessageDstID(@Nullable String messageDstID) {
        this.messageDstID = messageDstID;
    }

    @Nullable
    public String getMessageDstID() {
        return messageDstID;
    }
}