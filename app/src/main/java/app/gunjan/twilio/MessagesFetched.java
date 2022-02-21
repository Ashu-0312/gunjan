package app.gunjan.twilio;

import org.jetbrains.annotations.Nullable;

public interface MessagesFetched {
    void messagesFetched(boolean success, @Nullable Exception exception);
}
