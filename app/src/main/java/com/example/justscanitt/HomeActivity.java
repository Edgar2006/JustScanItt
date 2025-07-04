package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
    }

    public void onCLickScan(View view) {
        Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
        startActivity(intent); // Start the activity
    }
    public void onClickTranslate(View view){
        Intent intent = new Intent(HomeActivity.this, TestTranslate.class);
        startActivity(intent);
    }


}