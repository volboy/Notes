package com.example.notes.internet;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostsService {

    @GET("random?json=true")
    Call<PostsJSON> getPosts();

    @GET("hot/{id}")
    Call<PostsJSON> getPostById(@Path("id") int id);
}
