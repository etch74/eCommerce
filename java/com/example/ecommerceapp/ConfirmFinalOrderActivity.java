package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Currents.Current;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private EditText name, phone;
    private TextView address, city;
    private Button confirm , getAddress;
    private String Address;


    private String totalAmount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);
        name = (EditText) findViewById(R.id.shippment_name);
        phone = (EditText) findViewById(R.id.shippment_phone);
        address = (TextView) findViewById(R.id.Address);

        confirm = (Button) findViewById(R.id.confirm_button);
        getAddress=(Button)findViewById(R.id.getAddress);
        totalAmount=getIntent().getStringExtra("Total Price");
        Address=getIntent().getStringExtra("address");
        if(Address!="")
        {
            address.setText(Address);
        }
        getAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ConfirmFinalOrderActivity.this , LocationActivity.class);
                startActivity(intent);

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check()
    {
        if(TextUtils.isEmpty(name.getText().toString()))
        {
            Toast.makeText(this, "Please fill all fields" , Toast.LENGTH_LONG).show();

        }
       else if(TextUtils.isEmpty(phone.getText().toString()))
        {
            Toast.makeText(this, "Please fill all fields" , Toast.LENGTH_LONG).show();

        }
        else if(TextUtils.isEmpty(address.getText().toString()))
        {
            Toast.makeText(this, "Please fill all fields" , Toast.LENGTH_LONG).show();

        }
        else
        {
            ConfirmOrder();
        }


    }

    private void ConfirmOrder()
    {
        final String saveCurrentDate,saveCurrentTime;
        Calendar calendar= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Current.currentOnlineUser.getPhone());
        HashMap<String,Object> orderMap=new HashMap<>();
        orderMap.put("totalAmount",totalAmount);
        orderMap.put("name",name.getText().toString());
        orderMap.put("phone",phone.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);
        orderMap.put("address",Address);
        orderMap.put("state","not shipped");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Current.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "Your final order has been placed", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this , HomeActivity2.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });




    }
}