package com.example.ourkos;

import java.util.ArrayList;

public class Kost {
    public int image;
    public ArrayList<String> imageb;
    public ArrayList<String> imagek;
    public ArrayList<String> imagem;
    public String namaKost;
    public String alamat;
    public int stock;
    public int hargab;
    public int hargah;
    public int hargam;
    public int hargat;
    public String region;
    public String jenis;

    public Kost() {

    }

    public Kost(int image,ArrayList<String> imageb,ArrayList<String> imagek,ArrayList<String> imagem, String namaKost, String alamat, int stock,int hargab,int hargah,int hargam,int hargat,String region,String jenis) {
        this.image = image;
        this.imageb=imageb;
        this.imagek=imagek;
        this.imagem=imagem;
        this.namaKost = namaKost;
        this.alamat = alamat;
        this.stock = stock;
        this.hargab=hargab;
        this.hargah=hargah;
        this.hargam=hargam;
        this.hargat=hargat;
        this.region=region;
        this.jenis=jenis;
    }

    public int getImage() {
        return image;
    }

    public int getStock() {
        return stock;
    }

    public String getNamaKost() {
        return namaKost;
    }

    public String getAlamat() {
        return alamat;
    }
    public int getHarga(){
        return hargab;
    }
    public String getJenis(){
        return jenis;
    }
    public String getRegion(){
        return region;
    }
}
