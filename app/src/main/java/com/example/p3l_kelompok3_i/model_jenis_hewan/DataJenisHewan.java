package com.example.p3l_kelompok3_i.model_jenis_hewan;

public class DataJenisHewan {


    String nama_jenis_hewan;
    String id_jenis_hewan;

    String created_date;
    String updated_date;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public DataJenisHewan(String nama_jenis_hewan, String id_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public String getId_jenis_hewan() {
        return id_jenis_hewan;
    }

    public void setId_jenis_hewan(String id_jenis_hewan) {
        this.id_jenis_hewan = id_jenis_hewan;
    }

    public String getNama_jenis_hewan() {
        return nama_jenis_hewan;
    }

    public void setNama_jenis_hewan(String nama_jenis_hewan) {
        this.nama_jenis_hewan = nama_jenis_hewan;
    }

    @Override
    public String toString(){
        return nama_jenis_hewan;
    }

}
