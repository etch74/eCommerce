package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.example.ecommerceapp.Currents.Current;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettinsActivity extends AppCompatActivity {

private CircleImageView profileImageView;
private EditText fullNameEditText, userPhoneEditText, addressEditText;
private TextView profileChangeTextBtn, closeTextBtn, saveTextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);

    profileImageView= (CircleImageView) findViewById(R.id.settings_profile_image);
    fullNameEditText= (EditText) findViewById(R.id.settings_full_name);
    userPhoneEditText= (EditText) findViewById(R.id.settings_phone_number);
    addressEditText= (EditText) findViewById(R.id.settings_address);
    profileChangeTextBtn= (TextView) findViewById(R.id.profile_image_change_btn);
    closeTextBtn= (TextView) findViewById(R.id.close_settings_btn);
    saveTextButton= (TextView) findViewById(R.id.update_account_settings_btn);
    userInfoDisplay(profileImageView,fullNameEditText,userPhoneEditText,addressEditText);


    }

    private void userInfoDisplay(CircleImageView profileImageView, EditText fullNameEditText, EditText userPhoneEditText, EditText addressEditText)
    {

        DatabaseReference UsersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(Current.currentOnlineUser.getPhone());
    }
}