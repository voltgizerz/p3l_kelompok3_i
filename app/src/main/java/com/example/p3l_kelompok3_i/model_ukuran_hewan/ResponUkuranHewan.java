package com.example.p3l_kelompok3_i.model_ukuran_hewan;

import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;

import java.util.List;

public class ResponUkuranHewan {

    String message;
    String kode;

    List<DataUkuranHewan> data;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public List<DataUkuranHewan> getData() {
        return data;
    }

    public void setData(List<DataUkuranHewan> data) {
        this.data = data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
