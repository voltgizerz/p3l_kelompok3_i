package com.example.p3l_kelompok3_i.api;

import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_login.ResponLogin;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;
import com.example.p3l_kelompok3_i.model_pengadaan.ResponPengadaan;
import com.example.p3l_kelompok3_i.model_penjualan_produk.ResponPenjualanProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.example.p3l_kelompok3_i.model_supplier.ResponSupplier;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;
import com.example.p3l_kelompok3_i.pengadaan_detail.ResponPengadaanDetail;
import com.example.p3l_kelompok3_i.penjualan_produk_detail.ResponPenjualanProdukDetail;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {


    //LOGIN
    @FormUrlEncoded
    @POST("login")
    Call<ResponLogin> loginPegawai(@Field("username") String username,
                                   @Field("password") String password);

    //////////////////////////////////////////////////////// GET DATA   //////////////////////////////////////////////////////
    @GET("detail_penjualan_produk/get")
    Call<ResponPenjualanProdukDetail> getPenjualanProdukDetailSemua();

    @GET("pengadaan/get")
    Call<ResponPengadaan> getPengadaanSemua();

    @GET("detail_pengadaan/get")
    Call<ResponPengadaanDetail> getPengadaanDetailSemua();

    @GET("produk/get")
    Call<ResponProduk> getProdukSemua();

    @GET("supplier/get")
    Call<ResponSupplier> getSupplierSemua();

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

    @GET("penjualan_produk/get")
    Call<ResponPenjualanProduk> getPenjualanProdukSemua();


    /////////////////////////////////////////CREATE DATA//////////////////////////////////////////////

    @FormUrlEncoded
    @POST("detail_penjualan_produk/create")
    Call<ResponPenjualanProdukDetail> sendPenjualanProdukDetail(@Field("kode_transaksi_penjualan_produk_fk") String kode_transaksi_penjualan_produk_fk,
                                                                @Field("id_produk_penjualan_fk") Integer id_produk_penjualan_fk,
                                                                @Field("jumlah_produk") Integer jumlah_produk);

    @FormUrlEncoded
    @POST("penjualan_produk/create")
    Call<ResponPenjualanProduk> sendPenjualanProduk(@Field("id_cs") Integer id_cs,
                                                    @Field("id_kasir") Integer id_kasir);

    @FormUrlEncoded
    @POST("detail_pengadaan/create")
    Call<ResponPengadaanDetail> sendPengadaanDetail(@Field("id_produk_fk") Integer id_produk_fk,
                                                    @Field("kode_pengadaan_fk") String kode_pengadaan_fk,
                                                    @Field("satuan_pengadaan") String satuan_pengadaan,
                                                    @Field("jumlah_pengadaan") Integer jumlah_pengadaan);

    @FormUrlEncoded
    @POST("pengadaan/create")
    Call<ResponPengadaan> sendPengadaan(@Field("id_supplier") Integer id_supplier);


    @Multipart
    @POST("produk/create")
    Call<ResponProduk> sendProduk(@Part("nama_produk") RequestBody nama_produk,
                                  @Part("harga_produk") RequestBody harga_produk,
                                  @Part MultipartBody.Part gambar_produk,
                                  @Part("stok_produk") RequestBody stok_produk,
                                  @Part("stok_minimal_produk") RequestBody stok_minimal_produk);


    @FormUrlEncoded
    @POST("supplier/create")
    Call<ResponSupplier> sendSupplier(@Field("nama_supplier") String nama_supplier,
                                      @Field("alamat_supplier") String alamat_supplier,
                                      @Field("nomor_telepon_supplier") String nomor_telepon_supplier);

    @FormUrlEncoded
    @POST("jasa_layanan/create")
    Call<ResponLayanan> sendLayanan(@Field("nama_jasa_layanan") String nama_jasa_layanan,
                                    @Field("harga_jasa_layanan") String harga_jasa_layanan,
                                    @Field("id_jenis_hewan") Integer id_jenis_hewan,
                                    @Field("id_ukuran_hewan") Integer id_ukuran_hewan);

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


    //////////////////////////////////////////////////////// UPDATE DATA   //////////////////////////////////////////////////////
    @Multipart
    @POST("produk/update/{id_produk}")
    Call<ResponProduk> updateProduk(@Path("id_produk") String id_produk,
                                    @Part("nama_produk") RequestBody nama_produk,
                                    @Part("harga_produk") RequestBody harga_produk,
                                    @Part MultipartBody.Part gambar_produk,
                                    @Part("stok_produk") RequestBody stok_produk,
                                    @Part("stok_minimal_produk") RequestBody stok_minimal_produk);

    @Multipart
    @POST("produk/update/{id_produk}")
    Call<ResponProduk> updateProdukTanpaFoto(@Path("id_produk") String id_produk,
                                             @Part("nama_produk") RequestBody nama_produk,
                                             @Part("harga_produk") RequestBody harga_produk,
                                             @Part MultipartBody.Part gambar_produk,
                                             @Part("stok_produk") RequestBody stok_produk,
                                             @Part("stok_minimal_produk") RequestBody stok_minimal_produk);

    @FormUrlEncoded
    @POST("detail_pengadaan/update/{id_detail_pengadaan}")
    Call<ResponPengadaanDetail> updatePengadaanDetail(@Path("id_detail_pengadaan") String id_detail_pengadaan,
                                                      @Field("id_produk_fk") Integer id_produk_fk,
                                                      @Field("jumlah_pengadaan") Integer jumlah_pengadaan,
                                                      @Field("satuan_pengadaan") String satuan_pengadaan,
                                                      @Field("kode_pengadaan_fk") String kode_pengadaan_fk);


    @FormUrlEncoded
    @POST("pengadaan/update/{id_pengadaan}")
    Call<ResponPengadaan> updatePengadaan(@Path("id_pengadaan") String id_pengadaan,
                                          @Field("id_supplier") Integer id_supplier,
                                          @Field("status") String status_pengadaan,
                                          @Field("kode_pengadaan_fk") String kode_pengadaan_fk);

    @FormUrlEncoded
    @POST("supplier/update/{id_supplier}")
    Call<ResponSupplier> updateSupplier(@Path("id_supplier") String id_supplier,
                                        @Field("nama_supplier") String nama_supplier,
                                        @Field("alamat_supplier") String alamat_supplier,
                                        @Field("nomor_telepon_supplier") String nomor_telepon_supplier);

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

    @FormUrlEncoded
    @POST("jasa_layanan/update/{id_jasa_layanan}")
    Call<ResponLayanan> updateLayanan(@Path("id_jasa_layanan") String id_jasa_layanan,
                                      @Field("nama_jasa_layanan") String nama_jasa_layanan,
                                      @Field("harga_jasa_layanan") String harga_jasa_layanan,
                                      @Field("id_jenis_hewan") Integer id_jenis_hewan,
                                      @Field("id_ukuran_hewan") Integer id_ukuran_hewan);

    @FormUrlEncoded
    @POST("detail_penjualan_produk/update/{id_detail_penjualan_produk}")
    Call<ResponPenjualanProdukDetail> updatePenjualanProdukDetail(@Path("id_detail_penjualan_produk") String id_detail_penjualan_produk,
                                                                  @Field("kode_transaksi_penjualan_produk_fk") String kode_transaksi_penjualan_produk_fk,
                                                                  @Field("jumlah_produk") Integer jumlah_produk,
                                                                  @Field("id_produk_penjualan_fk") Integer id_produk_penjualan_fk);

    @FormUrlEncoded
    @POST("penjualan_produk/update/{id_transaksi_penjualan_produk}")
    Call<ResponPenjualanProduk> updatePenjualanProduk(@Path("id_transaksi_penjualan_produk") String id_transaksi_penjualan_produk,
                                                      @Field("status_penjualan") String status_penjualan,
                                                      @Field("kode_transaksi_penjualan_produk") String kode_transaksi_penjualan_produk);


    //////////////////////////////////////////////////////// SOFT DELETE DATA   //////////////////////////////////////////////////////
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

    @POST("jasa_layanan/delete/{id_jasa_layanan}")
    Call<ResponLayanan> deleteLayanan(@Path("id_jasa_layanan") String id_jasa_layanan);

    @POST("supplier/delete/{id_supplier}")
    Call<ResponSupplier> deleteSupplier(@Path("id_supplier") String id_supplier);

    @POST("produk/delete/{id_produk}")
    Call<ResponProduk> deleteProduk(@Path("id_produk") String id_produk);

    @FormUrlEncoded
    @POST("pengadaan/delete/{id_pengadaan}")
    Call<ResponPengadaan> deletePengadaan(@Path("id_pengadaan") String id_pengadaan,
                                          @Field("kode_pengadaan") String kode_pengadaan);

    @FormUrlEncoded
    @POST("detail_pengadaan/delete/{id_detail_pengadaan}")
    Call<ResponPengadaanDetail> deletePengadaanDetail(@Path("id_detail_pengadaan") String id_detail_pengadaan,
                                                      @Field("kode_pengadaan_fk") String kode_pengadaan_fk);

    @FormUrlEncoded
    @POST("penjualan_produk/delete/{id_transaksi_penjualan_produk}")
    Call<ResponPenjualanProduk> deletePenjualanProduk(@Path("id_transaksi_penjualan_produk") String id_transaksi_penjualan_produk,
                                                      @Field("kode_transaksi_penjualan_produk") String kode_transaksi_penjualan_produk);

    @FormUrlEncoded
    @POST("detail_penjualan_produk/delete/{id_detail_penjualan_produk}")
    Call<ResponPenjualanProdukDetail> deleteDetailPenjualanProduk(@Path("id_detail_penjualan_produk") String id_detail_penjualan_produk,
                                                                  @Field("kode_transaksi_penjualan_produk") String kode_transaksi_penjualan_produk);


    ////////////////////////////////////////////////////// RESTORE ////////////////////////////////////////////////////
    @POST("ukuran_hewan/restore/{id_ukuran_hewan}")
    Call<ResponUkuranHewan> restoreUkuranHewan(@Path("id_ukuran_hewan") String id_ukuran_hewan);

    @POST("jenis_hewan/restore/{id_jenis_hewan}")
    Call<ResponJenisHewan> restoreJenisHewan(@Path("id_jenis_hewan") String id_jenis_hewan);

    @POST("customer/restore/{id_customer}")
    Call<ResponCustomer> restoreCustomer(@Path("id_customer") String id_customer);

    @POST("pegawai/restore/{id_pegawai}")
    Call<ResponPegawai> restorePegawai(@Path("id_pegawai") String id_pegawai);

    @POST("hewan/restore/{id_hewan}")
    Call<ResponHewan> restoreHewan(@Path("id_hewan") String id_hewan);

    @POST("jasa_layanan/restore/{id_jasa_layanan}")
    Call<ResponLayanan> restoreLayanan(@Path("id_jasa_layanan") String id_jasa_layanan);

    @POST("supplier/restore/{id_supplier}")
    Call<ResponSupplier> restoreSupplier(@Path("id_supplier") String id_supplier);

    @POST("produk/restore/{id_produk}")
    Call<ResponProduk> restoreProduk(@Path("id_produk") String id_produk);


    ///////////////////////////////////////////////////////// DELETE PERMANENT  //////////////////////////////////////////////////////
    @POST("ukuran_hewan/deletePermanent/{id_ukuran_hewan}")
    Call<ResponUkuranHewan> deletePermanentUkuranHewan(@Path("id_ukuran_hewan") String id_ukuran_hewan);

    @POST("jenis_hewan/deletePermanent/{id_jenis_hewan}")
    Call<ResponJenisHewan> deletePermanentJenisHewan(@Path("id_jenis_hewan") String id_jenis_hewan);

    @POST("customer/deletePermanent/{id_customer}")
    Call<ResponCustomer> deletePermanentCustomer(@Path("id_customer") String id_customer);

    @POST("pegawai/deletePermanent/{id_pegawai}")
    Call<ResponPegawai> deletePermanentPegawai(@Path("id_pegawai") String id_pegawai);

    @POST("hewan/deletePermanent/{id_hewan}")
    Call<ResponHewan> deletePermanentHewan(@Path("id_hewan") String id_hewan);

    @POST("jasa_layanan/deletePermanent/{id_jasa_layanan}")
    Call<ResponLayanan> deletePermanentLayanan(@Path("id_jasa_layanan") String id_jasa_layanan);

    @POST("supplier/deletePermanent/{id_supplier}")
    Call<ResponSupplier> deletePermanentSupplier(@Path("id_supplier") String id_supplier);

    @POST("produk/deletePermanent/{id_produk}")
    Call<ResponProduk> deletePermanentProduk(@Path("id_produk") String id_produk);


}
