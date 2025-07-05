package com.example.justscanitt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.justscanitt.Class.Comment;
import com.example.justscanitt.Class.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class NewCommentActivity extends AppCompatActivity {

    private TextInputLayout comment;
    private ImageView imageView;
    private Uri uploadUri;
    private StorageReference mStorageRef;
    private RatingBar ratingBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comment);
        init();
    }

    // Find views and initialize values
    public void init(){
        // Convert current user email for Firebase-safe keys
        comment = findViewById(R.id.comment);
        // Allow pressing "done" on the keyboard
        imageView = findViewById(R.id.imageView);
        // Prepare Firebase Storage reference
        mStorageRef = FirebaseStorage.getInstance().getReference("ImageDB");
        // Get barcode passed from previous activity
        ratingBar = findViewById(R.id.ratingBar);
    }
    public void onClickSubmit(View view){
        try {
            // Convert ImageView content to bitmap
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            // Compress image to JPEG with 50% quality
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            // Set storage path: barCode + user email
            StorageReference mRef = mStorageRef.child(User.BARCODE + User.EMAIL_CONVERT);
            // Upload image bytes
            final UploadTask uploadTask = mRef.putBytes(byteArray);
            // Get download URL after upload
            Task<Uri> task = uploadTask.continueWithTask(task1 -> mRef.getDownloadUrl()).addOnCompleteListener(task12 -> {
                uploadUri = task12.getResult();
                sendToData(); // Proceed with saving comment
            });
        }catch (Exception e){
            // If image was not selected, just save comment without image
            sendToData();
        }

    }

    public void onClickChooseImage(View view){
        ImagePicker.with(this)
                .crop()            //Crop image(Optional), Check Customization for more option
                .compress(256)      //Final image size will be less than 1 MB(Optional)
                .maxResultSize(256, 256)  //Final image resolution will be less than 1080 x 1080(Optional)
                .start();
    }

    // Save comment, rating, image URL to Firebase
    public void sendToData(){
        String commentString = comment.getEditText().getText().toString();
        if(!commentString.isEmpty()){
            // Create comment/message object
            Comment messenger;
            messenger = new Comment(User.EMAIL, User.NAME, commentString,"noImage",ratingBar.getRating());
            // If an image was uploaded, attach its URL
            if(uploadUri != null){
                messenger.setImageRef(uploadUri.toString());
            }
            // Save message to product database
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Product").child(User.BARCODE).child(User.EMAIL_CONVERT);
            reference.setValue(messenger);
            // Show success and go to Read activity
            Intent intent = new Intent(NewCommentActivity.this, ReadActivity.class);
            startActivity(intent);
            finish(); // Close current screen
        }
    }

    // When image is selected and returned to activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Load selected image into ImageView using Glide
        Glide.with(getApplicationContext()).load(data.getData()).into(imageView);
    }

}