package com.example.ourkos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.ViewHolder> {
    private ArrayList<Kost> data;
    private ArrayList<String> img;
    private Context context;

    public OwnerAdapter(Context context, ArrayList<Kost> input, ArrayList<String> imag){
        this.data = input;
        this.img = imag;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtnama, alamaat, region;
        public ImageView imgfoto;
        public LinearLayout parentLayout;

        public ViewHolder(View v){
            super(v);
            txtnama = v.findViewById(R.id.namak);
            imgfoto = v.findViewById(R.id.fotok);
            alamaat = v.findViewById(R.id.alamatKos);
            region = v.findViewById(R.id.regiotKos);
            parentLayout = v.findViewById(R.id.parentLayout);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtnama.setText(data.get(position).getNamaKost());
        holder.alamaat.setText(data.get(position).getAlamat());
        holder.region.setText(data.get(position).getRegion());
        if(position < img.size()) {
//            System.out.println(position);
//            System.out.println(img.size());
            Glide.with(context).load(img.get(position)).into(holder.imgfoto);
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KosActivity.class);
                intent.putExtra("image_url", img.get(position));
                intent.putExtra("name", data.get(position).getNamaKost());
                intent.putExtra("hargaHarian", data.get(position).getHargaharian());
                intent.putExtra("hargaMingguan", data.get(position).getHargamingguan());
                intent.putExtra("hargaBulanan", data.get(position).getHargabulanan());
                intent.putExtra("hargaTahunan", data.get(position).getHargatahunan());
                intent.putExtra("stokKamar", data.get(position).getStock());
                intent.putExtra("ac", data.get(position).getHasAC());
                intent.putExtra("wifi", data.get(position).getHasListrik());
                intent.putExtra("listrik", data.get(position).getHasListrik());
                intent.putExtra("air", data.get(position).getHasAir());
                intent.putExtra("kamarMandiDalam", data.get(position).getHasKamarMandiDalam());
                intent.putExtra("kasur", data.get(position).getHasKasur());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return data.size();
    }
}
