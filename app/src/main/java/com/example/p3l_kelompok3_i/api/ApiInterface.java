package com.example.p3l_kelompok3_i.api;

import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

  @GET("produk/get")
  Call<ResponProduk> getProdukSemua();

  @GET("jasa_layanan/get")
  Call<ResponLayanan> getJasaLayananSemua();

  @FormUrlEncoded
  @POST("jenis_hewan/create")
  Call<ResponJenisHewan> sendJenisHewan(@Field("nama_jenis_hewan") String nama_jenis_hewan);

  @FormUrlEncoded
  @POST("customer/create")
  Call<ResponCustomer> sendCustomer(@Field("nama_customer") String nama_customer,
                                    @Field("alamat_customer") String alamat_customer,
                                    @Field("tanggal_lahir_customer") String tanggal_lahir_customer,
                                    @Field("nomor_hp_customer") String nomor_hp_customer);



}
