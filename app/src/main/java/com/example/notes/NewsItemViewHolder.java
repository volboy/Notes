package com.example.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

public class NewsItemViewHolder extends ViewHolder {
    TextView txtViewItemTitle, txtViewItemSubTitle;

    public NewsItemViewHolder(@NonNull View itemView) {
        super(itemView);

        //ищем поля в разметки item
        txtViewItemTitle = itemView.findViewById(R.id.txtViewItemTitle);
        txtViewItemSubTitle = itemView.findViewById(R.id.txtViewItemSubTitle);
    }

    //метод подгружает данные в разметку нашего item
    public void bind(NewsItem item) {
        txtViewItemTitle.setText(item.title);
        txtViewItemSubTitle.setText(item.subtitle);
    }
}
