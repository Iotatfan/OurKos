package com.example.ourkos;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private Context context;
    private List<Message> messages;
    public ChatAdapter(Context context,List<Message> pesan){
        this.context=context;
        this.messages=pesan;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtnama;
        public TextView txtpesan;
        public TextView txtwaktu;
        public CircleImageView foto;
        public LinearLayout parentLayout;

        public ViewHolder(View v) {
            super(v);
            txtnama = v.findViewById(R.id.namaorang);
            txtpesan = v.findViewById(R.id.chatorang);
            txtwaktu = v.findViewById(R.id.waktuchat);
            foto = v.findViewById(R.id.fotochat);
            parentLayout = v.findViewById(R.id.parentLayout);
        }
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ChatAdapter.ViewHolder holder, final int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        holder.txtnama.setText(messages.get(position).getUser());
        holder.txtpesan.setText(messages.get(position).getContent());
        holder.txtwaktu.setText(DateFormat.format("dd/MM/yyyy", messages.get(position).getMessageTime()));
        Glide.with(context).load(messages.get(position).getImage()).into(holder.foto);
    }
    @Override
    public int getItemCount(){
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return messages.size();
    }
}
