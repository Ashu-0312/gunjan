package app.gunjan.twilio;

import org.jetbrains.annotations.Nullable;

public interface TokenResponseListener {
    void receivedTokenResponse(boolean success, @Nullable Exception exception);
}
