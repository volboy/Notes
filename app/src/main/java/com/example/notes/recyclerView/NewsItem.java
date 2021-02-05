package com.example.notes.recyclerView;

import android.net.Uri;

import com.example.notes.internet.PostJSON;

import java.net.URL;
import java.util.List;

import retrofit2.http.Url;

public class NewsItem {
    public String title;
    public String subtitle;
    public URL gifURL;


    public NewsItem(PostJSON postJSON) {
        this.title = postJSON.description;
        this.subtitle = postJSON.date;
        this.gifURL = postJSON.gifURL;
    }


}
