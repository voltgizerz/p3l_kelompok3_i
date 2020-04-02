package com.example.p3l_kelompok3_i.model_customer;

import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;

import java.util.Comparator;

public class DataCustomer {

    String id_customer;
    String nama_customer;
    String alamat_customer;
    String nomor_hp_customer;
    String tanggal_lahir_customer;
    String pesan;
    String kode;

    public static final Comparator<DataCustomer> BY_NAME_ALPAHBETICAL = new Comparator<DataCustomer>()
    {
        @Override
        public int compare(DataCustomer customer, DataCustomer t1){
            return customer.getNama_customer().compareTo(t1.getNama_customer());
        }
    };


    public DataCustomer(String id_customer, String nama_customer) {
        this.id_customer = id_customer;
        this.nama_customer = nama_customer;
    }


    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }




    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getNomor_hp_customer() {
        return nomor_hp_customer;
    }

    public void setNomor_hp_customer(String nomor_hp_customer) {
        this.nomor_hp_customer = nomor_hp_customer;
    }

    public String getTanggal_lahir_customer() {
        return tanggal_lahir_customer;
    }

    public void setTanggal_lahir_customer(String tanggal_lahir_customer) {
        this.tanggal_lahir_customer = tanggal_lahir_customer;
    }

    @Override
    public String toString(){
        return nama_customer;
    }

}