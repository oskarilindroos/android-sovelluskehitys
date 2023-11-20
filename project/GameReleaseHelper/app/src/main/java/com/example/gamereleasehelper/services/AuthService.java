package com.example.gamereleasehelper.services;

import android.util.Log;

import com.example.gamereleasehelper.models.AccessToken;
import com.example.gamereleasehelper.BuildConfig;
import com.example.gamereleasehelper.interfaces.TwitchApi;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton class for getting access token from Twitch for IGDB API
 */
public class AuthService {
    private static AuthService instance = null;
    private TwitchApi service;
    private AccessToken accessToken;

    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public interface AuthCallback {
        void onTokenReceived(AccessToken token);
        void onError(String errorMessage);
    }

    public AuthService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.IGDB_AUTH_BASE_URL)
                .addConverterFactory( GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(TwitchApi.class);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void requestAccessToken(final AuthCallback callback) {
        Call<AccessToken> call = service.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, "client_credentials");

        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if (response.isSuccessful()) {
                    accessToken = response.body();
                    callback.onTokenReceived(accessToken);
                } else {
                    Log.e("AccessToken", response.message());
                }
            }
            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                System.out.println(t);
                callback.onError(t.getMessage());
                Log.e("AccessToken", t.getMessage());
            }
        });
    }
}
