package com.example.notes.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;

import java.util.List;

public class NewsItemsAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    private List<NewsItem> newsItems;

    public NewsItemsAdapter(LayoutInflater layoutInflater, List<NewsItem> newsItems) {
        this.layoutInflater = layoutInflater;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    //здесь нужно создать ViewHolder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new SimpleTextHeaderViewHolder(layoutInflater.inflate(R.layout.header_simple_text, parent, false));
        } else {
            return new NewsItemViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false));
        }


    }

    //метод вызывается при скролле, чтобы обновить данные
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsItemViewHolder) {
            ((NewsItemViewHolder) holder).bind(newsItems.get(position-1));
        }
    }

    //Определяем вид ViewHolder с header или без
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }

    //метод возвращает кол-во записей в RecyclerView
    @Override
    public int getItemCount() {
        return newsItems.size() + 1; //+1 потому что, один item уйдет под header
    }
}
