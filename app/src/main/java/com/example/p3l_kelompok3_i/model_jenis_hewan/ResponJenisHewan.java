package com.example.p3l_kelompok3_i.model_jenis_hewan;

import com.example.p3l_kelompok3_i.model_customer.DataCustomer;

import java.util.List;

public class ResponJenisHewan {

    String message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
