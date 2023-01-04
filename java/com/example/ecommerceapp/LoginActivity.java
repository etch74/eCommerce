package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Currents.Current;
import com.example.ecommerceapp.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText inputNumber , inputPassword;
    private Button loginBtn;
    private ProgressDialog loadingBar;
    private String paretnDbName="Users";
    private CheckBox chkBoxRememberMe;
    private TextView imadmin,notadmin,forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn=(Button) findViewById(R.id.login_btn);
        inputNumber=(EditText) findViewById(R.id.login_phone_number);
        inputPassword=(EditText) findViewById(R.id.login_password);
        loadingBar=new ProgressDialog(this);
        chkBoxRememberMe=(CheckBox)findViewById(R.id.remember_me_chck);
        imadmin=(TextView) findViewById(R.id.im_admin);
        notadmin=(TextView)findViewById(R.id.notAdmin);
        forgotpassword=(TextView)findViewById(R.id.forget_password);
        Paper.init(this);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginUser();
            }
        });

        imadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("Login as admin");
                imadmin.setVisibility(View.INVISIBLE);
                notadmin.setVisibility(View.VISIBLE);
                paretnDbName="Admins";
            }
        });
        notadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn.setText("Login");
                imadmin.setVisibility(View.VISIBLE);
                notadmin.setVisibility(View.INVISIBLE);
                paretnDbName="Users";
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , resetPasswordActivity.class);
                startActivity(intent);

            }
        });
    }


    private void loginUser()
    {
        String phone=inputNumber.getText().toString();
        String password=inputPassword.getText().toString();
        if(TextUtils.isEmpty(phone))
        {
        Toast.makeText(this, "Please write your phone number" , Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(password))
        {
        Toast.makeText(this, "Please write your password" , Toast.LENGTH_LONG);
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            AllowAccessToAccount(phone , password);
        }
    }

    private void AllowAccessToAccount(String phone, String password)
    {

        if(chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Current.userPhone,phone);
            Paper.book().write(Current.userPass,password);



        }
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(paretnDbName).child(phone).exists())
                {
                    Users usersData=snapshot.child(paretnDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone))
                    {
                        Toast.makeText(LoginActivity.this,"tewysqu" , Toast.LENGTH_LONG);
                        if(usersData.getPassword().equals(password))
                        {
                            if(paretnDbName.equals("Admins"))
                            {
                            Toast.makeText(LoginActivity.this , "Welcome Admin, Logged in successfully" , Toast.LENGTH_LONG);
                            loadingBar.dismiss();
                            Intent intent = new Intent(LoginActivity.this , AdminActivityCategory.class);
                            startActivity(intent);
                            }
                            else if(paretnDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this , "Logged in successfully" , Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this , HomeActivity2.class);
                                Current.currentOnlineUser=usersData;
                                startActivity(intent);
                            }
                        }

                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Account with this phone number"+phone+"does not exist",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}