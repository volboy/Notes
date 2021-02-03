package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.internet.PostsJSON;
import com.example.notes.recyclerView.NewsItem;
import com.example.notes.recyclerView.NewsItemRepository;
import com.example.notes.recyclerView.NewsItemsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TitleFragment extends Fragment {

    private List<NewsItem> newsItemList = new ArrayList<>();
    public static final String TITLE_FRAGMENT_TAG = "TITLE_FRAGMENT_TAG";
    //экземпляр интерфейса
    TitleFragmentInterface titleFragmentInterface;
    Button btnToDetailsFragment, btnAdd, btnDelete;
    TextView txtViewTitle;
    EditText edtTextTitle;
    private Context context;

    //объявление интерфейса с одним методом
    public interface TitleFragmentInterface {
        public void onTitleViewsClickListener(int id, String text);
    }

    //инициализация вьюшек
    private void getViewsId(View view) {
        btnToDetailsFragment = view.findViewById(R.id.btnToDetailsFragment);
        txtViewTitle = view.findViewById(R.id.txtViewTitle);
        edtTextTitle = view.findViewById(R.id.edtTextTitle);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnDelete = view.findViewById(R.id.btnDelete);
    }

    //во время присоединения фрагмента к акстивити
    //проверяем реализует ли активити интерфейс фрагмента
    //и если реализует, то экземпляру передаем ссылку на эту реализацию
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof TitleFragmentInterface) {
            titleFragmentInterface = (TitleFragmentInterface) context;
        } else {
            throw new ClassCastException(" " + context.getClass().toString() + " должен реализовывать интерфейс TitleFragmentInterface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);
        getViewsId(view);
        //создаем коллекцию новостей
        //newsItemList.addAll(NewsItemRepository.getInstance().getNewsItemList());

        //создаем RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        //создаем Layout менеджер для RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);

        //устанавливаем RecyclerView наш LinearLayoutManager
        recyclerView.setLayoutManager(linearLayoutManager);



        //Загружаем данные с сайта
        NotesApp.getInstance().postsService.getPosts().enqueue(new Callback<PostsJSON>() {
            @Override
            public void onResponse(Call<PostsJSON> call, Response<PostsJSON> response) {
                    //возращаются запросы на сервер
                    //коды ответа (404, 500, c 200-300 успешные)
                    if (response.isSuccessful()){
                        //создаем обьект куда скидываем обьекты JSON
                        PostsJSON postsJSON=response.body();
                        //в коллекцию которая связана с RecyclerView скидываем наш обьект через класс NewsItem
                        //мы разделили сущности чтобы не менять название полей, если они изменятся на сервере
                        newsItemList.clear();
                        newsItemList.add(new NewsItem(postsJSON));
                        //устанавливаем адаптер
                        recyclerView.setAdapter(new NewsItemsAdapter(inflater, newsItemList));
                    }else {

                    }
            }

            @Override
            public void onFailure(Call<PostsJSON> call, Throwable t) {
                //нет интернета, не валидный JSON
                Log.i("Retrofit", t.getMessage());
            }
        });



        //кастомизируем разделитель
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(context.getDrawable(R.drawable.black_line));

        //Добавляем разделитель
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(dividerItemDecoration);

        //подгрузка данных при достижении конца списка
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //если последний видимый обьект в recyclerView равен последнему элементу
                //в коллекции, то создать еще элементов в коллекции
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == newsItemList.size()) {
                    newsItemList.addAll(NewsItemRepository.getInstance().getNewsItemList());
                    recyclerView.getAdapter().notifyItemRangeInserted(newsItemList.size(), 30);
                }
            }
        });

        btnToDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //вызываем метод интерфейса, причем в onAttach мы указали реализацию метода из activity
                titleFragmentInterface.onTitleViewsClickListener(1, edtTextTitle.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //удаляем третий элемент в коллекции
                newsItemList.remove(2);
                //обновляем адаптер, указывая какой элемент удалили
                recyclerView.getAdapter().notifyItemRemoved(3);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //удаляем третий элемент в коллекции
                newsItemList.add(2, new NewsItem("Добавленная новость", "Кошка родила котят...", 0));
                //обновляем адаптер, указывая какой элемент удалили
                recyclerView.getAdapter().notifyItemInserted(3);
            }
        });
        return view;
    }


}
