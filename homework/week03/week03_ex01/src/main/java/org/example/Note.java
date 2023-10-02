package org.example;

enum Color {
    RED,
    GREEN,
    BLUE
}

public class Note {
    private String content;
    private Color backgroundColor;

    public Note() {
        content = "";
        backgroundColor = Color.RED;
    }

    public Note(String content, Color backgroundColor) {
        this.content = content;
        this.backgroundColor = backgroundColor;
    }

    public String getContent() {
        return content;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String toString() {
        return "Note: " + content + " (" + backgroundColor + ")";
    }
}
