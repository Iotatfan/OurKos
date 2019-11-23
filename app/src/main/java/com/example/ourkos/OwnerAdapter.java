package com.example.ourkos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {
    private ArrayList<Kost> data;
    private ArrayList<String> img;
    private Context context;
    public OwnerAdapter(Context context, ArrayList<Kost> input,ArrayList<String> imag){
        this.data=input;
        this.img=imag;
        this.context=context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtnama;
        public ImageView imgfoto;

        public ViewHolder(View v){
            super(v);
            txtnama=(TextView)v.findViewById(R.id.namak);
            imgfoto=(ImageView)v.findViewById(R.id.fotok);
        }
    }
    @Override
    public OwnerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_owner, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        holder.txtnama.setText(data.get(position).getNamaKost());
        Glide.with(context).load(img.get(position)).into(holder.imgfoto);
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return data.size();
    }
}
