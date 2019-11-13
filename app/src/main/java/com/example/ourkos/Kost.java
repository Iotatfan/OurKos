package com.example.ourkos;

public class Kost {
    public int image;
    public String namaKost;
    public String alamat;
    public int stock;
    public int harga;
    public String region;
    public String jenis;

    public Kost() {

    }

    public Kost(int image, String namaKost, String alamat, int stock,int harga,String region,String jenis) {
        this.image = image;
        this.namaKost = namaKost;
        this.alamat = alamat;
        this.stock = stock;
        this.harga=harga;
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
        return harga;
    }
    public String getJenis(){
        return jenis;
    }
    public String getRegion(){
        return region;
    }
}
