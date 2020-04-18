package com.example.p3l_kelompok3_i.model_supplier;

import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.Comparator;

public class DataSupplier {

    String id_supplier;
    String nama_supplier;
    String alamat_supplier;
    String nomor_telepon_supplier;

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

    public DataSupplier(String id_supplier, String nama_supplier) {
        this.id_supplier = id_supplier;
        this.nama_supplier = nama_supplier;
    }

    public static final Comparator<DataSupplier> BY_NAME_ALPAHBETICAL = new Comparator<DataSupplier>()
    {
        @Override
        public int compare(DataSupplier produk, DataSupplier t1){
            return produk.getNama_supplier().compareTo(t1.getNama_supplier());
        }
    };

    public String getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(String id_supplier) {
        this.id_supplier = id_supplier;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat_supplier() {
        return alamat_supplier;
    }

    public void setAlamat_supplier(String alamat_supplier) {
        this.alamat_supplier = alamat_supplier;
    }

    public String getNomor_telepon_supplier() {
        return nomor_telepon_supplier;
    }

    public void setNomor_telepon_supplier(String nomor_telepon_supplier) {
        this.nomor_telepon_supplier = nomor_telepon_supplier;
    }

    @Override
    public String toString(){
        return nama_supplier;
    }


}
