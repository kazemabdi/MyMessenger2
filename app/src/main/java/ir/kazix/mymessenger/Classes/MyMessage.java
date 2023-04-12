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

    public void setMessageSrcID(@Nullable String messageSrcID) {
        this.messageSrcID = messageSrcID;
    }

    public void setMessageDstID(@Nullable String messageDstID) {
        this.messageDstID = messageDstID;
    }

    public String getMessageText() {
        return messageText;
    }

    @Nullable
    public String getMessageSrcID() {
        return messageSrcID;
    }

    @Nullable
    public String getMessageDstID() {
        return messageDstID;
    }
}