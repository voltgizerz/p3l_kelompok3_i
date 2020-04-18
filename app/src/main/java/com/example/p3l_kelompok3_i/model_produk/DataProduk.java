package com.example.p3l_kelompok3_i.model_produk;

import java.util.Comparator;
import java.util.List;

public class DataProduk {


    String nama_produk;
    String gambar_produk;
    String  id_produk;
    Integer harga_produk;
    Integer stok_produk;
    Integer stok_minimal_produk;

    String created_date;
    String updated_date;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public DataProduk(String nama_produk, String id_produk) {
        this.nama_produk = nama_produk;
        this.id_produk = id_produk;
    }

    public static final Comparator<DataProduk> BY_NAME_ALPAHBETICAL = new Comparator<DataProduk>()
    {
        @Override
        public int compare(DataProduk produk, DataProduk t1){
            return Integer.parseInt(produk.getId_produk()) >  Integer.parseInt(t1.getId_produk()) ? -1 : ( Integer.parseInt(produk.getId_produk()) <  Integer.parseInt(t1.getId_produk()) ) ? 1 : 0;
        }
    };

    public static final Comparator<DataProduk> BY_NAME_HARGA = new Comparator<DataProduk>()
    {
        @Override
        public int compare(DataProduk produk, DataProduk t1){
            return produk.getHarga_produk().compareTo(t1.getHarga_produk());
        }
    };

    public static final Comparator<DataProduk> BY_NAME_STOK = new Comparator<DataProduk>()
    {
        @Override
        public int compare(DataProduk produk, DataProduk t1){
            return t1.getStok_produk().compareTo(produk.getStok_produk());
        }
    };

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public Integer getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(Integer harga_produk) {
        this.harga_produk = harga_produk;
    }

    public Integer getStok_produk() {
        return stok_produk;
    }

    public void setStok_produk(Integer stok_produk) {
        this.stok_produk = stok_produk;
    }

    public String getGambar_produk() {
        return "https://apip3landroid.000webhostapp.com/" + gambar_produk;
    }

    public void setGambar_produk(String gambar_produk) {
        this.gambar_produk = gambar_produk;
    }

    public Integer getStok_minimal_produk() {
        return stok_minimal_produk;
    }

    public void setStok_minimal_produk(Integer stok_minimal_produk) {
        this.stok_minimal_produk = stok_minimal_produk;
    }

    @Override
    public String toString(){
        return nama_produk;
    }


}
