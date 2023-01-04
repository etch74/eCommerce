package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class UserCategoriesActivity extends AppCompatActivity {

    private ImageView Tshirts,female_dresses,sports,sweathers;
    private ImageView glasses,hats,purses_bags,shoes;
    private ImageView headphones,laptops,watches,mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_categories);
        Toast.makeText(this, "Hi", Toast.LENGTH_SHORT).show();
        Tshirts=(ImageView) findViewById(R.id.t_shirts_user);
        female_dresses=(ImageView) findViewById(R.id.female_dressses_user);
        sports=(ImageView) findViewById(R.id.sports_tshirt_user);
        sweathers=(ImageView) findViewById(R.id.sweather_user);
        glasses=(ImageView) findViewById(R.id.glasses_user);
        hats=(ImageView) findViewById(R.id.hats_user);
        purses_bags=(ImageView) findViewById(R.id.purses_bags_user);
        shoes=(ImageView) findViewById(R.id.shoess_user);
        headphones=(ImageView) findViewById(R.id.headphones_user);
        laptops=(ImageView) findViewById(R.id.laptops_user);
        watches=(ImageView) findViewById(R.id.watches_user);
        mobile=(ImageView) findViewById(R.id.mobiles_user);
        Tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "tShirts");
                startActivity(intent);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Sports tShirts");
                startActivity(intent);
            }
        });
        female_dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Female Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Sweathers");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Glasses");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Hats Caps");
                startActivity(intent);
            }
        });

        purses_bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Wallets Bags Purses");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Shoes");
                startActivity(intent);
            }
        });
        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Headphones HandFress");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Watches");
                startActivity(intent);
            }
        });
        mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserCategoriesActivity.this,productByCategoryActivity.class);
                intent.putExtra("category", "Mobile Phones");
                startActivity(intent);
            }
        });

    }

}