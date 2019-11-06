package com.example.ourkos;

public class Kost {
    private int image;
    private String namaKost;
    private String alamat;
    private int stock;
    public Kost(){

    }
    public Kost(int image,String namaKost,String alamat,int stock){
        this.image=image;
        this.namaKost=namaKost;
        this.alamat=alamat;
        this.stock=stock;
    }
    public int getImage(){
        return image;
    }
    public int getStock(){
        return stock;
    }
    public String getNamaKost(){
        return namaKost;
    }
    public String getAlamat(){
        return alamat;
    }
}