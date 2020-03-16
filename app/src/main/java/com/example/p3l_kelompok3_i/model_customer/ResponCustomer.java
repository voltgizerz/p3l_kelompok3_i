package com.example.p3l_kelompok3_i.model_customer;

import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;

import java.util.List;

public class ResponCustomer {

    List<DataCustomer> data;

    public List<DataCustomer> getData() {
        return data;
    }

    public void setData(List<DataCustomer> data) {
        this.data = data;
    }
}
