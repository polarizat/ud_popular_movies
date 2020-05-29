package com.example.popularmoviesapp.model;

public class Trailer {

    public static final String TMDBM_KEY_ID = "id";
    public static final String TMDBM_KEY_NAME = "name";
    public static final String TMDBM_KEY_KEY = "key";
    public static final String TMDBM_KEY_SITE = "site";
    public static final String TMDBM_KEY_TYPE = "type";

    private String id;
    private String name;
    private String key;
    private String site;
    private String type;

    public Trailer(String id, String name, String key, String site, String type) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.site = site;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
