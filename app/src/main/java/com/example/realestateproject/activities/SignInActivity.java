package com.example.realestateproject.activities;

import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private RetroUser retroUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    TextView tv_Resgister;

    private void inititalView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tv_Resgister= findViewById(R.id.login_register);

        this.inititalView();
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign In");

        tv_Resgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resgister = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(resgister);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }
}
