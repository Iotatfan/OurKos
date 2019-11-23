package com.example.ourkos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.recyclerview.widget.RecyclerView;

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

        public ViewHolder(View v){
            super(v);
            txtnama=(TextView)v.findViewById(R.id.namakost);
            txtalamat=(TextView)v.findViewById(R.id.alamatkost);
            txtharga=(TextView)v.findViewById(R.id.hargakost);
            txtstock=(TextView)v.findViewById(R.id.stockkost);
            txtjenis=(TextView)v.findViewById(R.id.jeniskost);
            imgfoto=(ImageView)v.findViewById(R.id.foto);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        holder.txtnama.setText(data.get(position).getNamaKost());
        holder.txtalamat.setText(data.get(position).getAlamat());
        holder.txtstock.setText("Ruangan tersisa : " + data.get(position).getStock());
        holder.txtharga.setText("Rp. " + data.get(position).getHargabulanan());
        holder.txtjenis.setText(data.get(position).getJenis());
        Glide.with(context).load(img.get(position)).into(holder.imgfoto);
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return data.size();
    }
}
