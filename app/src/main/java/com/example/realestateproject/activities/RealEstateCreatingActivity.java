package com.example.realestateproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.models.RealEstate;
import com.example.realestateproject.retrofits.RetroClient;
import com.example.realestateproject.retrofits.RetroReal;
import com.example.realestateproject.retrofits.RetroUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RealEstateCreatingActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_realName,et_realAddress,et_realDescription,et_realPrice,et_realContact,et_realArea;
    private Button btn_realSubmit;
    private RetroReal retroReal;


    private void initialView(){
        et_realName=findViewById(R.id.et_realName_creating);
        et_realAddress=findViewById(R.id.et_realAddress_creating);
        et_realContact=findViewById(R.id.et_realContact_creating);
        et_realDescription=findViewById(R.id.et_realDescription_creating);
        et_realArea=findViewById(R.id.et_realArea_creating);
        et_realPrice=findViewById(R.id.et_realPrice_creating);
        btn_realSubmit=findViewById(R.id.btn_realSubmit_creating);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_estate_creating);
        initialView();
        Retrofit retrofitClient = RetroClient.getInstance();
        retroReal = retrofitClient.create(RetroReal.class);
        btn_realSubmit.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_realSubmit_creating:
                String realName = et_realName.getText().toString().trim();
                String realAddress=et_realAddress.getText().toString().trim();
                String realContact = et_realContact.getText().toString().trim();
                String realDescription = et_realDescription.getText().toString().trim();
                String realArea = et_realArea.getText().toString().trim();
                String realPrice = et_realPrice.getText().toString().trim();
                if(realName.isEmpty()){
                    et_realName.setError("Field can't be empty");
                    return;
                }
                else if(realAddress.isEmpty()  ){
                    et_realAddress.setError("Field can't be empty");
                    return;
                }
                else if(realContact.isEmpty()){
                    et_realContact.setError("Field can't be empty");
                    return;
                }
                else if(realDescription.isEmpty()){
                    et_realDescription.setError("Field can't be empty");
                    return;
                }
                else if(realPrice.isEmpty()){
                    et_realArea.setError("Field can't be empty");
                    return;
                }
                else  if(realArea.isEmpty()){
                    et_realPrice.setError("Field can't be empty");
                    return;
                }
                else{
                    RealEstate realEstate = new RealEstate(realName,realAddress,realContact,realDescription,Double.parseDouble(realPrice),Double.parseDouble(realArea));
                    Call<RealEstate> call = retroReal.createReal(realEstate);
                    call.enqueue(new Callback<RealEstate>() {
                        @Override
                        public void onResponse(Call<RealEstate> call, Response<RealEstate> response) {
                            Log.i("res:", response.message() + "");
                            if (response.isSuccessful()) {
                                Toast.makeText(RealEstateCreatingActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                Log.i("Result:", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<RealEstate> call, Throwable t) {
                            Toast.makeText(RealEstateCreatingActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Toast.makeText(this, ""+realName+realAddress+realArea+realContact+realDescription+realPrice, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_cancel_creating :
                finish();
                break;
        }
    }
}
