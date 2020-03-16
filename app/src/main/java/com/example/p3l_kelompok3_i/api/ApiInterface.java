package com.example.p3l_kelompok3_i.api;

import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

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
  @POST("ukuran_hewan/create")
  Call<ResponUkuranHewan> sendUkuranHewan(@Field("ukuran_hewan") String ukuran_hewan);

  @FormUrlEncoded
  @POST("customer/create")
  Call<ResponCustomer> sendCustomer(@Field("nama_customer") String nama_customer,
                                    @Field("alamat_customer") String alamat_customer,
                                    @Field("tanggal_lahir_customer") String tanggal_lahir_customer,
                                    @Field("nomor_hp_customer") String nomor_hp_customer);

  @FormUrlEncoded
  @POST("pegawai/create")
  Call<ResponPegawai> sendPegawai(@Field("nama_pegawai") String nama_pegawai,
                                   @Field("alamat_pegawai") String alamat_pegawai,
                                   @Field("tanggal_lahir_pegawai") String tanggal_lahir_pegawai,
                                   @Field("nomor_hp_pegawai") String nomor_hp_pegawai,
                                  @Field("role_pegawai") String role_pegawai,
                                  @Field("username") String username,
                                  @Field("password") String password);



}
