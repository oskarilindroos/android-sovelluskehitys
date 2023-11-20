package com.example.gamereleasehelper.services;

import android.util.Log;

import com.example.gamereleasehelper.BuildConfig;
import com.example.gamereleasehelper.interfaces.IGDBApi;
import com.example.gamereleasehelper.interfaces.TwitchApi;
import com.example.gamereleasehelper.models.AccessToken;
import com.example.gamereleasehelper.models.Game;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class for handling requests to IGDB API
 */
public class IGDBService {
    private IGDBApi service;
    private AuthService authService;
    private List<Game> searchResults = Collections.emptyList();

    public interface AuthCallback {
        void onResponseReceived(List<Game> games);
        void onError(String errorMessage);
    }

    public IGDBService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.IGDB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(IGDBApi.class);

        authService = AuthService.getInstance(); // Get the singleton instance of AuthService
    }

    public List<Game> getSearchResults() {
        return searchResults;
    }

    public void searchGames(String query, final AuthCallback callback) {
        AccessToken accessToken = authService.getAccessToken();

        String requestText = "fields name, id, cover.image_id, first_release_date, summary, screenshots.image_id, platforms.abbreviation;\n" +
                "where name ~ *\"" + query + "\"* &\n" +
                "themes.slug != \"erotic\" &\n" +
                "version_parent = null &\n" +
                "first_release_date > " + Instant.now().getEpochSecond() + ";\n" +
                "sort first_release_date asc;\n" +
                "limit 200;";

        // Create a request body from the request text (fixes issue with Retrofit sending double quotes)
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), requestText);

        Call<List<Game>> call = service.searchGames("Bearer " + accessToken.getAccessToken(), BuildConfig.CLIENT_ID, requestBody);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful()) {
                    searchResults = response.body();
                    callback.onResponseReceived(searchResults);
                } else {
                    callback.onError("API returned an error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

}
