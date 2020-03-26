package com.example.p3l_kelompok3_i.model_pegawai;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p3l_kelompok3_i.model_customer.DataCustomer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponPegawai {

    String status;

    String message;
    List<DataPegawai> data;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
