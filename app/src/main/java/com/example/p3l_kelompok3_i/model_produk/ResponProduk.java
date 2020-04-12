package com.example.p3l_kelompok3_i.model_produk;

import java.util.List;

public class ResponProduk {

    String message;
    List<DataProduk> data;

    public List<DataProduk> getData() {
        return data;
    }

    public void setData(List<DataProduk> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
