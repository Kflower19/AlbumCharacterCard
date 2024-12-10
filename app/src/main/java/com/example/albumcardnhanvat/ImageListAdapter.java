package com.example.albumcardnhanvat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>{
    private Context context;
    private List<Integer> imageList;
    private OnImageSelectedListener listener;

    public interface OnImageSelectedListener {
        void onImageSelected(int imageRes);
    }

    public ImageListAdapter(Context context, List<Integer> imageList, OnImageSelectedListener listener) {
        this.context = context;
        this.imageList = imageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int imageRes = imageList.get(position);
        holder.imgItem.setImageResource(imageRes);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageSelected(imageRes);
            }
        });
    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;


        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
        }
    }
}
