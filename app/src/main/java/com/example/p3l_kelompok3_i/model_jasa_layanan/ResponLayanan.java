package com.example.p3l_kelompok3_i.model_jasa_layanan;

import java.util.List;

public class ResponLayanan {

    String message;
    List<DataLayanan> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataLayanan> getData() {
        return data;
    }

    public void setData(List<DataLayanan> data) {
        this.data = data;
    }
}
