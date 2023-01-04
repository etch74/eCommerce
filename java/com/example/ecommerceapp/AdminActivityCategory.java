package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminActivityCategory extends AppCompatActivity {

    private ImageView Tshirts,female_dresses,sports,sweathers;
    private ImageView glasses,hats,purses_bags,shoes;
    private ImageView headphones,laptops,watches,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        Tshirts=(ImageView) findViewById(R.id.t_shirts);
        female_dresses=(ImageView) findViewById(R.id.female_dressses);
        sports=(ImageView) findViewById(R.id.sports_tshirt);
        sweathers=(ImageView) findViewById(R.id.sweather);
        glasses=(ImageView) findViewById(R.id.glasses);
        hats=(ImageView) findViewById(R.id.hats);
        purses_bags=(ImageView) findViewById(R.id.purses_bags);
        shoes=(ImageView) findViewById(R.id.shoess);
        headphones=(ImageView) findViewById(R.id.headphones);
        laptops=(ImageView) findViewById(R.id.laptops);
        watches=(ImageView) findViewById(R.id.watches);
        mobile=(ImageView) findViewById(R.id.mobiles);


        Tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Sports tShirts");
                startActivity(intent);
            }
        });
        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });

        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Headphones HandFress");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminActivityCategory.this,AdminActivityProducts.class);
                intent.putExtra("category", "Mobile Phones");
                startActivity(intent);
            }
        });
    }
}