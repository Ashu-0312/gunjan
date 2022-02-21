package app.gunjan.twilio;

import org.jetbrains.annotations.Nullable;

public interface ChannelCreated {
    void channelCreated(boolean success, @Nullable Exception exception);
}
