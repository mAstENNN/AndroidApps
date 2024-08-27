package com.example.project2;


public class Movie {
    private String title;
    private String actors;
    private int thumbnail; // Resource ID for the drawable thumbnail
    private int highResImageId; // Resource ID for the drawable high-res image
    private String detailsUrl; // URL to the movie's web page
    private String wikipediaUrl; // Wikipedia page URL
    private String streamUrl; // Streaming service URL

    // Constructor
    public Movie(int thumbnail, int highResImageId, String actors, String title, String detailsUrl, String wikipediaUrl, String streamUrl) {
        this.title = title;
        this.actors = actors;
        this.thumbnail = thumbnail;
        this.highResImageId = highResImageId;
        this.detailsUrl = detailsUrl;
        this.wikipediaUrl = wikipediaUrl;
        this.streamUrl = streamUrl;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getHighResImageId() {
        return highResImageId;
    }

    public void setHighResImageId(int highResImageId) {
        this.highResImageId = highResImageId;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }
}


//package com.example.project2;
//
//public class Movie {
//    private String title;
//    private String actors;
//    private int thumbnail; // Resource ID for the drawable
//    private String detailsUrl; // URL to the movie's web page
//
//    public Movie(int thumbnail, String actors, String title, String detailsUrl) {
//        this.title = title;
//        this.actors = actors;
//        this.thumbnail = thumbnail;
//        this.detailsUrl = detailsUrl;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getActors() {
//        return actors;
//    }
//
//    public void setActors(String actors) {
//        this.actors = actors;
//    }
//
//    public int getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(int thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public String getDetailsUrl() {
//        return detailsUrl;
//    }
//
//    public void setDetailsUrl(String detailsUrl) {
//        this.detailsUrl = detailsUrl;
//    }
//}
