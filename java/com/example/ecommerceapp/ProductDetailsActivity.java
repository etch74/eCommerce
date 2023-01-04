package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import com.example.ecommerceapp.Currents.Current;
import com.example.ecommerceapp.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shawnlin.numberpicker.NumberPicker;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addtocartbtn;
    private ImageView productImage;
    private NumberPicker numberButton;
    private TextView productPrice , productDescription , productName , productquantity;
    private String productId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productId=getIntent().getStringExtra("pid");

        addtocartbtn=(Button) findViewById(R.id.pd_add_to_cart_button);
        numberButton=(NumberPicker) findViewById(R.id.number_counter);
        productImage=(ImageView) findViewById(R.id.product_image_details);
        productDescription=(TextView) findViewById(R.id.product_description_details);
        productPrice=(TextView) findViewById(R.id.product_price_details);
        productName=(TextView) findViewById(R.id.product_name_details);
        productquantity=(TextView) findViewById(R.id.cart_product_quantity);
        numberButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        getProductDetails(productId);

        addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });

    }

    private void addingToCartList()
    {
        String saveCurrentDate,saveCurrentTime;
       Calendar calendar= Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",String.format(String.valueOf(numberButton.getValue())));

        cartListRef.child("User View").child(Current.currentOnlineUser
                .getPhone()).child("Products").child(productId)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            cartListRef.child("Admin View").child(Current.currentOnlineUser
                                            .getPhone()).child("Products").child(productId)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(ProductDetailsActivity.this , "Added to cart" , Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(ProductDetailsActivity.this , HomeActivity2.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductDetails(String productId) {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Products products=snapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}