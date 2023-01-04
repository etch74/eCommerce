package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Currents.Current;
import com.example.ecommerceapp.Model.Products;
import com.example.ecommerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.databinding.ActivityHome2Binding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHome2Binding binding;
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");
        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Paper.init(this);
        setSupportActionBar(binding.appBarHome2.toolbar);
        recyclerView=(RecyclerView) findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        binding.appBarHome2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent= new Intent(HomeActivity2.this , CartActivity.class);
               startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cart, R.id.nav_orders, R.id.nav_categories ,R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)this);
        View headerview=navigationView.getHeaderView(0);
        TextView userNameTextview=headerview.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerview.findViewById(R.id.user_profile_image);
        userNameTextview.setText(Current.currentOnlineUser.getName());
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               if(item.getItemId()==R.id.nav_logout){
                   Toast.makeText(HomeActivity2.this,"hsaj",Toast.LENGTH_SHORT).show();
                   Paper.book().destroy();
                   Intent intent = new Intent(HomeActivity2.this , MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
                else if(item.getItemId()==R.id.nav_cart){
                   Intent intent= new Intent(HomeActivity2.this , CartActivity.class);
                   startActivity(intent);
                }
                else if(item.getItemId()==R.id.nav_search)
                {
                    Intent intent= new Intent(HomeActivity2.this , SearchProductsActivity.class);
                    startActivity(intent);
                }
               else if(item.getItemId()==R.id.nav_categories)
               {
                   Intent intent= new Intent(HomeActivity2.this , UserCategoriesActivity.class);
                   startActivity(intent);
               }
               else if(item.getItemId()==R.id.nav_chart)
               {
                   Intent intent= new Intent(HomeActivity2.this , Chart.class);
                   startActivity(intent);
               }

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options=
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef,Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products , ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder( @NonNull ProductViewHolder holder, int position, @NonNull Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price= " + model.getPrice() + " Egp");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(HomeActivity2.this , ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });

                    }

                    @Override
                    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout , parent,false);
                        ProductViewHolder holder=new ProductViewHolder(view);

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}