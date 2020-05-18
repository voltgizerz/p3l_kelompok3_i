package com.example.p3l_kelompok3_i.penjualan_layanan_detail;

import java.util.List;

public class ResponPenjualanLayananDetail {

    String status;

    List<DataPenjualanLayananDetail> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPenjualanLayananDetail> getData() {
        return data;
    }

    public void setData(List<DataPenjualanLayananDetail> data) {
        this.data = data;
    }
}
