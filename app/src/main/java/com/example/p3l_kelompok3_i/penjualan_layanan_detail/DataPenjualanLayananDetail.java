package com.example.p3l_kelompok3_i.penjualan_layanan_detail;

public class DataPenjualanLayananDetail {

    String id_detail_penjualan_jasa_layanan;
    String kode_transaksi_penjualan_jasa_layanan_fk;
    Integer id_jasa_layanan_fk;
    Integer jumlah_jasa_layanan;
    Integer subtotal;
    String nama_jasa_layanan;
    String nama_jenis_hewan;
    String nama_ukuran_hewan;

    public String getId_detail_penjualan_jasa_layanan() {
        return id_detail_penjualan_jasa_layanan;
    }

    public void setId_detail_penjualan_jasa_layanan(String id_detail_penjualan_jasa_layanan) {
        this.id_detail_penjualan_jasa_layanan = id_detail_penjualan_jasa_layanan;
    }

    public String getKode_transaksi_penjualan_jasa_layanan_fk() {
        return kode_transaksi_penjualan_jasa_layanan_fk;
    }

    public void setKode_transaksi_penjualan_jasa_layanan_fk(String kode_transaksi_penjualan_jasa_layanan_fk) {
        this.kode_transaksi_penjualan_jasa_layanan_fk = kode_transaksi_penjualan_jasa_layanan_fk;
    }

    public Integer getId_jasa_layanan_fk() {
        return id_jasa_layanan_fk;
    }

    public void setId_jasa_layanan_fk(Integer id_jasa_layanan_fk) {
        this.id_jasa_layanan_fk = id_jasa_layanan_fk;
    }

    public Integer getJumlah_jasa_layanan() {
        return jumlah_jasa_layanan;
    }

    public void setJumlah_jasa_layanan(Integer jumlah_jasa_layanan) {
        this.jumlah_jasa_layanan = jumlah_jasa_layanan;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public String getNama_jasa_layanan() {
        return nama_jasa_layanan;
    }

    public void setNama_jasa_layanan(String nama_jasa_layanan) {
        this.nama_jasa_layanan = nama_jasa_layanan;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
    }

    public String getNama_ukuran_hewan() {
        return nama_ukuran_hewan;
    }

    public void setNama_ukuran_hewan(String nama_ukuran_hewan) {
        this.nama_ukuran_hewan = nama_ukuran_hewan;
    }
}
