package com.example.realestateproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.realestateproject.Adapter.RealEstateAdapter;
import com.example.realestateproject.DAO.AddRealEstate;
import com.example.realestateproject.models.RealEstateModel;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class RealEstate_Activity extends AppCompatActivity {


        List<RealEstateModel> list = new ArrayList<>();
        ListView lv;
        RealEstateAdapter adapter;

        private static final String TAG = RealEstate_Activity.class.getSimpleName();

        //KẾT NỐI IP
        private Socket mSocket;

        {
            try {
                mSocket = IO.socket("http://192.168.0.109:1998"); //Nhap IP cua server NodeJS
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        // SHOW
        private Emitter.Listener onGetUser = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject jsonObject = (JSONObject) args[0];

                            try {
                                String id = jsonObject.getString("_id");
                                String name = jsonObject.getString("name");
                                String address = jsonObject.getString("address");
                                String contactnumber = jsonObject.getString("contactnumber");
                                String description= jsonObject.getString("description");
                                String price= jsonObject.getString("price");
                                String area = jsonObject.getString("area");

                                RealEstateModel realEstateModel = new RealEstateModel(id, name, address, contactnumber,
                                        description , price , area);
                                list.add(realEstateModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!list.isEmpty()) {
                                adapter = new RealEstateAdapter(list, getApplicationContext());
                                lv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } else {
                                Log.d("error", "cant add");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };

        private Emitter.Listener onUpdate = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = args[0].toString();

                if (data == "true"){
                    Intent i = new Intent(getApplication(),RealEstate_Activity.class);
                    startActivity(i);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(MembersListActivity.this, "1010", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("error", "cant register");
                }
//            hideDialog();
            }
        };

//        private void updateUser(String id, String masv, String hoten, String email, String lop) {
//            mSocket.emit("updateStudent", id, masv, hoten, email, lop);
////        pDialog.setMessage("Updatting ...");
////        showDialog();
//        }

        private Emitter.Listener onDelete = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String data = args[0].toString();

                if (data == "true"){
                    Intent i = new Intent(getApplication(),RealEstate_Activity.class);
                    startActivity(i);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(MembersListActivity.this, "1010", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("error", "cant register");
                }
//            hideDialog();
            }
        };

        private void delUser(String id, String username) {
            mSocket.emit("deleteStudent", id, username);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_real_estate_);

            mSocket.connect();
            lv = findViewById(R.id.lv_bds);

            mSocket.on("getStudent", onGetUser);
            mSocket.on("updateStudent", onUpdate);
            mSocket.on("deleteStudent", onDelete);

            mSocket.emit("getStudent");
            registerForContextMenu(lv);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            getMenuInflater().inflate(R.menu.menu_them_xoa_sua, menu);
            super.onCreateContextMenu(menu, v, menuInfo);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            int idx = info.position;
            RealEstateModel user = list.get(idx);
            switch(item.getItemId()) {
                case R.id.add:
                    Intent intent = new Intent(getApplication(), AddRealEstate.class);
                    startActivity(intent);
                    return(true);

                case R.id.xoa:
                    DialogXoa(user.getId(), user.getName());
                    break;
                default:
            }
            return super.onContextItemSelected(item);
        }

//    dialog

//        public void DialogSua(final String id, String masv, String hoten, String email, String lop){
//           final Dialog dialog = new Dialog(this);
//            dialog.setTitle("Cập nhật Sinh vien");
//            dialog.setContentView(R.layout.dialog_update);
//
//            final EditText etID = (EditText) dialog.findViewById(R.id.et_ids_dlu);
//           final EditText etName = (EditText) dialog.findViewById(R.id.et_name_dlu);
//          final EditText etEmail = (EditText) dialog.findViewById(R.id.et_email_dlu);
//            final EditText etClass = (EditText) dialog.findViewById(R.id.et_class_dlu);
//          Button btnSua = (Button) dialog.findViewById(R.id.btn_up);
//           Button btnHuysua = (Button) dialog.findViewById(R.id.btn_canlup);
//
//            etID.setText(masv);
//            etName.setText(hoten);
//            etEmail.setText(email);
//          etClass.setText(lop);
//
//            btnSua.setOnClickListener(new View.OnClickListener() {
//               @Override
//               public void onClick(View view) {
//                   String masv = etID.getText().toString().trim();
//                   String hoten = etName.getText().toString().trim();
//                   String email = etEmail.getText().toString().trim();
//                    String lop = etClass.getText().toString().trim();
//
//                    if (!masv.isEmpty() && !hoten.isEmpty()  && !email.isEmpty() && !lop.isEmpty()) {
//                        updateUser(id, masv, hoten, email, lop);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Please enter your details!", Toast.LENGTH_SHORT).show();
//                    }
//                    dialog.dismiss();
//                }
//            });
//
//            btnHuysua.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//
//            dialog.show();
//        }


        //
        //  Xóa
        public void DialogXoa(final String id, final String hoten){
            final AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
            dialogXoa.setMessage("Bạn có muốn xóa bạn sinh viên "+hoten+" không?");
            dialogXoa.setPositiveButton("Xóa luôn", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    delUser(id, hoten);
                }
            });
            dialogXoa.setNegativeButton("Thôi, tha cho", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            dialogXoa.show();
        }
    }

