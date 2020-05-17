package com.example.p3l_kelompok3_i.model_penjualan_layanan;

import com.example.p3l_kelompok3_i.model_penjualan_produk.DataPenjualanProduk;

import java.util.Comparator;

public class DataPenjualanLayanan {

    String id_transaksi_penjualan_jasa_layanan;
    String kode_transaksi_penjualan_jasa_layanan;
    Integer id_hewan;
    String tanggal_penjualan_jasa_layanan;
    String tanggal_pembayaran_jasa_layanan;
    Integer diskon;
    Integer total_penjualan_jasa_layanan;
    String status_layanan;
    String status_pembayaran;
    String status_penjualan;
    Integer id_cs;
    Integer id_kasir;
    Integer total_harga;
    String nama_cs;
    String nama_kasir;
    String nama_hewan;
    String created_date;
    String updated_date;

    public static final Comparator<DataPenjualanLayanan> BY_NAME_ALPAHBETICAL = new Comparator<DataPenjualanLayanan>()
    {
        @Override
        public int compare(DataPenjualanLayanan pengadaan, DataPenjualanLayanan t1){
            return t1.getId_transaksi_penjualan_jasa_layanan().compareTo(pengadaan.getId_transaksi_penjualan_jasa_layanan());
        }
    };

    public String getNama_kasir() {
        return nama_kasir;
    }

    public void setNama_kasir(String nama_kasir) {
        this.nama_kasir = nama_kasir;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public String getId_transaksi_penjualan_jasa_layanan() {
        return id_transaksi_penjualan_jasa_layanan;
    }

    public void setId_transaksi_penjualan_jasa_layanan(String id_transaksi_penjualan_jasa_layanan) {
        this.id_transaksi_penjualan_jasa_layanan = id_transaksi_penjualan_jasa_layanan;
    }

    public String getKode_transaksi_penjualan_jasa_layanan() {
        return kode_transaksi_penjualan_jasa_layanan;
    }

    public void setKode_transaksi_penjualan_jasa_layanan(String kode_transaksi_penjualan_jasa_layanan) {
        this.kode_transaksi_penjualan_jasa_layanan = kode_transaksi_penjualan_jasa_layanan;
    }

    public Integer getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(Integer id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getTanggal_penjualan_jasa_layanan() {
        return tanggal_penjualan_jasa_layanan;
    }

    public void setTanggal_penjualan_jasa_layanan(String tanggal_penjualan_jasa_layanan) {
        this.tanggal_penjualan_jasa_layanan = tanggal_penjualan_jasa_layanan;
    }

    public String getTanggal_pembayaran_jasa_layanan() {
        return tanggal_pembayaran_jasa_layanan;
    }

    public void setTanggal_pembayaran_jasa_layanan(String tanggal_pembayaran_jasa_layanan) {
        this.tanggal_pembayaran_jasa_layanan = tanggal_pembayaran_jasa_layanan;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public void setDiskon(Integer diskon) {
        this.diskon = diskon;
    }

    public Integer getTotal_penjualan_jasa_layanan() {
        return total_penjualan_jasa_layanan;
    }

    public void setTotal_penjualan_jasa_layanan(Integer total_penjualan_jasa_layanan) {
        this.total_penjualan_jasa_layanan = total_penjualan_jasa_layanan;
    }

    public String getStatus_layanan() {
        return status_layanan;
    }

    public void setStatus_layanan(String status_layanan) {
        this.status_layanan = status_layanan;
    }

    public String getStatus_pembayaran() {
        return status_pembayaran;
    }

    public void setStatus_pembayaran(String status_pembayaran) {
        this.status_pembayaran = status_pembayaran;
    }

    public String getStatus_penjualan() {
        return status_penjualan;
    }

    public void setStatus_penjualan(String status_penjualan) {
        this.status_penjualan = status_penjualan;
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
}
