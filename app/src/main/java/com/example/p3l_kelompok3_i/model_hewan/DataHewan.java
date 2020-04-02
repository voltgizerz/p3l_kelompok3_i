package com.example.p3l_kelompok3_i.model_hewan;

import android.content.Intent;

import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.Comparator;

public class DataHewan {

    String tanggal_lahir_hewan;
    String id_hewan;
    String nama_hewan;
    Integer id_jenis_hewan;
    Integer id_ukuran_hewan;
    Integer id_customer;
    String nama_customer;
    String ukuran_hewan;
    String nama_jenis_hewan;

    public static final Comparator<DataHewan> BY_NAME_ALPAHBETICAL = new Comparator<DataHewan>()
    {
        @Override
        public int compare(DataHewan hewan, DataHewan t1){
            return hewan.getNama_hewan().compareTo(t1.getNama_hewan());
        }
    };

    public String getTanggal_lahir_hewan() {
        return tanggal_lahir_hewan;
    }

    public void setTanggal_lahir_hewan(String tanggal_lahir_hewan) {
        this.tanggal_lahir_hewan = tanggal_lahir_hewan;
    }

    public String getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(String id_hewan) {
        this.id_hewan = id_hewan;
    }

    public String getNama_hewan() {
        return nama_hewan;
    }

    public void setNama_hewan(String nama_hewan) {
        this.nama_hewan = nama_hewan;
    }

    public Integer getId_jenis_hewan() {
        return id_jenis_hewan;
    }

    public void setId_jenis_hewan(Integer id_jenis_hewan) {
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public Integer getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public void setId_ukuran_hewan(Integer id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    public Integer getId_customer() {
        return id_customer;
    }

    public void setId_customer(Integer id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getUkuran_hewan() {
        return ukuran_hewan;
    }

    public void setUkuran_hewan(String ukuran_hewan) {
        this.ukuran_hewan = ukuran_hewan;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
    }

    @Override
    public String toString(){
        return nama_hewan;
    }


   }
