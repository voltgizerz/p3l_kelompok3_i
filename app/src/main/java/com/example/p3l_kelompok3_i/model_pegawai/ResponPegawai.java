package com.example.p3l_kelompok3_i.model_pegawai;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p3l_kelompok3_i.model_customer.DataCustomer;

import java.util.List;

public class ResponPegawai {

    String kode;
    String pesan;
    List<DataPegawai> data;

    public List<DataPegawai> getData() {
        return data;
    }

    public void setData(List<DataPegawai> data) {
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
