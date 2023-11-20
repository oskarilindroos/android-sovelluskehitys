package com.example.gamereleasehelper.models;
import com.google.gson.annotations.SerializedName;

public class AccessToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private long expiresIn;

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String value) { this.accessToken = value; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String value) { this.tokenType = value; }

    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long value) { this.expiresIn = value; }
}
