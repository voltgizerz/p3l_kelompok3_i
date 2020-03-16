package com.example.p3l_kelompok3_i.api;

import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

public interface ApiInterface {

  @GET("produk/get")
  Call<ResponProduk> getProdukSemua();

  @GET("jasa_layanan/get")
  Call<ResponLayanan> getJasaLayananSemua();



}
