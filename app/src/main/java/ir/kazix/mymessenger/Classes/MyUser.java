package ir.kazix.mymessenger.Classes;

import androidx.annotation.Nullable;

public class MyUser {

    private String sessionId;

    private String email;

    @Nullable
    private String lastName;

    @Nullable
    private String firstName;

    @Nullable
    private String password;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
