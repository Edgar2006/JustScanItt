package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.justscanitt.Class.User;

public class HomeActivity extends AppCompatActivity {

    TextView user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        user = findViewById(R.id.user_name);
        user.setText(User.NAME);
    }

    public void onCLickScan(View view) {
        Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
        startActivity(intent); // Start the activity

    }
    @Override
    public void onBackPressed() {
        finish();


    }

}