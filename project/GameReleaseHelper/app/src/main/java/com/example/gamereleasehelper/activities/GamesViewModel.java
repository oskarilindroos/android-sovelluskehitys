package com.example.gamereleasehelper.activities;

import androidx.lifecycle.ViewModel;

import com.example.gamereleasehelper.models.Game;

import java.util.List;

public class GamesViewModel extends ViewModel {
    private List<Game> games;

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
