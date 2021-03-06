package com.example.realestateproject.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.CardFragmentPagerAdapter;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.supports.Constants;
import com.example.realestateproject.supports.ShadowTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.NewThreadScheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private ViewPager home_viewPager;
    private TextView tv_numReal;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        home_viewPager = view.findViewById(R.id.home_viewPager);
        tv_numReal = view.findViewById(R.id.tv_numReal);
        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getChildFragmentManager(), dpToPixels(2, getContext()));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(home_viewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        Retrofit retrofit = RetroClient.getInstance();
        RetroReal retroReal = retrofit.create(RetroReal.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");
        Call<UserResponses> callTotalReals = retroReal.countNumReal(userId);
        callTotalReals.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 0) {
                        tv_numReal.setText(userResponses.getMessage());
                    }
                    else {
                        int numReal = userResponses.getRealList().size();
                        String post = numReal < 2 ? " post" : " posts";
                        tv_numReal.setText("You have: "+numReal+post);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });

        home_viewPager.setAdapter(pagerAdapter);
        home_viewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        home_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
