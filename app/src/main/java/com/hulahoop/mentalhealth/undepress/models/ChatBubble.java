package com.hulahoop.mentalhealth.undepress.models;

public class ChatBubble {
    private String messageContent;
    private boolean signedInUserMessage;

    public ChatBubble(String messageContent, boolean signedInUserMessage) {
        this.messageContent = messageContent;
        this.signedInUserMessage = signedInUserMessage;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public boolean isSignedInUserMessage() {
        return signedInUserMessage;
    }
}
