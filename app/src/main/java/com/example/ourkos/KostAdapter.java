package com.example.ourkos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class KostAdapter extends ArrayAdapter<Kost> {
    List<Kost> listKost;
    Context context;
    int layout;
    private static class KostHolder{
        ImageView image;
        TextView txtnama;
        TextView txtalamat;
        TextView txtstock;
    }
    public KostAdapter(Context context,int layout,List<Kost> listKost){
        super(context,layout,listKost);
        this.context=context;
        this.layout=layout;
        this.listKost=listKost;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View root=convertView;
        KostHolder holder;
        if(root==null){
            LayoutInflater v1=((Activity)context).getLayoutInflater();
            root=v1.inflate(layout,parent,false);
            holder= new KostHolder();
            holder.image=(ImageView)root.findViewById(R.id.foto);
            holder.txtnama=(TextView)root.findViewById(R.id.namakost);
            holder.txtalamat=(TextView)root.findViewById(R.id.alamatkost);
            holder.txtstock=(TextView)root.findViewById(R.id.stockkost);
            root.setTag(holder);
        }
        else{
            holder=(KostHolder)root.getTag();
        }
        Kost kost=listKost.get(position);
        holder.image.setImageResource(kost.getImage());
        holder.txtnama.setText(kost.getNamaKost());
        holder.txtalamat.setText(kost.getAlamat());
        holder.txtstock.setText("The remaining room : " + String.valueOf(kost.getStock()));
        return root;
    }
}