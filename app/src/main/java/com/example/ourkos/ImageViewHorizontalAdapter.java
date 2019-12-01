package com.example.ourkos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageViewHorizontalAdapter extends RecyclerView.Adapter<ImageViewHorizontalAdapter.ViewHolder> {
    private List<String> img;
    private Context context;
    public ImageViewHorizontalAdapter(Context context, List<String> img){
        this.context=context;
        this.img=img;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgfoto;
        public LinearLayout parentLayout;

        public ViewHolder(View v) {
            super(v);
            imgfoto=v.findViewById(R.id.gambar);
            parentLayout=v.findViewById(R.id.parentLayout);
        }
    }
    @Override
    public ImageViewHorizontalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_imagehorizontal, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(img.get(position)).into(holder.imgfoto);
    }

    @Override
    public int getItemCount(){
        return img.size();
    }
}
