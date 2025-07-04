package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justscanitt.Class.User;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {
    private String barCode = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

    }
    public void onCLickScanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }

    // Called when scanner returns result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                try {
                    // Get scanned barcode and update input field
                    barCode = result.getContents();
                    push_activity(); // Open read screen
                }catch (Exception ignored){
                }
            }
        }else{
            // Default behavior for other activities
            super.onActivityResult(requestCode,resultCode,data);
        }

    }

    // Open Read activity to display product info by barcode
    public void push_activity(){
        Toast.makeText(this, "barCode", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ScanActivity.this, ReadActivity.class);
        User.BARCODE = barCode;
        startActivity(intent);
    }



}
