package com.example.justscanitt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justscanitt.Class.ProductBio;
import com.example.justscanitt.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ReadActivity extends AppCompatActivity {
    // UI components

    // Firebase references
    private DatabaseReference referenceComment,referenceProduct;

    // Product info
    private String longText,barCode;
    private TextView productName,bioText,companyName,barCodeTextView;
    private ImageView productImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        init();
        //getDataFromDataBase();
        getDataProductDataBase();

    }
    // Initialization of views and Firebase references
    private void init(){
        barCodeTextView = findViewById(R.id.Barcode);
        companyName = findViewById(R.id.CompanyName);

        Intent intent = getIntent();
        barCode = intent.getStringExtra("barCode");
        // Firebase paths
        referenceComment = FirebaseDatabase.getInstance().getReference("Product").child(barCode);
        referenceProduct = FirebaseDatabase.getInstance().getReference("Product_bio").child(barCode);
        productName = findViewById(R.id.ProductName);
        bioText = findViewById(R.id.ProductBio);
        productImageView = findViewById(R.id.ProductImages);

    }

    public void onClickComment(View view){
        Intent intent = new Intent(ReadActivity.this, NewCommentActivity.class);
        intent.putExtra("barCode", barCode);
        startActivity(intent);
    }


    // Load product information
    private void getDataProductDataBase(){
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    ProductBio productBio = snapshot.getValue(ProductBio.class);
                    barCodeTextView.setText(barCode);
                    companyName.setText(productBio.getCompanyName());
                    productName.setText(productBio.getProductName());
                    longText = productBio.getBio();
                    bioText.setText(longText);
                    // Load product image if exists
                    if(!Objects.equals(productBio.getImageRef(), "noImage")) {
                        Glide.with(getApplicationContext()).load(productBio.getImageRef()).into(productImageView);
                    }
                    // Load company name from user's info
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(productBio.getCompanyEmail());
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            User userNameAndImageUnit = snapshot.getValue(User.class);
                            companyName.setText(userNameAndImageUnit.getName());
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                catch (Exception e){
                    firstBio(); // If product is not found
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        referenceProduct.addValueEventListener(eventListener);

    }

    // If product bio is not found — prompt user to add one
    public void firstBio() {
        String title = "Product is not found";
        String message = "You can add product";
        String button1String = "Add now";
        String button2String = "Go back";
        AlertDialog.Builder builder = new AlertDialog.Builder(ReadActivity.this);
        builder.setTitle(title);  // заголовок
        builder.setMessage(message); // сообщение
        builder.setPositiveButton(button1String, (dialog, id) -> {
            Intent intent = new Intent(ReadActivity.this, AddProductActivity.class);
            intent.putExtra("barCode",barCode);
            startActivity(intent);
        });
        builder.setNegativeButton(button2String, (dialog, id) -> finish());
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }


    // Translate bio using Translations class
    public void onClickTranslate(View view) {
    }


}