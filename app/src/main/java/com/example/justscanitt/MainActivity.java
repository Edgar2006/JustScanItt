package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justscanitt.Class.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText email,name;
    private String emailToString, nameToString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emailInput);
        name = findViewById(R.id.nameInput);
        Log.e("1111111111111111111111","1");
    }

    public void onClickSignIn(View view){
        emailToString = email.getText().toString();
        Log.e("T", "_"+emailToString + "T");
        nameToString = name.getText().toString();
        if (!emailToString.isEmpty() && !nameToString.isEmpty()) {
            User user;
            user = new User(nameToString, emailToString);
            User.NAME = nameToString;
            User.EMAIL_CONVERT = emailToString;
            User.EMAIL = emailToString;
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(User.EMAIL_CONVERT);
            reference.setValue(user);

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent); // Start the activity
        }
    }

}