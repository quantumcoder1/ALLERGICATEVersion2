package com.example.allergicateversion2;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private List<MenuItem> menuItems;
    private Context context;

    public MenuItemAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        //LinearLayout layout;
        ConstraintLayout layout;

        TextView description;

        LinearLayout linearLayoutForIcons;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.menuItemTitle);
            description = itemView.findViewById(R.id.menuDescription);
            layout = itemView.findViewById(R.id.menuItemLayout);
            linearLayoutForIcons = itemView.findViewById(R.id.linearLayoutForIcons);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);
        holder.title.setText(menuItem.getTitle());
        holder.description.setText(menuItem.getDescription());
        holder.title.setTextSize(20);
        holder.title.setBackgroundColor(Color.parseColor("#12AF83"));
        holder.description.setBackgroundColor(Color.parseColor("#F39B6D"));
        for (int i : menuItem.getAllergens()){
            // Create a new ImageView
            ImageView imageView = new ImageView(holder.itemView.getContext());
            imageView.setImageResource(i);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            imageView.setLayoutParams(layoutParams);
            holder.linearLayoutForIcons.addView(imageView);
        }
        if (position % 2 == 0) {
            //holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_700));
        } else {
            //holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200));
        }
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }
}
