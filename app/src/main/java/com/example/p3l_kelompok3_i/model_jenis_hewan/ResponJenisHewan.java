package com.example.p3l_kelompok3_i.model_jenis_hewan;

import com.example.p3l_kelompok3_i.model_customer.DataCustomer;

import java.util.List;

public class ResponJenisHewan {

    String pesan;
    String kode;

    List<DataJenisHewan> data;

    public List<DataJenisHewan> getData() {
        return data;
    }

    public void setData(List<DataJenisHewan> data) {
        this.data = data;
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
}
