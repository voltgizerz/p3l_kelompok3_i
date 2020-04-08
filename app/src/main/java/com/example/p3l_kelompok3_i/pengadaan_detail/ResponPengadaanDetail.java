package com.example.p3l_kelompok3_i.pengadaan_detail;

import com.example.p3l_kelompok3_i.model_pengadaan.DataPengadaan;

import java.util.List;

public class ResponPengadaanDetail {

    String status;

    List<DataPengadaanDetail> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPengadaanDetail> getData() {
        return data;
    }

    public void setData(List<DataPengadaanDetail> data) {
        this.data = data;
    }
}
