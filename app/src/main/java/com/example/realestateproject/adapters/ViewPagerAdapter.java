package com.example.realestateproject.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.realestateproject.fragments.ContactFragment;
import com.example.realestateproject.fragments.HomeFragment;
import com.example.realestateproject.fragments.MapFragment;
import com.example.realestateproject.fragments.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new MapFragment();
                break;
            case 2:
                fragment = new SearchFragment();
                break;
            default:
                return null;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3; //Trả về số fragment tương ứng với số Tab
    }
}
