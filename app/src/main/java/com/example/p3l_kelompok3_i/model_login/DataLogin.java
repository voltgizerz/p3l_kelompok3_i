package com.example.p3l_kelompok3_i.model_login;

import com.google.gson.annotations.SerializedName;

public class DataLogin {

    @SerializedName("nama_pegawai")
    String nama_pegawai;
    @SerializedName("role_pegawai")
    String role_pegawai;
    @SerializedName("id_pegawai")
    String id_pegawai;
    @SerializedName("username")
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public String getRole_pegawai() {
        return role_pegawai;
    }

    public void setRole_pegawai(String role_pegawai) {
        this.role_pegawai = role_pegawai;
    }

    public String getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(String id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public DataLogin(){

    }



}
