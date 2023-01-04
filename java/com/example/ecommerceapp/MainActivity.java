package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.Currents.Current;
import com.example.ecommerceapp.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button joinNowBtn , loginBtn;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joinNowBtn=(Button) findViewById(R.id.main_join_now_btn);
        loginBtn=(Button) findViewById(R.id.main_login_btn);
        Paper.init(this);
        loadingBar=new ProgressDialog(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });
        joinNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });
        String userPhone=Paper.book().read(Current.userPhone);
        String userPass=Paper.book().read(Current.userPass);
        if(userPhone!="" && userPass!="")
        {
         if(!TextUtils.isEmpty(userPhone) && !TextUtils.isEmpty(userPass)){
             AllowAccess(userPass , userPhone);
             loadingBar.setTitle("Already Logged in");
             loadingBar.setMessage("Please wait...");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();

         }
        }

    }

    private void AllowAccess(String password, String phone)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(phone).exists())
                {
                    Users usersData=snapshot.child("Users").child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this , "Logged in successfully" , Toast.LENGTH_LONG).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this , HomeActivity2.class);
                           Current.currentOnlineUser=usersData;
                            startActivity(intent);
                        }

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Account with this phone number"+phone+"does not exist",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}