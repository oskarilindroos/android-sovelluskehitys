package com.example.gamereleasehelper.interfaces;

import com.example.gamereleasehelper.models.AccessToken;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TwitchApi {
    @POST("oauth2/token" )
    public Call<AccessToken> getAccessToken(@Query("client_id") String client_id, @Query("client_secret") String client_secret, @Query("grant_type") String grant_type);

}
