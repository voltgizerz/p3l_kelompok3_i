package com.example.p3l_kelompok3_i.model_ukuran_hewan;

import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;

import java.util.Comparator;

public class DataUkuranHewan {

    String ukuran_hewan;
    String id_ukuran_hewan;

    String created_date;
    String updated_date;

    public static final Comparator<DataUkuranHewan> BY_NAME_ALPAHBETICAL = new Comparator<DataUkuranHewan>()
    {
        @Override
        public int compare(DataUkuranHewan layanan, DataUkuranHewan t1){
            return Integer.parseInt(layanan.getId_ukuran_hewan()) >  Integer.parseInt(t1.getId_ukuran_hewan()) ? -1 : ( Integer.parseInt(layanan.getId_ukuran_hewan()) <  Integer.parseInt(t1.getId_ukuran_hewan()) ) ? 1 : 0;
        }
    };

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
