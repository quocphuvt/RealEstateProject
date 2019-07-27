package com.example.realestateproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.realestateproject.Contact.MyContact;
import com.example.realestateproject.activities.MySMSActivity;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    EditText editName, editPhone;
    Button btnSave;
    ListView lvContact;
    ArrayList<MyContact> arrContact = new ArrayList<MyContact> ();
    ArrayAdapter<MyContact> adapter = null;
    MyContact selectedContact = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);
        doGetFormWidgets ();
        doAddEvents ();
    }

    public void doGetFormWidgets() {
        btnSave = (Button) findViewById (R.id.btnSaveContact);
        editName = (EditText) findViewById (R.id.editName);
        editPhone = (EditText) findViewById (R.id.editPhone);
        lvContact = (ListView) findViewById (R.id.lvContact);
        adapter = new ArrayAdapter<MyContact>
                (this, android.R.layout.simple_list_item_1, arrContact);
        lvContact.setAdapter (adapter);
        registerForContextMenu (lvContact);
    }

    public void doAddEvents() {
        btnSave.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                doSaveContact ();
            }
        });
        lvContact.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener () {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                selectedContact = arrContact.get (arg2);
                return false;
            }
        });
    }

    public void doSaveContact() {
        MyContact ct = new MyContact ();
        ct.setName (editName.getText () + "");
        ct.setPhone (editPhone.getText () + "");
        arrContact.add (ct);
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu (menu, v, menuInfo);
        getMenuInflater ().inflate (R.menu.phonecontextmenu, menu);
        menu.setHeaderTitle ("Call- Sms");
        menu.getItem (0).setTitle ("Call to " + selectedContact.getPhone ());
        menu.getItem (1).setTitle ("Send sms to " + selectedContact.getPhone ());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //kiểm tra xem Menu Item nào được chọn
        switch (item.getItemId ()) {
            case R.id.mnuCall:
                doMakeCall ();
                break;
            case R.id.mnuSms:
                doMakeSms ();
                break;
            case R.id.mnuRemove:
                arrContact.remove (selectedContact);
                adapter.notifyDataSetChanged ();
                break;
        }
        return super.onContextItemSelected (item);
    }

    public void doMakeCall() {
        Uri uri = Uri.parse ("tel:" + selectedContact.getPhone ());
        Intent i = new Intent (Intent.ACTION_CALL, uri);
        startActivity (i);
    }

    public void doMakeSms() {
        Intent i = new Intent (this, MySMSActivity.class);
        Bundle b = new Bundle ();
        b.putSerializable ("CONTACT", selectedContact);
        i.putExtra ("DATA", b);
        startActivity (i);
    }
}
