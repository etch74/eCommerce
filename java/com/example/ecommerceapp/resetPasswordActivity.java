package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Currents.Current;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class resetPasswordActivity extends AppCompatActivity {


    private TextView title,titlequestion;
    private EditText phoneNumber , question2;
    private Button verifybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        title=(TextView) findViewById(R.id.title_reset);
        titlequestion=(TextView) findViewById(R.id.title_questions);

        question2=(EditText) findViewById(R.id.question_2);
        phoneNumber=(EditText) findViewById(R.id.find_phone_number);
        verifybtn=(Button) findViewById(R.id.reset_btn);
    }

    @Override
    protected void onStart() {

        super.onStart();

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();

            }
        });



    }

    private void verifyUser()
    {
       final String phone =phoneNumber.getText().toString();

        final String ans2=question2.getText().toString().toLowerCase();

        if(!phone.equals("") && !ans2.equals("")){
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if(snapshot.exists())
                    {
                        String mphone=snapshot.child("phone").getValue().toString();
                        if(snapshot.hasChild("answer"))
                        {
                            String answer2=snapshot.child("answer").getValue().toString();
                            if(!answer2.equals(ans2))
                            {
                                Toast.makeText(resetPasswordActivity.this, "Answer is not correct", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(resetPasswordActivity.this);
                                builder.setTitle("New Password");
                                final EditText newPassword= new EditText(resetPasswordActivity.this);
                                newPassword.setHint("Write new passsword");
                                builder.setView(newPassword);
                                builder.setPositiveButton("change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        if(newPassword.getText().toString().equals(""))
                                        {
                                            Toast.makeText(resetPasswordActivity.this, "Write a password", Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            ref.child("password")
                                                    .setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                Toast.makeText(resetPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(resetPasswordActivity.this,LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                     dialogInterface.cancel();
                                    }
                                });
                                builder.show();
                            }

                        }
                        else
                        {
                            Toast.makeText(resetPasswordActivity.this, "This number doesnt exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else

        {
            Toast.makeText(this, "complete Form", Toast.LENGTH_SHORT).show();
        }


    }
}