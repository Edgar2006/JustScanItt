package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.justscanitt.Class.ProductBio;
import com.example.justscanitt.Class.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {

    public EditText productBioEditText, productNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        productNameEditText = findViewById(R.id.newProductName);
        productBioEditText = findViewById(R.id.newProductBio);
    }

    public void OnClickSetImage(View view) {
    }

    public void OnClickSave(View view) {
        ProductBio productBio = new ProductBio(User.EMAIL, User.NAME, productNameEditText.getText().toString(), "noImage", productBioEditText.getText().toString(),User.BARCODE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_bio").child(User.BARCODE);
        reference.setValue(productBio);
        Intent intent = new Intent(AddProductActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}