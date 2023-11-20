package com.example.gamereleasehelper.models;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {
    private long id;
    private Cover cover;
    @SerializedName("first_release_date")
    private long firstReleaseDate;
    private String name;
    private List<Platform> platforms;
    private List<Cover> screenshots;
    private String summary;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public Cover getCover() { return cover; }
    public void setCover(Cover value) { this.cover = value; }

    public long getFirstReleaseDate() { return firstReleaseDate; }
    public void setFirstReleaseDate(long value) { this.firstReleaseDate = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<Platform> getPlatforms() { return platforms; }
    public void setPlatforms(List<Platform> value) { this.platforms = value; }

    public List<Cover> getScreenshots() { return screenshots; }
    public void setScreenshots(List<Cover> value) { this.screenshots = value; }

    public String getSummary() { return summary; }
    public void setSummary(String value) { this.summary = value; }
}

