package com.example.p3l_kelompok3_i.model_hewan;

import android.content.Intent;

public class DataHewan {

    String nama_hewan;

    public Intent getId_hewan() {
        return id_hewan;
    }

    public void setId_hewan(Intent id_hewan) {
        this.id_hewan = id_hewan;
    }

    Intent id_hewan;
    Integer id_jenis_hewan;
    Integer id_ukuran_hewan;
    Integer id_customer;

    public String getTanggal_lahir_hewan() {
        return tanggal_lahir_hewan;
    }

    public void setTanggal_lahir_hewan(String tanggal_lahir_hewan) {
        this.tanggal_lahir_hewan = tanggal_lahir_hewan;
    }

    String tanggal_lahir_hewan;


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



   }
