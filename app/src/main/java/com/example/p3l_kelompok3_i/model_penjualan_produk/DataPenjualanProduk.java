package com.example.p3l_kelompok3_i.model_penjualan_produk;

public class DataPenjualanProduk {

    String id_transaksi_penjualan_produk;
    String kode_transaksi_penjualan_produk;
    Integer id_hewan;
    String tanggal_penjualan_produk;
    String tanggal_pembayaran_produk;
    Integer diskon;
    Integer total_penjualan_produk;
    String status_pembayaran;
    Integer id_cs;
    Integer id_kasir;
    Integer total_harga;
    String nama_cs;
    String created_date;
    String updated_date;

    public String getNama_cs() {
        return nama_cs;
    }

    public void setNama_cs(String nama_cs) {
        this.nama_cs = nama_cs;
    }

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


    public String getId_transaksi_penjualan_produk() {
        return id_transaksi_penjualan_produk;
    }

    public void setId_transaksi_penjualan_produk(String id_transaksi_penjualan_produk) {
        this.id_transaksi_penjualan_produk = id_transaksi_penjualan_produk;
    }

    public String getKode_transaksi_penjualan_produk() {
        return kode_transaksi_penjualan_produk;
    }

    public void setKode_transaksi_penjualan_produk(String kode_transaksi_penjualan_produk) {
        this.kode_transaksi_penjualan_produk = kode_transaksi_penjualan_produk;
    }

    public Integer getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(Integer id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getTanggal_penjualan_produk() {
        return tanggal_penjualan_produk;
    }

    public void setTanggal_penjualan_produk(String tanggal_penjualan_produk) {
        this.tanggal_penjualan_produk = tanggal_penjualan_produk;
    }

    public String getTanggal_pembayaran_produk() {
        return tanggal_pembayaran_produk;
    }

    public void setTanggal_pembayaran_produk(String tanggal_pembayaran_produk) {
        this.tanggal_pembayaran_produk = tanggal_pembayaran_produk;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public void setDiskon(Integer diskon) {
        this.diskon = diskon;
    }

    public Integer getTotal_penjualan_produk() {
        return total_penjualan_produk;
    }

    public void setTotal_penjualan_produk(Integer total_penjualan_produk) {
        this.total_penjualan_produk = total_penjualan_produk;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public Integer getId_cs() {
        return id_cs;
    }

    public void setId_cs(Integer id_cs) {
        this.id_cs = id_cs;
    }

    public Integer getId_kasir() {
        return id_kasir;
    }

    public void setId_kasir(Integer id_kasir) {
        this.id_kasir = id_kasir;
    }

    public Integer getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(Integer total_harga) {
        this.total_harga = total_harga;
    }
}
