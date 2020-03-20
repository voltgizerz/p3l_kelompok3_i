package com.example.p3l_kelompok3_i.model_hewan;

import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;

import java.util.List;

public class ResponHewan {

    String pesan;
    String kode;

    List<DataHewan> data;
    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public List<DataHewan> getData() {
        return data;
    }

    public void setData(List<DataHewan> data) {
        this.data = data;
    }





}
