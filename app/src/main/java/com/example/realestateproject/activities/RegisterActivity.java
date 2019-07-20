package com.example.realestateproject.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroUser;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_id, et_password;
    private Button btn_submit, btn_cancle;
    private RetroUser retroUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private void initialView() {
        et_id = findViewById(R.id.et_id_reg);
        et_password = findViewById(R.id.et_password_reg);
        btn_submit = findViewById(R.id.btn_submit_reg);
        btn_cancle = findViewById(R.id.btn_cancle_reg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.initialView();
        //Init retrofit to class
        Retrofit retrofitClient = RetroClient.getInstance();
        retroUser = retrofitClient.create(RetroUser.class);

        btn_submit.setOnClickListener(this);
        btn_cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_reg:
                String id = et_id.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                this.registerUser(id,password);
                break;
            case R.id.btn_cancle_reg:
                //Doing something
                finish();
                break;
        }
    }

    private void registerUser(String id, String password) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Id/Password is required", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(retroUser.registerUser(id, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String response) throws Exception {
                            Toast.makeText(RegisterActivity.this, ""+response, Toast.LENGTH_SHORT);
                        }
                    })
            );
        }
    }
}
