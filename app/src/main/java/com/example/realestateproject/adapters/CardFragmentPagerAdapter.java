package com.example.realestateproject.adapters;

import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.realestateproject.R;
import com.example.realestateproject.activities.HistoryActivity;
import com.example.realestateproject.activities.MyClientActivity;
import com.example.realestateproject.activities.MyRealEstatesActivity;
import com.example.realestateproject.fragments.CardFragment;
import com.example.realestateproject.supports.CardAdapter;
import com.example.realestateproject.supports.Constants;

import java.util.ArrayList;
import java.util.List;

public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<CardFragment> mFragments;
    private float mBaseElevation;

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;
        Class[] classes = {MyClientActivity.class, MyRealEstatesActivity.class, HistoryActivity.class};
        int[] resources = {R.drawable.customer, R.drawable.mansion, R.drawable.history};
        for(int i = 0; i< 3; i++){
            addCardFragment(new CardFragment(Constants.CARD_TITLE[i], Constants.CARD_SUBTITLE[i], classes[i], resources[i]));
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Class[] classes = {MyClientActivity.class, MyRealEstatesActivity.class, HistoryActivity.class};
        int[] resources = {R.drawable.customer, R.drawable.mansion, R.drawable.history};
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, new CardFragment(Constants.CARD_TITLE[position], Constants.CARD_SUBTITLE[position], classes[position], resources[position]));
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        mFragments.add(fragment);
    }

}