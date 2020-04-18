package com.example.p3l_kelompok3_i.model_pegawai;

import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;

import java.util.Comparator;

public class DataPegawai {

    String id_pegawai;
    String nama_pegawai;
    String alamat_pegawai;
    String tanggal_lahir_pegawai;
    String nomor_hp_pegawai;
    String role_pegawai;
    String username;
    String password;

    String created_date;
    String updated_date;

    public static final Comparator<DataPegawai> BY_NAME_ALPAHBETICAL = new Comparator<DataPegawai>()
    {
        @Override
        public int compare(DataPegawai layanan, DataPegawai t1){
            return Integer.parseInt(layanan.getId_pegawai()) >  Integer.parseInt(t1.getId_pegawai()) ? -1 : ( Integer.parseInt(layanan.getId_pegawai()) <  Integer.parseInt(t1.getId_pegawai()) ) ? 1 : 0;
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

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getTanggal_lahir_pegawai() {
        return tanggal_lahir_pegawai;
    }

    public void setTanggal_lahir_pegawai(String tanggal_lahir_pegawai) {
        this.tanggal_lahir_pegawai = tanggal_lahir_pegawai;
    }

    public String getAlamat_pegawai() {
        return alamat_pegawai;
    }

    public void setAlamat_pegawai(String alamat_pegawai) {
        this.alamat_pegawai = alamat_pegawai;
    }

    public String getNomor_hp_pegawai() {
        return nomor_hp_pegawai;
    }

    public void setNomor_hp_pegawai(String nomor_hp_pegawai) {
        this.nomor_hp_pegawai = nomor_hp_pegawai;
    }

    public String getRole_pegawai() {
        return role_pegawai;
    }

    public void setRole_pegawai(String role_pegawai) {
        this.role_pegawai = role_pegawai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
