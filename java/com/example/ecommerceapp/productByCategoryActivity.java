package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.Model.Products;
import com.example.ecommerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;

public class productByCategoryActivity extends AppCompatActivity {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_category);
        category=getIntent().getExtras().get("category").toString();
        recyclerView=(RecyclerView) findViewById(R.id.recycler_menu_user);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ProductsRef= FirebaseDatabase.getInstance().getReference().child("Products");
    }
    protected void onStart() {
        super.onStart();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("category").startAt(category).endAt(category),Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder>adapter=
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int i, @NonNull Products model) {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price= " + model.getPrice() + " Egp");
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(productByCategoryActivity.this , ProductDetailsActivity.class);
                                intent.putExtra("pid",model.getPid());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.products_items_layout , parent,false);
                        ProductViewHolder holder=new ProductViewHolder(view);
                        return holder;
                    }
                } ;
        recyclerView.setAdapter(adapter);
        adapter.startListening();
}
}