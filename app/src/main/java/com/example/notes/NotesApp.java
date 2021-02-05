package com.example.notes;

import android.app.Application;
import com.example.notes.internet.PostsService;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NotesApp extends Application {

    public PostsService postsService;
    private static NotesApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRetrofit();
    }

    public static NotesApp getInstance() {
        return instance;
    }

    private void initRetrofit() {

        //добавление interceptor
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //инициализируем Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://developerslife.ru/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Создаем сервис (по сути сервис -это описание наших endpoints
        postsService = retrofit.create(PostsService.class);

    }
}
