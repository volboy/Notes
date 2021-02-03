package com.example.notes.recyclerView;

import com.example.notes.internet.PostsJSON;

public class NewsItem {
    public String title;
    public String subtitle;
    public int color;

    public NewsItem(String title, String subtitle, int color) {
        this.title = title;
        this.subtitle = subtitle;
        this.color = color;
    }

    public NewsItem(PostsJSON postsJSON) {
        this.title = postsJSON.description;
        this.subtitle = postsJSON.date;
    }

}
