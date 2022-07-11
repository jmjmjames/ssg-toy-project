package com.example;

public class WiseSaying {
    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\": " + id + "," +
                "\"content\": \"" + content + "\"," +
                "\"author\": \"" + author +  "\"" +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
