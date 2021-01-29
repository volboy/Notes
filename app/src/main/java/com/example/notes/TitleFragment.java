package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TitleFragment extends Fragment {

    public static final String TITLE_FRAGMENT_TAG = "TITLE_FRAGMENT_TAG";
    //экземпляр интерфейса
    TitleFragmentInterface titleFragmentInterface;
    Button btnToDetailsFragment;
    TextView txtViewTitle;
    EditText edtTextTitle;

    //объявление интерфейса с одним методом
    public interface TitleFragmentInterface {
        public void onTitleViewsClickListener(int id, String text);
    }

    //инициализация вьюшек
    private void getViewsId(View view){
        btnToDetailsFragment = view.findViewById(R.id.btnToDetailsFragment);
        txtViewTitle = view.findViewById(R.id.txtViewTitle);
        edtTextTitle = view.findViewById(R.id.edtTextTitle);
    }

    //во время присоединения фрагмента к акстивити
    //проверяем реализует ли активити интерфейс фрагмента
    //и если реализует, то экземпляру передаем ссылку на эту реализацию
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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
        btnToDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //вызываем метод интерфейса, причем в onAttach мы указали реализацию метода из activity
                titleFragmentInterface.onTitleViewsClickListener(1, edtTextTitle.getText().toString());
            }
        });
        return view;
    }


}
