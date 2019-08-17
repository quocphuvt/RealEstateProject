package com.example.realestateproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ViewPagerAdapter;
import com.example.realestateproject.models.UserModel;
import com.example.realestateproject.models.UserResponses;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;
import com.example.realestateproject.supports.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerlayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mToogle;
    private Toolbar toolbar;
    private FloatingActionButton add_fab;
    private ViewPager main_viewPager;
    private BottomNavigationView bottom_nav_view;
    private MenuItem prevMenuItem;
    private View headerLayout;
    private TextView tv_fullname_header, tv_id_header;
    private ImageView iv_avatar_header;
    private RetroUser retroUser;

    private void initView(){
        toolbar = findViewById(R.id.toolbar_logo);
        navigationView = findViewById(R.id.navigationView);
        mDrawerlayout =  findViewById(R.id.drawer);
        add_fab = findViewById(R.id.add_fab);
        main_viewPager = findViewById(R.id.main_viewPager);
        bottom_nav_view = findViewById(R.id.bottom_nav_view);
        //Initialize view in header
        headerLayout = navigationView.getHeaderView(0);
        tv_fullname_header = headerLayout.findViewById(R.id.tv_fullname_header);
        tv_id_header = headerLayout.findViewById(R.id.tv_id_header);
        iv_avatar_header = headerLayout.findViewById(R.id.iv_avatar_header);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        add_fab.setOnClickListener(this);
        mToogle = new ActionBarDrawerToggle(this,mDrawerlayout,R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mToogle);
        mToogle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_date);

        Intent i = getIntent();
        if(i.getBooleanExtra("see_map", true)) {
            Toast.makeText(this, "see map ne", Toast.LENGTH_SHORT).show();
            main_viewPager.setCurrentItem(1);
        }

        Retrofit retrofit = RetroClient.getInstance();
        retroUser = retrofit.create(RetroUser.class);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        String userId = sharedPreferences.getString("id", "");

        this.getCurrentUser(userId);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        main_viewPager.setOffscreenPageLimit(4);
        main_viewPager.setAdapter(viewPagerAdapter);
        main_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottom_nav_view.getMenu().getItem(0).setChecked(false);
                }
                bottom_nav_view.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottom_nav_view.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        add_fab.show();
                        main_viewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_map:
                        main_viewPager.setCurrentItem(1);
                        add_fab.hide();
                        break;
                    case R.id.navigation_search:
                        main_viewPager.setCurrentItem(2);
                        add_fab.show();
                        break;
                }
                return false;
            }
        });
    }

    private void getCurrentUser(String id) {
        Call<UserResponses> callCurrentUser = retroUser.getCurrentUser(id);
        callCurrentUser.enqueue(new Callback<UserResponses>() {
            @Override
            public void onResponse(Call<UserResponses> call, Response<UserResponses> response) {
                if(response.isSuccessful()) {
                    UserResponses userResponses = response.body();
                    if(userResponses.getStatus() == 1) {
                        UserModel userModel = userResponses.getUserModel();
                        iv_avatar_header.setImageBitmap(Utils.decodeBase64Image(userModel.getAvatar()));
                        tv_id_header.setText("ID: "+userModel.getId());
                        tv_fullname_header.setText(userModel.getFullName());
                    }
                    else if (userResponses.getStatus() == 0) {
                        tv_id_header.setText("ID: Unknown");
                        tv_fullname_header.setText("Unknown");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponses> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToogle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerlayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_fab:
                startActivity(new Intent(this, RealEstateCreatingActivity.class));
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.ic_userInfo:
                startActivity(new Intent(this, UpdateProfileUserActivity.class));
                break;
            case R.id.ic_signout:
                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("autoLogin", false);
                editor.apply();
                startActivity(new Intent(this, SignInActivity.class));
                break;
            case R.id.ic_about:
                Toast.makeText(this, "Develope by team 2", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
