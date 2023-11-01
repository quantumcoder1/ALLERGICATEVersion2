package com.example.allergicateversion2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewItemAdapter extends RecyclerView.Adapter<ReviewItemAdapter.ViewHolder> {
    private List<ReviewItem> reviewItems;
    private Context context;

    public ReviewItemAdapter(Context context, List<ReviewItem> reviewItems) {
        this.context = context;
        this.reviewItems = reviewItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user;
        //LinearLayout layout;
        ConstraintLayout layout;

        TextView comment;

        LinearLayout linearLayoutForRating;

        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            user = itemView.findViewById(R.id.reviewItemTitle);
            comment = itemView.findViewById(R.id.reviewDescription);
            layout = itemView.findViewById(R.id.reviewItemLayout);
            linearLayoutForRating = itemView.findViewById(R.id.linearLayoutForRating);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewItem reviewItem = reviewItems.get(position);
        holder.user.setText(reviewItem.getUser());
        holder.comment.setText(reviewItem.getComment());
        holder.user.setTextSize(20);
        holder.user.setBackgroundColor(Color.parseColor("#12AF83"));
        holder.comment.setBackgroundColor(Color.parseColor("#F39B6D"));
        // Create a new ImageView
        RatingBar ratingBar = new RatingBar(holder.itemView.getContext());
        ratingBar.setRating(reviewItem.getRating());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ratingBar.setLayoutParams(layoutParams);
        holder.linearLayoutForRating.addView(ratingBar);
    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }
}
