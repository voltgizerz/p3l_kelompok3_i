package com.example.p3l_kelompok3_i.model_produk;

import java.util.List;

public class DataProduk {

    String nama_produk;
    String gambar_produk;
    Integer  id_produk;
    Integer harga_produk;
    Integer stok_produk;
    Integer stok_minimal_produk;

    public Integer getId_produk() {
        return id_produk;
    }

    public void setId_produk(Integer id_produk) {
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
        return gambar_produk;
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


}
