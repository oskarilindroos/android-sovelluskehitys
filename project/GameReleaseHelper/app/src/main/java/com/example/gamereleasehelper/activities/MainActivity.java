package com.example.gamereleasehelper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.gamereleasehelper.adapters.GameAdapter;
import com.example.gamereleasehelper.models.AccessToken;
import com.example.gamereleasehelper.models.Game;
import com.example.gamereleasehelper.services.AuthService;
import com.example.gamereleasehelper.R;
import com.example.gamereleasehelper.services.IGDBService;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView gamesRecyclerView;
    ProgressBar progressBar;
    TextView errorTextView;

    private AuthService authService;
    private IGDBService igdbService;

    private GameAdapter gameAdapter;
    private GamesViewModel viewModel; // For persisting data across configuration changes (e.g. screen rotation)
    final Handler handler = new Handler(); // Handler for delaying search

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        gamesRecyclerView = findViewById(R.id.gamesRecyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorTextView);

        authService = AuthService.getInstance(); // Create the singleton instance of AuthService
        igdbService = new IGDBService();

        gameAdapter = new GameAdapter(igdbService.getSearchResults());
        gamesRecyclerView.setAdapter(gameAdapter);
        gamesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(GamesViewModel.class);

        // If there is data in the ViewModel, update the UI
        List<Game> storedGames = viewModel.getGames();
        if (storedGames != null && !storedGames.isEmpty()) {
            gameAdapter.setGames(storedGames);
            gameAdapter.notifyDataSetChanged();
        }

        // Request access token from Twitch for IGDB API
        authService.requestAccessToken(new AuthService.AuthCallback() {
            @Override
            public void onTokenReceived(AccessToken token) {
                // Search for games when the user presses the enter key
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        errorTextView.setVisibility(TextView.INVISIBLE);
                        progressBar.setVisibility(ProgressBar.VISIBLE);

                        searchGames(query);

                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        // Delay search until user has stopped typing for 500ms
                        handler.removeCallbacksAndMessages(null);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (!query.isEmpty()) {
                                    errorTextView.setVisibility(TextView.INVISIBLE);
                                    progressBar.setVisibility(ProgressBar.VISIBLE);

                                    searchGames(query);
                                }
                            }
                        }, 500);

                        return true;
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                String errorText = String.format(getString(R.string.errorText), errorMessage);
                errorTextView.setText(errorText);
            }
        });
    }

    private void searchGames(String query) {
        igdbService.searchGames(query, new IGDBService.AuthCallback() {
            @Override
            public void onResponseReceived(List<Game> games) {
                viewModel.setGames(games);
                gameAdapter.setGames(games);
                gameAdapter.notifyDataSetChanged();
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                if (games.size() == 0) {
                    String noResultsText = String.format(getString(R.string.noSearchResults), query);
                    errorTextView.setText(noResultsText);
                    errorTextView.setVisibility(TextView.VISIBLE);
                }
            }

            @Override
            public void onError(String errorMessage) {
                String errorText = String.format(getString(R.string.errorText), errorMessage);
                errorTextView.setText(errorText);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
        });
    }

}