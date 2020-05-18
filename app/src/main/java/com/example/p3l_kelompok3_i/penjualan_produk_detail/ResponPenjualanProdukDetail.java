package com.example.p3l_kelompok3_i.penjualan_produk_detail;

import java.util.List;

public class ResponPenjualanProdukDetail {

    String status;

    List<DataPenjualanProdukDetail> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataPenjualanProdukDetail> getData() {
        return data;
    }

    public void setData(List<DataPenjualanProdukDetail> data) {
        this.data = data;
    }
}
