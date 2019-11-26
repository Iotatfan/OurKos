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

import java.util.List;

public class KostAdapter extends RecyclerView.Adapter<KostAdapter.ViewHolder> {
    private List<Kost> data;
    private List<String> img;
    private Context context;
    public KostAdapter(Context context, List<Kost> input,List<String> imag){
        this.data=input;
        this.img=imag;
        this.context=context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtnama;
        public TextView txtalamat;
        public TextView txtharga;
        public TextView txtstock;
        public TextView txtjenis;
        public ImageView imgfoto;
        public LinearLayout parentLayout;

        public ViewHolder(View v){
            super(v);
            txtnama = v.findViewById(R.id.namakost);
            txtalamat = v.findViewById(R.id.alamatkost);
            txtharga = v.findViewById(R.id.hargakost);
            txtstock = v.findViewById(R.id.stockkost);
            txtjenis = v.findViewById(R.id.jeniskost);
            imgfoto = v.findViewById(R.id.foto);
            parentLayout = v.findViewById(R.id.parentLayout);
        }
    }
    @Override
    public KostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        holder.txtnama.setText(data.get(position).getNamaKost());
        holder.txtalamat.setText(data.get(position).getAlamat());
        holder.txtstock.setText("Ruangan tersisa : " + data.get(position).getStock());
        holder.txtharga.setText("Rp. " + data.get(position).getHargabulanan());
        holder.txtjenis.setText(data.get(position).getJenis());
        Glide.with(context).load(img.get(position)).into(holder.imgfoto);

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
