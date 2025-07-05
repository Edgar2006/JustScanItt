package com.example.justscanitt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.justscanitt.Class.ProductBio;
import com.example.justscanitt.Class.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AddProductActivity extends AppCompatActivity {

    public TextInputLayout productBioEditText, productNameEditText;
    private ImageView imageView;
    private Uri uploadUri;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);
        productNameEditText = findViewById(R.id.ProductName);
        productBioEditText = findViewById(R.id.comment);
        imageView = findViewById(R.id.comment_image);
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageProduct");
        // Get data passed from the previous activity
        Intent intent = getIntent();
        if (intent != null) {
            try {
                String url = intent.getStringExtra("URL");
                if (!url.equals("noImage")) {
                    // Load the image from the given URL using Glide
                    Glide.with(getApplicationContext()).load(url).into(imageView);
                }
            }catch (Exception e){
                // Ignore errors if the image is not available
            }

        }
    }

    public void onClickSubmit(View view){
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            // Compress the image to reduce size
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            // Upload the image using the barcode as filename
            StorageReference mRef = mStorageRef.child(User.BARCODE);
            final UploadTask uploadTask = mRef.putBytes(byteArray);
            // After upload, get the download URL and call sendToData
            Task<Uri> task = uploadTask.continueWithTask(task1 -> mRef.getDownloadUrl()).addOnCompleteListener(task12 -> {
                uploadUri = task12.getResult();
                sendToData();
            });
        }catch (Exception e){
            // If image is not selected, skip image upload and continue
            sendToData();
        }
    }
    // Save data to Firebase after the image is uploaded
    private void sendToData(){
        String productNameString = productNameEditText.getEditText().getText().toString();
        String bioLong = productBioEditText.getEditText().getText().toString();
        // Make sure all fields are filled and image is uploaded
        if(!(productNameString.isEmpty() && bioLong.isEmpty() && uploadUri == null)){
            String uri = uploadUri.toString();
            // Create ProductBio object to store in database
            ProductBio productBio = new ProductBio(User.EMAIL_CONVERT, User.NAME, productNameString, uri, bioLong, User.BARCODE);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product_bio").child(User.BARCODE);
            reference.setValue(productBio);
            Intent intent = new Intent(AddProductActivity.this, ReadActivity.class);
            startActivity(intent);
            finish();

        }
    }


    // Save ProductBio to Firebase and open Read activity
    public void OnClickSetImage(View view){

        ImagePicker.with(this)
                .crop()
                .compress(256)
                .maxResultSize(256, 256)
                .start();
    }

    // Receive the selected image and show it in imageView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Glide.with(getApplicationContext()).load(data.getData()).into(imageView);
    }
}