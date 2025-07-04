package com.example.justscanitt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.nl.translate.*;

public class TestTranslate extends AppCompatActivity {

    private TextView productDescription;
    private Button btnTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_translate); // make sure this matches your XML filename

        productDescription = findViewById(R.id.productDescription);
        btnTranslate = findViewById(R.id.btn_translate);

        // Optional: set initial text
        productDescription.setText("This product contains natural ingredients and no sugar.");

        btnTranslate.setOnClickListener(v -> {
            translateText(productDescription.getText().toString());
        });
    }

    private void translateText(String originalText) {
        TranslatorOptions options = new TranslatorOptions.Builder()
                .setSourceLanguage(TranslateLanguage.ENGLISH)
                .setTargetLanguage(TranslateLanguage.RUSSIAN)
                .build();

        final Translator translator = Translation.getClient(options);

        translator.downloadModelIfNeeded()
                .addOnSuccessListener(unused ->
                        translator.translate(originalText)
                                .addOnSuccessListener(translatedText -> {
                                    productDescription.setText(translatedText);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Translation failed", Toast.LENGTH_SHORT).show();
                                }))
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Model download failed", Toast.LENGTH_SHORT).show();
                });
    }
}
