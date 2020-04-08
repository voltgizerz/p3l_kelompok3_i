package com.example.p3l_kelompok3_i.model_pengadaan;


import java.util.List;

public class ResponPengadaan {

    String status;

    List<DataPengadaan> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPengadaan> getData() {
        return data;
    }

    public void setData(List<DataPengadaan> data) {
        this.data = data;
    }

}
