package com.example.p3l_kelompok3_i.model_jasa_layanan;

import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.Comparator;

public class DataLayanan {

    String id_jasa_layanan;
    String nama_jasa_layanan;
    String  harga_jasa_layanan;
    Integer id_jenis_hewan;
    Integer id_ukuran_hewan;
    //DATA FK
    String ukuran_hewan;
    String nama_jenis_hewan;

    public static final Comparator<DataLayanan> BY_NAME_ALPAHBETICAL = new Comparator<DataLayanan>()
    {
        @Override
        public int compare(DataLayanan layanan, DataLayanan t1){
            return layanan.getNama_jasa_layanan().compareTo(t1.getNama_jasa_layanan());
        }
    };

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


    public String getId_jasa_layanan() {
        return id_jasa_layanan;
    }

    public void setId_jasa_layanan(String id_jasa_layanan) {
        this.id_jasa_layanan = id_jasa_layanan;
    }

    public String getNama_jasa_layanan() {
        return nama_jasa_layanan;
    }

    public void setNama_jasa_layanan(String nama_jasa_layanan) {
        this.nama_jasa_layanan = nama_jasa_layanan;
    }

    public String getHarga_jasa_layanan() {
        return harga_jasa_layanan;
    }

    public void setHarga_jasa_layanan(String harga_jasa_layanan) {
        this.harga_jasa_layanan = harga_jasa_layanan;
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


}
