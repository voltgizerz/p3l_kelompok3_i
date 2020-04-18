package com.example.p3l_kelompok3_i.model_ukuran_hewan;

public class DataUkuranHewan {

    String ukuran_hewan;
    String id_ukuran_hewan;

    String created_date;
    String deleted_date;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getDeleted_date() {
        return deleted_date;
    }

    public void setDeleted_date(String deleted_date) {
        this.deleted_date = deleted_date;
    }

    public DataUkuranHewan(String ukuran_hewan, String id_ukuran_hewan) {
        this.ukuran_hewan = ukuran_hewan;
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    public String getUkuran_hewan() {
        return ukuran_hewan;
    }

    public void setUkuran_hewan(String ukuran_hewan) {
        this.ukuran_hewan = ukuran_hewan;
    }

    public String getId_ukuran_hewan() {
        return id_ukuran_hewan;
    }

    public void setId_ukuran_hewan(String id_ukuran_hewan) {
        this.id_ukuran_hewan = id_ukuran_hewan;
    }

    @Override
    public String toString(){
        return ukuran_hewan;
    }

}
