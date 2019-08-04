package com.example.realestateproject.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.realestateproject.R;
import com.example.realestateproject.supports.CardAdapter;

public class CardFragment extends Fragment {
    private Button btn_goToDetails;
    private CardView mCardView;
    private TextView tv_title, tv_subtitle;
    private String title, subtitle;

    public CardFragment(){

    }

    public CardFragment(String title, String subtitle){
        this.title = title;
        this.subtitle = subtitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        mCardView = (CardView) view.findViewById(R.id.cardView);
        tv_title = view.findViewById(R.id.card_title_home);
        tv_subtitle = view.findViewById(R.id.card_subtitle_home);
        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
        btn_goToDetails = view.findViewById(R.id.card_button_home);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
