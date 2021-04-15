package com.example.imagegallery.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.imagegallery.R;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ImageViewHolder> {

    Activity activity;
    List<String> url_list;

    public GalleryImageAdapter(Activity activity, List<String> url_list){
        this.activity = activity;
        this.url_list = url_list;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String currentImage = url_list.get(position);
        ImageView imageView = holder.imageView;
        ProgressBar progressBar = holder.progressBar;
        Glide.with(activity).load(currentImage).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
    }

    @Override
    public int getItemCount() {
        return url_list.size() ;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ProgressBar progressBar;
        public ImageViewHolder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}


