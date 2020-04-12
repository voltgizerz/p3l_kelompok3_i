package com.example.p3l_kelompok3_i.model_supplier;

import java.util.List;

public class ResponSupplier {

    String message;
    List<DataSupplier> data;

    public List<DataSupplier> getData() {
        return data;
    }

    public void setData(List<DataSupplier> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
