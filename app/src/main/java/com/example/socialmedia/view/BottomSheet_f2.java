package com.example.socialmedia.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.socialmedia.R;
import com.example.socialmedia.controller.Related;
import com.example.socialmedia.controller.YourQuestion;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheet_f2 extends BottomSheetDialogFragment {
    CardView cardView, cardView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_f2,null);
        cardView = view.findViewById(R.id.related_f2);
        cardView2 = view.findViewById(R.id.your_Question_f2);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Related.class));
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), YourQuestion.class));
            }
        });


        return view;
    }
}