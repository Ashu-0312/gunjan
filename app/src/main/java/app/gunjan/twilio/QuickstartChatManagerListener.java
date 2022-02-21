package app.gunjan.twilio;

public interface QuickstartChatManagerListener {
    void receivedNewMessage();

    void messageSentCallback();
}
