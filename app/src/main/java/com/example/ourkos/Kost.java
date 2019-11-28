package com.example.ourkos;


public class Kost {
    private String namaKost;
    private String alamat;
    private int stock;
    private int hargabulanan;
    private int hargaharian;
    private int hargamingguan;
    private int hargatahunan;
    private String region;
    private String jenis;
    private String key;
    private int hasAC, hasWifi, hasListrik, hasAir, hasKamarMandiDalam, hasKasur;

    public String getKey(){
        return key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public Kost() {

    }

    public Kost(String namaKost, String alamat, int stock,int hargab,int hargah,int hargam,int hargat
            ,String region,String jenis, int hasAC, int hasWifi, int hasListrik, int hasAir,
                int hasKamarMandiDalam, int hasKasur) {
        this.namaKost = namaKost;
        this.alamat = alamat;
        this.stock = stock;
        this.hargabulanan = hargab;
        this.hargaharian = hargah;
        this.hargamingguan = hargam;
        this.hargatahunan = hargat;
        this.region = region;
        this.jenis = jenis;
        this.hasAC = hasAC;
        this.hasWifi = hasWifi;
        this.hasListrik = hasListrik;
        this.hasAir = hasAir;
        this.hasKamarMandiDalam = hasKamarMandiDalam;
        this.hasKasur = hasKasur;
    }

    public int getHargaharian() {
        return hargaharian;
    }

    public int getHargamingguan() {
        return hargamingguan;
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

    public int getHargatahunan(){

        return hargatahunan;
    }

    public String getJenis(){

        return jenis;
    }

    public void setNamaKost(String namaKost) {
        this.namaKost = namaKost;
    }

    public void setHasKasur(int hasKasur) {
        this.hasKasur = hasKasur;
    }

    public String getRegion(){
        return region;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setHargabulanan(int hargabulanan) {
        this.hargabulanan = hargabulanan;
    }

    public void setHargaharian(int hargaharian) {
        this.hargaharian = hargaharian;
    }

    public void setHargamingguan(int hargamingguan) {
        this.hargamingguan = hargamingguan;
    }

    public void setHargatahunan(int hargatahunan) {
        this.hargatahunan = hargatahunan;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getHasAC() {
        return hasAC;
    }

    public void setHasAC(int hasAC) {
        this.hasAC = hasAC;
    }

    public int getHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(int hasWifi) {
        this.hasWifi = hasWifi;
    }

    public int getHasListrik() {
        return hasListrik;
    }

    public void setHasListrik(int hasListrik) {
        this.hasListrik = hasListrik;
    }

    public int getHasAir() {
        return hasAir;
    }

    public void setHasAir(int hasAir) {
        this.hasAir = hasAir;
    }

    public int getHasKamarMandiDalam() {
        return hasKamarMandiDalam;
    }

    public void setHasKamarMandiDalam(int hasKamarMandiDalam) {
        this.hasKamarMandiDalam = hasKamarMandiDalam;
    }

    public int getHasKasur() {
        return hasKasur;
    }
}
