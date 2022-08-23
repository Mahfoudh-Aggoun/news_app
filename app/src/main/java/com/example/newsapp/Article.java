package com.example.newsapp;

public class Article {
    private String title;
    private String authorName;
    private String sectionName;
    private String thumbnailUrl;
    private String authorImageUrl;
    private String webUrl;
    private String publishDate;
    private String body;


    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public Article(String title, String authorName, String sectionName, String thumbnailUrl, String authorImageUrl, String webUrl, String publishDate, String body) {
        this.title = title;
        this.authorName = authorName;
        this.sectionName = sectionName;
        this.thumbnailUrl = thumbnailUrl;
        this.authorImageUrl = authorImageUrl;
        this.webUrl = webUrl;
        this.publishDate = publishDate;
        this.body = body;
    }
}
