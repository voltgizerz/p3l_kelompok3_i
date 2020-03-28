package com.example.p3l_kelompok3_i.api;

import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_login.ResponLogin;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ResponLogin> loginPegawai(@Field("username") String username,
                                    @Field("password") String password);

    @GET("produk/get")
    Call<ResponProduk> getProdukSemua();

    @GET("jenis_hewan/get")
    Call<ResponJenisHewan> getJenisHewanSemua();

    @GET("hewan/get")
    Call<ResponHewan> getHewanSemua();

    @GET("ukuran_hewan/get")
    Call<ResponUkuranHewan> getUkuranHewanSemua();

    @GET("jasa_layanan/get")
    Call<ResponLayanan> getJasaLayananSemua();

    @GET("customer/get")
    Call<ResponCustomer> getCustomerSemua();

    @GET("pegawai/get")
    Call<ResponPegawai> getPegawaiSemua();

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

    @FormUrlEncoded
    @POST("hewan/create")
    Call<ResponHewan> sendHewan(@Field("nama_hewan") String nama_hewan,
                                @Field("id_jenis_hewan") Integer id_jenis_hewan,
                                @Field("id_ukuran_hewan") Integer id_ukuran_hewan,
                                @Field("id_customer") Integer id_customer,
                                @Field("tanggal_lahir_hewan") String tanggal_lahir_hewan);

    @FormUrlEncoded
    @POST("jenis_hewan/update/{id_jenis_hewan}")
    Call<ResponJenisHewan> updateJenisHewan(@Path("id_jenis_hewan") String id_jenis_hewan,
                                            @Field("nama_jenis_hewan") String nama_jenis_hewan);

    @FormUrlEncoded
    @POST("ukuran_hewan/update/{id_ukuran_hewan}")
    Call<ResponUkuranHewan> updateUkuranHewan(@Path("id_ukuran_hewan") String id_ukuran_hewan,
                                              @Field("ukuran_hewan") String ukuran_hewan);

    @FormUrlEncoded
    @POST("customer/update/{id_customer}")
    Call<ResponCustomer> updateCustomer(@Path("id_customer") String id_customer,
                                        @Field("nama_customer") String nama_customer,
                                        @Field("alamat_customer") String alamat_customer,
                                        @Field("tanggal_lahir_customer") String tanggal_lahir_customer,
                                        @Field("nomor_hp_customer") String nomor_hp_customer);

    @FormUrlEncoded
    @POST("pegawai/update/{id_pegawai}")
    Call<ResponPegawai> updatePegawai(@Path("id_pegawai") String id_pegawai,
                                      @Field("nama_pegawai") String nama_pegawai,
                                      @Field("alamat_pegawai") String alamat_pegawai,
                                      @Field("tanggal_lahir_pegawai") String tanggal_lahir_pegawai,
                                      @Field("nomor_hp_pegawai") String nomor_hp_pegawai,
                                      @Field("role_pegawai") String role_pegawai,
                                      @Field("username") String username,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("hewan/update/{id_hewan}")
    Call<ResponHewan> updateHewan(@Path("id_hewan") String id_hewan,
                                  @Field("nama_hewan") String nama_hewan,
                                  @Field("id_jenis_hewan") Integer id_jenis_hewan,
                                  @Field("id_ukuran_hewan") Integer id_ukuran_hewan,
                                  @Field("id_customer") Integer id_customer,
                                  @Field("tanggal_lahir_hewan") String tanggal_lahir_hewan);


    @POST("ukuran_hewan/delete/{id_ukuran_hewan}")
    Call<ResponUkuranHewan> deleteUkuranHewan(@Path("id_ukuran_hewan") String id_ukuran_hewan);

    @POST("jenis_hewan/delete/{id_jenis_hewan}")
    Call<ResponJenisHewan> deleteJenisHewan(@Path("id_jenis_hewan") String id_jenis_hewan);

    @POST("customer/delete/{id_customer}")
    Call<ResponCustomer> deleteCustomer(@Path("id_customer") String id_customer);

    @POST("pegawai/delete/{id_pegawai}")
    Call<ResponPegawai> deletePegawai(@Path("id_pegawai") String id_pegawai);

    @POST("hewan/delete/{id_hewan}")
    Call<ResponHewan> deleteHewan(@Path("id_hewan") String id_hewan);


}
