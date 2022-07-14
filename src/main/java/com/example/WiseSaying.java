package com.example;

import java.util.Map;

public class WiseSaying {
    private final int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public WiseSaying(Map<String, Object> map) {
        this.id = (int) map.get("id");
        this.content = (String) map.get("content");
        this.author = (String) map.get("author");
    }

    @Override
    public String toString() {
        return "WiseSaying{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String toJson() {
        return """
                {
                    "id": %d,
                    "content": "%s",
                    "author": "%s"
                }
                """
                .stripIndent()
                .formatted(id, content, author)
                .trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WiseSaying)) return false;

        WiseSaying other = (WiseSaying) o;

        if (this.id != other.id) return false;
        if (!this.content.equals(other.content)) return false;
        if (!this.author.equals(other.author)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
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

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

