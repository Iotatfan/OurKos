package com.example.ourkos;


public class Kost {
    public String namaKost;
    public String alamat;
    public int stock;
    public int hargabulanan;
    public int hargaharian;
    public int hargamingguan;
    public int hargatahunan;
    public String region;
    public String jenis;
    public String key;

    public String getKey(){
        return key;
    }
    public void setKey(String key){
        this.key=key;
    }

    public Kost() {

    }

    public Kost(String namaKost, String alamat, int stock,int hargab,int hargah,int hargam,int hargat,String region,String jenis) {
        this.namaKost = namaKost;
        this.alamat = alamat;
        this.stock = stock;
        this.hargabulanan=hargab;
        this.hargaharian=hargah;
        this.hargamingguan=hargam;
        this.hargatahunan=hargat;
        this.region=region;
        this.jenis=jenis;
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
    public int getHargabulanan(){
        return hargabulanan;
    }
    public String getJenis(){
        return jenis;
    }
    public String getRegion(){
        return region;
    }
}
