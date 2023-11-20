package com.example.gamereleasehelper.interfaces;

import com.example.gamereleasehelper.models.Game;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IGDBApi {
    @Headers("Content-Type: text/plain")
    @POST("games")
    public Call<List<Game>> searchGames(
            @Header("Authorization") String authorizationHeader,
            @Header("Client-ID") String clientIdHeader,
            @Body RequestBody body
    );
}
