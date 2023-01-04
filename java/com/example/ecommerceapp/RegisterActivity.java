package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button createAccountbtn;
    private EditText inputName , inputPhoneNumber, inputPassword , inputans;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createAccountbtn=(Button) findViewById(R.id.register_btn);
        inputName=(EditText) findViewById(R.id.register_name);
        inputPassword=(EditText) findViewById(R.id.register_password);
        inputPhoneNumber=(EditText) findViewById(R.id.register_phonenumber);
        inputans=(EditText)findViewById(R.id.register_question);
        loadingBar=new ProgressDialog(this);
        createAccountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount(){
        String name=inputName.getText().toString();
        String phone=inputPhoneNumber.getText().toString();
        String password=inputPassword.getText().toString();
        String answer=inputans.getText().toString();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your name" , Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please write your phone number" , Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your password" , Toast.LENGTH_LONG);
        }
        else if(TextUtils.isEmpty(answer)){
            Toast.makeText(this, "Please write your password" , Toast.LENGTH_LONG);
        }
        else{
            loadingBar.setTitle("Create Accouunt");
            loadingBar.setMessage("Please wait");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber(name , phone , password ,answer);

        }
    }

    private void ValidatePhoneNumber(String name, String phone, String password , String answer)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object> userDataMap=new HashMap<>();
                    userDataMap.put("phone",phone);
                    userDataMap.put("password",password);
                    userDataMap.put("name",name);
                    userDataMap.put("answer",answer);
                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this , "Congratulations, Your account has been created" , Toast.LENGTH_LONG);
                                        Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this , "Error: please try again" , Toast.LENGTH_LONG);

                                    }

                                }
                            });
                }
                else{
                    Toast.makeText(RegisterActivity.this , "This" + phone+ "already exist" , Toast.LENGTH_LONG);
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this , "try another phone number" , Toast.LENGTH_LONG);
                    Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}