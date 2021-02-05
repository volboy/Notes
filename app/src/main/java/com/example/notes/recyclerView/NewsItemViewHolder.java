package com.example.notes.recyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.notes.R;
import com.example.notes.recyclerView.NewsItem;

public class NewsItemViewHolder extends ViewHolder {
    TextView txtViewItemTitle, txtViewItemSubTitle;
    ImageView imageView;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);

        //ищем поля в разметки item
        txtViewItemTitle = itemView.findViewById(R.id.txtViewItemTitle);
        txtViewItemSubTitle = itemView.findViewById(R.id.txtViewItemSubTitle);
        imageView=itemView.findViewById(R.id.imageViewGlide);
    }

    //метод подгружает данные в разметку нашего item
    public void bind(NewsItem item) {
        txtViewItemTitle.setText(item.title);
        txtViewItemSubTitle.setText(item.subtitle);
        Glide.with(itemView.getContext())
                .load(item.gifURL)
                .fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

    }
}
