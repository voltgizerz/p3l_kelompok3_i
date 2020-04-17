package com.example.p3l_kelompok3_i.model_penjualan_produk;

import java.util.List;

public class ResponPenjualanProduk {

    String status;
    List<DataPenjualanProduk> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPenjualanProduk> getData() {
        return data;
    }

    public void setData(List<DataPenjualanProduk> data) {
        this.data = data;
    }
}
