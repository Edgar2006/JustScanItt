package com.example.justscanitt;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.justscanitt.Class.Comment;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Objects;



public class CommentFragment extends RecyclerView.Adapter<CommentFragment.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Comment> messengers;

    // Constructor: initializes the adapter with context and data list
    public CommentFragment(Context context, ArrayList<Comment> messengers) {
        this.messengers = messengers;
        this.inflater = LayoutInflater.from(context);
    }

    // Called to bind data to a ViewHolder at the given position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment messenger = messengers.get(position);
        holder.arrayPosition = holder.getLayoutPosition();
        holder.email.setText(messenger.getName());
        holder.emailToString = messenger.getEmail();
        holder.name = messenger.getName();
        holder.comment.setText(messenger.getComment().trim());
        holder.database = FirebaseDatabase.getInstance();
        holder.uploadUri = messenger.getImageRef();
        holder.ratingBar.setRating(messenger.getRatingBarScore());
        holder.ratingBarScore.setText(ROUND(messenger.getRatingBarScore()));

        // Load image if available, otherwise hide the image view
        if(!Objects.equals(holder.uploadUri, "noImage")) {
            Glide.with(holder.itemView.getContext()).load(holder.uploadUri).into(holder.imageDataBase);
        }
        else{
            holder.imageDataBase.setVisibility(View.GONE);
            holder.comment.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
    }

    // Returns number of items in the list
    @Override
    public int getItemCount() {
        return messengers.size();
    }

    // ViewHolder: holds and initializes views for one list item
    public static class ViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        TextView email,comment,ratingBarScore;
        String emailToString,name,uploadUri;
        ImageView imageDataBase;
        FirebaseDatabase database;
        int size;
        int arrayPosition;
        TextView translateView;

        public ViewHolder(View view) {
            super(view);
            init(view);
            // When the translate button is clicked, trigger translation logic
            translateView.setOnClickListener(v -> {
            });

        }
        private void init(View view){
            translateView = view.findViewById(R.id.translate);
            ratingBarScore = view.findViewById(R.id.rating_bar_score);
            ratingBar = view.findViewById(R.id.rating_bar);
            email = view.findViewById(R.id.name);
            comment = view.findViewById(R.id.comment);
            imageDataBase = view.findViewById(R.id.image_data_base);
            size = 0;
        }

    }

    // Inflate layout for each list item
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_comment,parent,false);
        return new ViewHolder(view);
    }


    public static String ROUND(Float v) {
        BigDecimal bigDecimal = new BigDecimal(v).setScale(1, RoundingMode.DOWN);
        return bigDecimal.toString();
    }
}
