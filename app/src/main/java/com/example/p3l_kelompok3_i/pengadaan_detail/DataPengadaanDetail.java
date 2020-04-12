package com.example.p3l_kelompok3_i.pengadaan_detail;

public class DataPengadaanDetail {

    String id_detail_pengadaan;
    Integer id_produk_fk;
    String nama_produk;
    String kode_pengadaan_fk;
    String satuan_pengadaan;
    Integer jumlah_pengadaan;
    String tanggal_pengadaan;

    public String getId_detail_pengadaan() {
        return id_detail_pengadaan;
    }

    public void setId_detail_pengadaan(String id_detail_pengadaan) {
        this.id_detail_pengadaan = id_detail_pengadaan;
    }

    public Integer getId_produk_fk() {
        return id_produk_fk;
    }

    public void setId_produk_fk(Integer id_produk_fk) {
        this.id_produk_fk = id_produk_fk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getKode_pengadaan_fk() {
        return kode_pengadaan_fk;
    }

    public void setKode_pengadaan_fk(String kode_pengadaan_fk) {
        this.kode_pengadaan_fk = kode_pengadaan_fk;
    }

    public String getSatuan_pengadaan() {
        return satuan_pengadaan;
    }

    public void setSatuan_pengadaan(String satuan_pengadaan) {
        this.satuan_pengadaan = satuan_pengadaan;
    }

    public Integer getJumlah_pengadaan() {
        return jumlah_pengadaan;
    }

    public void setJumlah_pengadaan(Integer jumlah_pengadaan) {
        this.jumlah_pengadaan = jumlah_pengadaan;
    }

    public String getTanggal_pengadaan() {
        return tanggal_pengadaan;
    }

    public void setTanggal_pengadaan(String tanggal_pengadaan) {
        this.tanggal_pengadaan = tanggal_pengadaan;
    }
}
