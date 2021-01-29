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

public class DetailsFragment extends Fragment {

    public static final String DETAILS_FRAGMENT_TAG = "DETAILS_FRAGMENT_TAG";
    DetailsFragmentInterface detailsFragmentInterface;
    Button btnToTitleFragment;
    TextView txtViewDetails;
    EditText edtTextTitle;

    public interface DetailsFragmentInterface {
        public void onDetailsViewsListener(int id);
    }

    private void getViewsId(View view) {
        btnToTitleFragment = view.findViewById(R.id.btnToDetailsFragment);
        txtViewDetails = view.findViewById(R.id.txtViewTitle);
        edtTextTitle = view.findViewById(R.id.edtTextTitle);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailsFragmentInterface) {
            detailsFragmentInterface = (DetailsFragmentInterface) context;
        } else {
            throw new ClassCastException(" " + context.getClass().toString() + " должен реализовывать интерфейс DetailsFragmentInterface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        getViewsId(view);
        //получаем аргументы установленные в Activity
        if (getArguments().getString(TitleFragment.TITLE_FRAGMENT_TAG) != null) {
            String text = getArguments().getString(TitleFragment.TITLE_FRAGMENT_TAG);
            edtTextTitle.setText(text);
        }

        btnToTitleFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsFragmentInterface.onDetailsViewsListener(1);
            }
        });
        return view;
    }
}
