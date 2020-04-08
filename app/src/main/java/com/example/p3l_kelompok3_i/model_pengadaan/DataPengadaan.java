package com.example.p3l_kelompok3_i.model_pengadaan;

public class DataPengadaan {

    String kode_pengadaan;
    Integer id_supplier;
    String status_pengadaan;
    String tanggal_pengadaan;
    Integer total_pengadaan;

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
