package com.example.justscanitt;

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

    EditText ProductBioEditText, ProductNameEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ProductNameEditText.findViewById(R.id.ProductName);
        ProductBioEditText.findViewById(R.id.ProductBio);

    }

    public void OnClickSetImage(View view) {
    }

    public void OnClickSave(View view) {
        ProductBio productBio = new ProductBio(User.EMAIL, User.NAME, ProductNameEditText.getText().toString(), "noImage", ProductBioEditText.getText().toString(),User.BARCODE);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_bio").child(User.BARCODE);
        reference.setValue(productBio);
    }
}