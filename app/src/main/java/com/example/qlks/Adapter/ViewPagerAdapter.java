package com.example.qlks.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.qlks.R;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private Context context;
    private int[] images;
    private int[] headings;
    private int[] descriptions;

    public ViewPagerAdapter(Context context, int[] images, int[] headings, int[] descriptions) {
        this.context = context;
        this.images = images;
        this.headings = headings;
        this.descriptions = descriptions;
    }

    @Override
    public int getItemCount() {
        return headings.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_sliderHeading.setText(headings[position]);
        holder.tv_sliderDescription.setText(descriptions[position]);
        holder.img_sliderImage.setBackgroundResource(images[position]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sliderHeading, tv_sliderDescription;
        private ImageView img_sliderImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sliderHeading = itemView.findViewById(R.id.tv_sliderHeading);
            tv_sliderDescription = itemView.findViewById(R.id.tv_sliderDescription);
            img_sliderImage = itemView.findViewById(R.id.img_sliderImage);
        }
    }
}

