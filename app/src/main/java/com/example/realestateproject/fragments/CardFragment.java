package com.example.realestateproject.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.realestateproject.R;
import com.example.realestateproject.supports.CardAdapter;

public class CardFragment extends Fragment {
    private Button btn_goToDetails;
    private ImageView iv_feature;
    private CardView mCardView;
    private TextView tv_title, tv_subtitle;
    private String title, subtitle;
    private Class className;
    private int iv_resource;

    public CardFragment(){

    }

    public CardFragment(String title, String subtitle,Class className, int iv_resource){
        this.title = title;
        this.subtitle = subtitle;
        this.className = className;
        this.iv_resource = iv_resource;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        mCardView = (CardView) view.findViewById(R.id.cardView);
        iv_feature = view.findViewById(R.id.iv_feature);
        tv_title = view.findViewById(R.id.card_title_home);
        tv_subtitle = view.findViewById(R.id.card_subtitle_home);
        tv_title.setText(title);
        tv_subtitle.setText(subtitle);
        btn_goToDetails = view.findViewById(R.id.card_button_home);
        iv_feature.setImageResource(iv_resource);
        btn_goToDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), className));
            }
        });
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
