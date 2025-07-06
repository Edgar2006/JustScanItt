package com.example.justscanitt;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.justscanitt.Class.Comment;
import com.example.justscanitt.Class.ProductBio;
import com.example.justscanitt.Class.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;

public class ReadActivity extends AppCompatActivity {
    // UI components
    private RecyclerView listView;
    private CommentFragment viewAdapter;
    private ArrayList<Comment> listData;

    private RatingBar ratingBar;
    private int ratingUserCount;
    private float ratingSum;

    // Firebase references
    private DatabaseReference referenceComment,referenceProduct;

    // Product info
    private String longText,barCode;
    private TextView productName,bioText,companyName,barCodeTextView;
    private ImageView productImageView;
    TextView ratingBarScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        init();
        getDataFromDataBase();
        getDataProductDataBase();
        viewAdapter.notifyDataSetChanged();

    }
    // Initialization of views and Firebase references
    private void init(){
        ratingSum = 0;
        ratingUserCount = 0;
        barCodeTextView = findViewById(R.id.Barcode);
        companyName = findViewById(R.id.CompanyName);
        ratingBar = findViewById(R.id.rating_bar);
        ratingBarScore = findViewById(R.id.rating_bar_score);
        barCode = User.BARCODE;
        // Firebase paths
        referenceComment = FirebaseDatabase.getInstance().getReference("Product").child(barCode);
        referenceProduct = FirebaseDatabase.getInstance().getReference("Product_bio").child(barCode);
        productName = findViewById(R.id.ProductName);
        bioText = findViewById(R.id.ProductBio);
        productImageView = findViewById(R.id.ProductImages);

        listView = findViewById(R.id.rec_view);
        listData = new ArrayList<>();
        viewAdapter = new CommentFragment(this,listData);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(viewAdapter);

    }


    // Load user comments from Firebase
    private void getDataFromDataBase(){
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(listData.size() > 0){
                    listData.clear();
                    ratingSum = 0;
                    ratingUserCount = 0;
                }
                for(DataSnapshot ds : snapshot.getChildren()){
                    Comment messenger = ds.getValue(Comment.class);
                    listData.add(messenger);
                    ratingSum += messenger.getRatingBarScore();
                    ratingUserCount++;
                }
                float rating_ = 0F;
                if(ratingUserCount != 0 && ratingSum != 0){
                    rating_ =  ratingSum / ratingUserCount;

                }
                ratingBar.setRating(rating_);
                ratingBarScore.setText(ROUND(rating_) + "  (" + ratingUserCount + ')');
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        referenceComment.addValueEventListener(eventListener);
    }



    // Load product information
    private void getDataProductDataBase(){
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        ProductBio productBio = snapshot.getValue(ProductBio.class);
                        barCodeTextView.setText(barCode);
                        companyName.setText(productBio.getCompanyName());
                        productName.setText(productBio.getProductName());
                        longText = productBio.getBio();
                        bioText.setText(longText);
                        // Load product image if exists
                        if(!Objects.equals(productBio.getImageRef(), "noImage")) {
                            Glide.with(getApplicationContext()).load(productBio.getImageRef()).into(productImageView);
                        }
                    } else {
                        firstBio(); // If product is not found
                    }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        referenceProduct.addValueEventListener(eventListener);

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
            intent.putExtra("barCode",barCode);
            startActivity(intent);
        });
        builder.setNegativeButton(button2String, (dialog, id) -> finish());
        builder.setCancelable(false);
        builder.create();
        builder.show();
    }


    public void onClickTranslate(View view) {
        String originalText = bioText.getText().toString();
            LanguageIdentifier languageIdentifier = LanguageIdentification.getClient();

            languageIdentifier.identifyLanguage(originalText)
                    .addOnSuccessListener(languageCode -> {
                        if (languageCode.equals("und")) {
                            Toast.makeText(this, "Cannot identify language", Toast.LENGTH_SHORT).show();
                        } else {
                            // Now translate from detected language to English
                            TranslatorOptions options = new TranslatorOptions.Builder()
                                    .setSourceLanguage(languageCode)
                                    .setTargetLanguage(TranslateLanguage.ENGLISH)
                                    .build();

                            final Translator translator = Translation.getClient(options);

                            translator.downloadModelIfNeeded()
                                    .addOnSuccessListener(unused ->
                                            translator.translate(originalText)
                                                    .addOnSuccessListener(translatedText -> {
                                                        bioText.setText(translatedText);
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        Toast.makeText(this, "Translation failed", Toast.LENGTH_SHORT).show();
                                                    }))
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Model download failed", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(
                            e -> Toast.makeText(this, "Language detection failed", Toast.LENGTH_SHORT).show()
                    );

    }


    public void onClickComment(View view) {
        Intent intent = new Intent(ReadActivity.this, NewCommentActivity.class);
        startActivity(intent);
    }

    public static String ROUND(Float v) {
        BigDecimal bigDecimal = new BigDecimal(v).setScale(1, RoundingMode.DOWN);
        return bigDecimal.toString();
    }
}