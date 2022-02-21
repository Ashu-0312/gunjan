package app.gunjan.twilio;

import com.twilio.chat.ChatClient;

import org.jetbrains.annotations.Nullable;

public interface ClientCreated {
    void clientCreated(ChatClient chatClient, boolean success, @Nullable Exception exception);
}
