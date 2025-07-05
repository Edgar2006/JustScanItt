package com.example.justscanitt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.justscanitt.Class.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    private String barCode = "";
    private TextInputEditText barCodeEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        barCodeEditText = findViewById(R.id.manual_input1);

    }
    public void onCLickScanNow(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning Code");
        integrator.initiateScan();
    }
    public void onCLickRead(View view){
        // Get text from barcode input field
        String barCodeText = barCodeEditText.getText().toString();
        if (barCode.isEmpty())
        {
            barCode = barCodeText;
        }
        push_activity();
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
                    barCodeEditText.setText(barCode);
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
        Intent intent = new Intent(ScanActivity.this, ReadActivity.class);
        intent.putExtra("barCode", barCode);
        User.BARCODE = barCode;
        startActivity(intent);
    }


}
