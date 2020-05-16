package com.example.p3l_kelompok3_i.model_penjualan_layanan;

import java.util.List;

public class ResponPenjualanLayanan {

    String status;
    List<DataPenjualanLayanan> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPenjualanLayanan> getData() {
        return data;
    }

    public void setData(List<DataPenjualanLayanan> data) {
        this.data = data;
    }
}
