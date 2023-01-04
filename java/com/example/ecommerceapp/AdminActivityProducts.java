package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.xml.validation.Validator;

public class AdminActivityProducts extends AppCompatActivity {

    private String categoryName,Description,Price,PName,saveCurrentDate,saveCurrentTime;
    private Button addNewProduct;
    private ImageView inputImage;
   private EditText inputProductName , inputProductDescription, inputProductPrice;
    private static final int GalleryPick=1;
    private Uri ImageUri;
    private String productRandomKey,downloadImageUrL;
    private StorageReference ProductImagesRef;
    private DatabaseReference Products;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);
        categoryName=getIntent().getExtras().get("category").toString();
        ProductImagesRef= FirebaseStorage.getInstance().getReference().child("Product Images");
        Products=FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar=new ProgressDialog(this);
        addNewProduct=(Button) findViewById(R.id.add_new_product_btn);
        inputImage =(ImageView) findViewById(R.id.select_product_image);
        inputProductDescription=(EditText) findViewById(R.id.product_description);
        inputProductName=(EditText) findViewById(R.id.product_name);
        inputProductPrice=(EditText) findViewById(R.id.product_price);

        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatorProductData();
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent= new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
         ImageUri=data.getData();
         inputImage.setImageURI(ImageUri);

        }
    }

    private void ValidatorProductData()
    {
        Description=inputProductDescription.getText().toString();
        Price=inputProductPrice.getText().toString();
        PName=inputProductName.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(this,"Product image is required..",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this," Description is required..",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this," Price is required..",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(PName))
        {
            Toast.makeText(this," Name is required..",Toast.LENGTH_SHORT).show();

        }
        else
        {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Adding New Product");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());

        productRandomKey=saveCurrentDate+saveCurrentTime;

        StorageReference filePath=ProductImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+".jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message=e.toString();
                Toast.makeText(AdminActivityProducts.this,"Error:"+message ,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminActivityProducts.this,"Image Uploaded successfully" ,Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }
                        downloadImageUrL=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if(task.isSuccessful()){
                            downloadImageUrL=task.getResult().toString();
                            Toast.makeText(AdminActivityProducts.this,"Product Image save to database successfully" ,Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });



    }

    private void SaveProductInfoToDatabase() {
        HashMap<String,Object> productMap=new HashMap<>();
        productMap.put("pid" , productRandomKey);
        productMap.put("date" , saveCurrentDate);
        productMap.put("time" , saveCurrentTime);
        productMap.put("description" , Description);
        productMap.put("image" , downloadImageUrL);
        productMap.put("category" , categoryName);
        productMap.put("price" , Price);
        productMap.put("pname" , PName);

        Products.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminActivityProducts.this , AdminActivityCategory.class);
                            startActivity(intent);
                            loadingBar.dismiss();
                            Toast.makeText(AdminActivityProducts.this,"Product is added successfully.." ,Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message=task.getException().toString();
                            Toast.makeText(AdminActivityProducts.this,"ERROR: "+ message ,Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


}