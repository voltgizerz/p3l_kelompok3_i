package com.example.p3l_kelompok3_i.model_pengadaan;

import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.Comparator;
import java.util.List;

public class DataPengadaan {

    String id_pengadaan;
    String kode_pengadaan;
    Integer id_supplier;
    String nama_supplier;
    String status_pengadaan;
    String tanggal_pengadaan;
    Integer total_pengadaan;
    List<ProdukDibeli> produk_dibeli;

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

    public static final Comparator<DataPengadaan> BY_NAME_ALPAHBETICAL = new Comparator<DataPengadaan>()
    {
        @Override
        public int compare(DataPengadaan pengadaan, DataPengadaan t1){
            return t1.getId_pengadaan().compareTo(pengadaan.getId_pengadaan());
        }
    };

    public String getId_pengadaan() {
        return id_pengadaan;
    }

    public void setId_pengadaan(String id_pengadaan) {
        this.id_pengadaan = id_pengadaan;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public List<ProdukDibeli> getProdukDibeli() {
        return produk_dibeli;
    }

    public void setProdukDibeli(List<ProdukDibeli> produkDibeli) {
        this.produk_dibeli = produkDibeli;
    }


    public Integer getTotal_pengadaan() {
        return total_pengadaan;
    }

    public void setTotal_pengadaan(Integer total_pengadaan) {
        this.total_pengadaan = total_pengadaan;
    }

    public String getKode_pengadaan() {
        return kode_pengadaan;
    }

    public void setKode_pengadaan(String kode_pengadaan) {
        this.kode_pengadaan = kode_pengadaan;
    }

    public Integer getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getStatus_pengadaan() {
        return status_pengadaan;
    }

    public void setStatus_pengadaan(String status_pengadaan) {
        this.status_pengadaan = status_pengadaan;
    }

    public String getTanggal_pengadaan() {
        return tanggal_pengadaan;
    }

    public void setTanggal_pengadaan(String tanggal_pengadaan) {
        this.tanggal_pengadaan = tanggal_pengadaan;
    }

}
