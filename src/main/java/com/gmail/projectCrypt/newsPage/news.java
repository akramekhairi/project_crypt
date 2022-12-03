package com.gmail.projectCrypt.newsPage;

public class news {

    private String title;
    private String body;
    private String source;
    private String url;
    private String thumbnail;
    //Constructor for an object
    public news(String title, String body, String source, String url, String thumbnail) {
        this.title = title;
        this.body = body;
        this.source = source;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    //Getters and setters for the attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
