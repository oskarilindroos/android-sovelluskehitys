package com.example.gamereleasehelper.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamereleasehelper.R;
import com.example.gamereleasehelper.models.Game;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private List<Game> games = Collections.emptyList();

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView gameNameTextView;
        private TextView gameReleaseDateTextView;
        private ImageButton addToCalendarButton;
        ImageView gameCoverImageView;

        Context context;

        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            gameNameTextView = view.findViewById(R.id.gameNameTextView);
            gameReleaseDateTextView = view.findViewById(R.id.gameReleaseDateTextView);
            gameCoverImageView = view.findViewById(R.id.gameCoverImageView);
            addToCalendarButton = view.findViewById(R.id.addToCalendarButton);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.gameslist_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Game game = games.get(position);

            viewHolder.gameNameTextView.setText(game.getName());

            // Convert UNIX timestamp to date string
            Instant instant = Instant.ofEpochSecond(game.getFirstReleaseDate());
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            String formattedDate = localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            // Calculate days until release
            long daysUntilRelease = ChronoUnit.DAYS.between(LocalDateTime.now(), localDateTime);
            String daysUntilString;

            if (daysUntilRelease == 0) {
                daysUntilString = "today";
            } else if (daysUntilRelease == 1) {
                daysUntilString = daysUntilRelease + " day";
            } else {
                daysUntilString = daysUntilRelease + " days";
            }
            viewHolder.gameReleaseDateTextView.setText(formattedDate + " (" + daysUntilString + ")");

            viewHolder.addToCalendarButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_INSERT)
                    .setData(CalendarContract.Events.CONTENT_URI)
                    .putExtra(CalendarContract.Events.TITLE, game.getName())
                    .putExtra(CalendarContract.Events.DESCRIPTION, "Game release date")
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, game.getFirstReleaseDate() * 1000)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, game.getFirstReleaseDate() * 1000);

                if (intent.resolveActivity(viewHolder.context.getPackageManager()) != null)
                {
                    viewHolder.context.startActivity(intent);
                } else {
                    Toast.makeText(viewHolder.context, "No calendar app found", Toast.LENGTH_SHORT).show();
                }
            });

            // Set game cover image
            if (game.getCover() != null) {
                String coverUrl = "https://images.igdb.com/igdb/image/upload/t_cover_big/" + game.getCover().getImageID() + ".png";
                Glide.with(viewHolder.itemView.getContext()).load(coverUrl).into(viewHolder.gameCoverImageView);
            }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return games.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
