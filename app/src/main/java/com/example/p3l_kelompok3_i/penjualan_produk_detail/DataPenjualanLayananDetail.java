package com.example.p3l_kelompok3_i.penjualan_produk_detail;

public class DataPenjualanLayananDetail {

    String id_detail_penjualan_produk;
    String kode_transaksi_penjualan_produk_fk;
    Integer id_produk_penjualan_fk;
    Integer jumlah_produk;
    Integer subtotal;
    String nama_produk;
    String gambar_produk;

    public String getGambar_produk() {
        return "https://apip3landroid.000webhostapp.com/" + gambar_produk;
    }

    public void setGambar_produk(String gambar_produk) {
        this.gambar_produk = gambar_produk;
    }

    public String getId_detail_penjualan_produk() {
        return id_detail_penjualan_produk;
    }

    public void setId_detail_penjualan_produk(String id_detail_penjualan_produk) {
        this.id_detail_penjualan_produk = id_detail_penjualan_produk;
    }

    public String getKode_transaksi_penjualan_produk_fk() {
        return kode_transaksi_penjualan_produk_fk;
    }

    public void setKode_transaksi_penjualan_produk_fk(String kode_transaksi_penjualan_produk_fk) {
        this.kode_transaksi_penjualan_produk_fk = kode_transaksi_penjualan_produk_fk;
    }

    public Integer getId_produk_penjualan_fk() {
        return id_produk_penjualan_fk;
    }

    public void setId_produk_penjualan_fk(Integer id_produk_penjualan_fk) {
        this.id_produk_penjualan_fk = id_produk_penjualan_fk;
    }

    public Integer getJumlah_produk() {
        return jumlah_produk;
    }

    public void setJumlah_produk(Integer jumlah_produk) {
        this.jumlah_produk = jumlah_produk;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }
}
