package com.example.realestateproject.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realestateproject.Contact.MyContact;
import com.example.realestateproject.R;

public class MySMSActivity extends AppCompatActivity {

    Button btnSendSMS;
    EditText editContent;
    TextView txtSendTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_my_sms);
        btnSendSMS =(Button) findViewById(R.id.btnSendSms);
        editContent =(EditText) findViewById(R.id.editSMS);
        txtSendTo=(TextView) findViewById(R.id.txtSendTo);
        Intent i =getIntent();
        Bundle b=i.getBundleExtra("DATA");
        final MyContact c=(MyContact) b.getSerializable("CONTACT");
        btnSendSMS.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                sendSms(c);
            }
        });
        txtSendTo.setText("Send to : "+c.getPhone());
    }
    public void sendSms(MyContact c)
    {
        final SmsManager sms = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
        final PendingIntent pendingMsgSent =
                PendingIntent.getBroadcast(this, 0, msgSent, 0);
        registerReceiver(new BroadcastReceiver () {
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg="Send OK";
                if (result != Activity.RESULT_OK) {
                    msg="Send failed";
                }
                Toast.makeText(MySMSActivity.this, msg,
                        Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter ("ACTION_MSG_SENT"));
        sms.sendTextMessage(c.getPhone(), null, editContent.getText()+"",
                pendingMsgSent, null);
        finish();
    }
}
