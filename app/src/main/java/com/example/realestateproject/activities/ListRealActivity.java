package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.adapters.ListRealAdapter;
import com.example.realestateproject.interfaces.ClickRealItemListener;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListRealActivity extends AppCompatActivity implements ClickRealItemListener {
    private ListView lv_reals;
    private Toolbar toolbar;
    private RetroReal retroReal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_real);
        lv_reals = findViewById(R.id.lv_reals_listreal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        Intent i = getIntent();
        final String city = i.getStringExtra("city");
        Retrofit retrofit = RetroClient.getInstance();
        retroReal = retrofit.create(RetroReal.class);
        Call<List<RealEstate>> call = retroReal.getListreals();
        call.enqueue(new Callback<List<RealEstate>>() {
            @Override
            public void onResponse(Call<List<RealEstate>> call, Response<List<RealEstate>> response) {
                if(city.isEmpty()){
                    getSupportActionBar().setTitle("Having "+response.body().size()+" properties...");
                }
                getSupportActionBar().setTitle(city+" have "+response.body().size()+" properties...");
//                Toast.makeText(ListRealActivity.this, ""+response.body(), Toast.LENGTH_SHORT).show();
                ListRealAdapter listRealAdapter = new ListRealAdapter(response.body(), ListRealActivity.this, ListRealActivity.this);
                lv_reals.setAdapter(listRealAdapter);

            }

            @Override
            public void onFailure(Call<List<RealEstate>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(String idReal) {
        Toast.makeText(this, ""+idReal, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, RealDetailsActivity.class);
        i.putExtra("id", idReal);
        startActivity(i);
    }
}
