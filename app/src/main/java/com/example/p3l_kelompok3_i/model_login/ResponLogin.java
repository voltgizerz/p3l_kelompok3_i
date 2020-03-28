package com.example.p3l_kelompok3_i.model_login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponLogin {

    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataLogin> data;

    public ResponLogin(){

    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataLogin> getData() {
        return data;
    }

    public void setData(List<DataLogin> data) {
        this.data = data;
    }

}
