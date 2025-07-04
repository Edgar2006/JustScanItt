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
    private EditText manualInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        manualInput = findViewById(R.id.manual_input); // connect EditText from XML
    }

    // Barcode scanner triggered
    public void onCLickScanNow(View view){
        // If manual barcode entered, use it
        String manualCode = manualInput.getText().toString().trim();
        if (!manualCode.isEmpty()) {
            barCode = manualCode;
            push_activity(); // Go directly to ReadActivity
        } else {
            // Else, launch scanner
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setCaptureActivity(CaptureAct.class);
            integrator.setOrientationLocked(false);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setPrompt("Scanning Code");
            integrator.initiateScan();
        }
    }

    // Handle scanner result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                try {
                    barCode = result.getContents();
                    push_activity();
                } catch (Exception ignored) {
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Navigate to product details
    public void push_activity(){
        if (barCode == null || barCode.isEmpty()) {
            Toast.makeText(this, "No barcode provided", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Barcode: " + barCode, Toast.LENGTH_SHORT).show();
        User.BARCODE = barCode;
        Intent intent = new Intent(ScanActivity.this, ReadActivity.class);
        startActivity(intent);
    }
}
