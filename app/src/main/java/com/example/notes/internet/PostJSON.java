package com.example.notes.internet;

import android.net.Uri;

import java.net.URL;

import retrofit2.http.Url;

public class PostJSON {
    public int id;
    public String description;
    public int votes;
    public String author;
    public String date;
    public URL gifURL;
    public int gifSize;
    public URL previewURL;
    public URL videoURL;
    public String videoPath;
    public int videoSize;
    public String type;
    public int width;
    public int height;
    public int commentCount;
    public int fileSize;
    public Boolean canVote;
}
