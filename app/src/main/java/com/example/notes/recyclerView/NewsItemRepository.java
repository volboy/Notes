package com.example.notes.recyclerView;

import java.util.ArrayList;
import java.util.List;

//класс сингтон для хранения данных о новостях
//так делаются классы Singleton
public class NewsItemRepository {
    private static List<NewsItem> newsItemList = new ArrayList<>();

    private static NewsItemRepository instance;

    //ПРИВАТНЫЙ КОНСТРУКТОР
    private NewsItemRepository() {
        //заполняем данные в цикле
        for (int i = 0; i < 30; i++) {
            newsItemList.add(new NewsItem("Заголовок" + i, "Подзаголовок" + i, 0));
        }
    }

    //проверяем был ли создан ранее класс NewsItemRepository
    //если нет создаем
    public static NewsItemRepository getInstance() {
        if (instance == null) {
            return instance = new NewsItemRepository();
        }
        return instance;

    }
    //метод получения списка
    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }


}
