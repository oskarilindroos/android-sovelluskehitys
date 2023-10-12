package com.example.week04_ex01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText gameSearchEditText;
    ListView gamesListView;
    String IGDB_ACCESS_TOKEN = "";
    List<String> gamesList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSearchEditText = (EditText) findViewById(R.id.gameSearchText);
        gamesListView = (ListView) findViewById(R.id.gamesListView);

        getApiAccessToken();

        // Search for games when the user presses the enter key
        gameSearchEditText.setOnEditorActionListener((v, actionId, event) -> {
            gamesList.clear();
            String searchQuery = gameSearchEditText.getText().toString();
            searchGames(searchQuery);
            return false;
        });
    }

    public void openWebpage(View view) {
        String url = "https://www.igdb.com/";
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    private void getApiAccessToken() {
        // Build request URL
        Uri.Builder builder = Uri.parse("https://id.twitch.tv/oauth2/token").buildUpon();
        builder.appendQueryParameter("client_id", BuildConfig.CLIENT_ID);
        builder.appendQueryParameter("client_secret", BuildConfig.CLIENT_SECRET);
        builder.appendQueryParameter("grant_type", "client_credentials");
        String url = builder.build().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            // Handle response
            IGDB_ACCESS_TOKEN = parseAccessToken(response);
        }, error -> {
            // Handle error
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        });

        Volley.newRequestQueue(this).add(request);
    }

    private String parseAccessToken(String response) {
        try {
            // Parse response
            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getString("access_token");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateGamesListUI(List<String> gamesList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gamesList);
        gamesListView.setAdapter(adapter);
    }

    private void searchGames(String query) {
        StringRequest request = new StringRequest(Request.Method.POST, BuildConfig.IGDB_API_BASE_URL + "games", response -> {
            // Handle response
            try {
                JSONArray jsonResponse = new JSONArray(response);

                // for each game in the response, add the game's name to the games list
                for (int i = 0; i < jsonResponse.length(); i++) {
                    JSONObject game = jsonResponse.getJSONObject(i);
                    gamesList.add(game.getString("name"));
                }
                updateGamesListUI(gamesList);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            // Handle error
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() {
                // Set headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Client-ID", BuildConfig.CLIENT_ID);
                headers.put("Authorization", "Bearer " + IGDB_ACCESS_TOKEN);
                return headers;
            }

            @Override
            public byte[] getBody() {
                // Set request body
                String body = "fields name; search \"" + query + "\"; limit 10;";
                return body.getBytes();
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

}