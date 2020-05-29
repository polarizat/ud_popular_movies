package com.example.popularmoviesapp.model;

public class Review {

    public static final String TMDBM_KEY_ID = "id";
    public static final String TMDBM_KEY_AUTHOR = "author";
    public static final String TMDBM_KEY_CONTENT = "content";
    public static final String TMDBM_KEY_URL = "url";

    private String id;
    private String author;
    private String content;
    private String url;

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

}


