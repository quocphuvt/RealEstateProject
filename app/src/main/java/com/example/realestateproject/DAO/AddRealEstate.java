package com.example.realestateproject.DAO;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realestateproject.R;
import com.example.realestateproject.RealEstate_Activity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class AddRealEstate extends AppCompatActivity {
    EditText etname, etadress, etcontacnb,etdescription,etprice,etarea;

    private static final String TAG = AddRealEstate.class.getSimpleName();
    private ProgressDialog pDialog;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://192.168.0.109:1998"); //Nhap IP cua server NodeJS
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_realestate);

        mSocket.connect();

        anhxa();

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        mSocket.on("addstudent",onAdd);
    }

    private void anhxa() {
        etname = (EditText) findViewById(R.id.et_name_add);
        etadress = (EditText) findViewById(R.id.et_address_add);
        etcontacnb = (EditText) findViewById(R.id.et_contact_add);
        etdescription = (EditText) findViewById(R.id.et_description_add);
        etprice = (EditText) findViewById(R.id.et_price_add);
        etarea = (EditText) findViewById(R.id.et_area_add);

    }

    private void addUser( String name, String address, String contactnumber,String description,
                         String price,String area) {
        mSocket.emit("addstudent",  name, address, contactnumber,description,price,area);
        pDialog.setMessage("Adding ...");
        showDialog();
    }

    private Emitter.Listener onAdd = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            String data = args[0].toString();

            if (data == "true"){
                Intent i = new Intent(getApplicationContext(), RealEstate_Activity.class);
                startActivity(i);
            } else {
                Log.d("error", "cant add");
            }
            hideDialog();
        }
    };

    private void showDialog() {
        if(!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog() {
        if(pDialog.isShowing())
            pDialog.dismiss();
    }

    public void Add_Student(View view) {

        String name = etname.getText().toString().trim();
        String address = etadress.getText().toString().trim();
        String contactnumber = etcontacnb.getText().toString().trim();
        String descriptione = etdescription.getText().toString().trim();
        String price = etprice.getText().toString().trim();
        String area = etarea.getText().toString().trim();

        if (!name.isEmpty() && !address.isEmpty()  && !contactnumber.isEmpty()&& !descriptione.isEmpty()
                && !price.isEmpty()&& !area.isEmpty()) {
            addUser(name,address,contactnumber,descriptione,price,area);
        } else {
            Toast.makeText(this, "Please enter your details!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel_Add_Student(View view) {
        finish();
    }
}


