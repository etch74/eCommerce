package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Products;
import com.example.ecommerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchProductsActivity extends AppCompatActivity {

    private Button searchbtn , voice;
    private EditText inputText;
    private RecyclerView searchlist;
    private String Searchinput="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        searchbtn=(Button) findViewById(R.id.search_button);
        voice=(Button) findViewById(R.id.search_voice_btn);
        inputText=(EditText) findViewById(R.id.search_text);
        searchlist=(RecyclerView) findViewById(R.id.search_list);
        searchlist.setLayoutManager(new LinearLayoutManager(SearchProductsActivity.this));
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVoiceDialog();
                onStart();
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Searchinput=inputText.getText().toString();
                onStart();
            }
        });


    }

    private void openVoiceDialog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,200);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== 200 && resultCode == RESULT_OK )
        {

            ArrayList<String> arrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice = arrayList.get(0);
            Toast.makeText(this,voice,Toast.LENGTH_SHORT).show();

//            searchView.setQuery(voice,true);
            inputText.setText(String.valueOf(voice));
            Searchinput=(String.valueOf(voice));

        }
        else{

            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onStart()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Products");
        super.onStart();
        FirebaseRecyclerOptions<Products> options=new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(reference.orderByChild("pname").startAt(Searchinput),Products.class).build();
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
                                Intent intent=new Intent(SearchProductsActivity.this , ProductDetailsActivity.class);
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
        searchlist.setAdapter(adapter);
        adapter.startListening();

    }
}