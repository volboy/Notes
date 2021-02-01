package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements TitleFragment.TitleFragmentInterface, DetailsFragment.DetailsFragmentInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //устанавливаем TitleFragment при создании Activity
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragments_container, new TitleFragment(), TitleFragment.TITLE_FRAGMENT_TAG)
                .commit();


    }

    //реализуем метод интерфйса TitleFragmentInterface
    //в этом случае клик кнопки в TitleFragment вызывает
    //создание DetailsFragment и переход к нему
    //попутно передаем данные
    @Override
    public void onTitleViewsClickListener(int id, String text) {
        //создаем экземпляр фрагмента
        DetailsFragment detailsFragment=new DetailsFragment();
        Bundle bundle=new Bundle();
        //передаем строку в DetailsFragment
        //можно сделать это во время создания DetailsFragment
        //для этого используем newInstance
        bundle.putString(TitleFragment.TITLE_FRAGMENT_TAG, text);
        detailsFragment.setArguments(bundle);

        //переход к DetailsFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, detailsFragment, DetailsFragment.DETAILS_FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }

    //реализуем метод интерфйса DetailFragmentInterface
    //в этом случае клик кнопки в DetailsFragment вызывает
    //создание TitleFragment и переход к нему
    @Override
    public void onDetailsViewsListener(int id) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragments_container, new TitleFragment(), TitleFragment.TITLE_FRAGMENT_TAG)
                .commit();
    }
}