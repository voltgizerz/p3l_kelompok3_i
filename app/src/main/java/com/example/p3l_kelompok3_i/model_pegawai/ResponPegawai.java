package com.example.p3l_kelompok3_i.model_pegawai;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p3l_kelompok3_i.model_customer.DataCustomer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponPegawai {


    String message;
    List<DataPegawai> data;



    public List<DataPegawai> getData() {
        return data;
    }

    public void setData(List<DataPegawai> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
