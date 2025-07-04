package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReadActivity extends AppCompatActivity {

    TextView companyName, productName, productBio, barcode;
    ImageView productImages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);
        init();

    }

    public void getProductBio()
    {


    }






    public void init()
    {
        companyName = findViewById(R.id.CompanyName);
        productName = findViewById(R.id.ProductName);
        productBio = findViewById(R.id.ProductBio);
        barcode = findViewById(R.id.Barcode);
        productImages = findViewById(R.id.ProductImages);

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
            startActivity(intent);
        });
        builder.setNegativeButton(button2String, (dialog, id) -> finish());
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }

}