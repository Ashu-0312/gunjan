package app.gunjan.twilio;

import org.jetbrains.annotations.Nullable;

public interface AccessTokenListener {
    void receivedAccessToken(@Nullable String token, @Nullable Exception exception);
}
