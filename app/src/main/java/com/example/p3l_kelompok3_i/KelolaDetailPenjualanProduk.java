package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.example.p3l_kelompok3_i.penjualan_produk_detail.ResponPenjualanProdukDetail;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDetailPenjualanProduk extends AppCompatActivity {

    private List<DataProduk> mItemsProduk = new ArrayList<>();
    ProgressDialog pd;
    Spinner spinnerProduk;
    String iddata;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    EditText etJumlahProduk;
    TextView tvKode;
    Integer dataIdProduk;
    private static SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_detail_penjualan_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanProduk", 0);
        final String cookieName = prefs.getString("kode_penjualan_produk", null);

        spinnerProduk = (Spinner) findViewById(R.id.spinnerProdukPenjualan);
        pd = new ProgressDialog(this);
        btnTampil = (Button) findViewById(R.id.btnTampilProdukDetailPenjualan);
        btnCreate = (Button) findViewById(R.id.btnTambahProdukDetailPenjualan);
        btnDelete = (Button) findViewById(R.id.btnDeleteProdukDetailPenjualan);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProdukDetailPenjualan);
        etJumlahProduk = findViewById(R.id.jumlah_penjualan_produk_detail);
        tvKode = (TextView) findViewById(R.id.tampilKodeTransaksiDetailPenjualanProduk);
        tvKode.setText(cookieName);
        Intent data = getIntent();
        iddata = data.getStringExtra("id_detail_transaksi_penjualan_produk");
        dataIdProduk = data.getIntExtra("id_produk_fk", 0);

        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            etJumlahProduk.setText(String.valueOf(data.getIntExtra("jumlah_produk", 0)));
        }

        //GET PRODUK UNTUK SPINNER PRODUK
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponProduk> getProduk = api.getProdukSemua();

        getProduk.enqueue(new Callback<ResponProduk>() {
            @Override
            public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                mItemsProduk = response.body().getData();
                Collections.sort(mItemsProduk, DataProduk.BY_NAME_ALPAHBETICAL);
                //ADD DATA HANYA UNTUK HINT SPINNER
                int position = -1;
                for (int i = 0; i < mItemsProduk.size(); i++) {
                    if (mItemsProduk.get(i).getId_produk().equals(Integer.toString(dataIdProduk))) {
                        position = i + 1;
                        // break;  // uncomment to get the first instance
                    }
                }

                Log.d("[POSISI ID Produk] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API PRODUK  " + response.body().getData());
                mItemsProduk.add(0, new DataProduk("Pilih Produk Ingin Dibeli", "0", null));
                //SPINNER UNTUK ID SUPPLIER
                ArrayAdapter<DataProduk> adapter = new ArrayAdapter<DataProduk>(KelolaDetailPenjualanProduk.this, R.layout.spinner, mItemsProduk);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerProduk.setAdapter(adapter);
                spinnerProduk.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponProduk> call, Throwable t) {
                pd.hide();
                if (isInternetAvailable() == false) {
                    Log.d("API", "RESPONSE : TIDAK ADA INTERNET! ");
                } else {
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PRODUK! ");
                }

            }
        });

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
                startActivity(i);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPenjualanProdukDetail> deleteDetailPenjualanProduk = api.deleteDetailPenjualanProduk(iddata, cookieName);

                deleteDetailPenjualanProduk.enqueue(new Callback<ResponPenjualanProdukDetail>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanProdukDetail> call, Response<ResponPenjualanProdukDetail> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaDetailPenjualanProduk.this, "Sukses Hapus Produk!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanProdukDetail> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaDetailPenjualanProduk.this, "Gagal Hapus Produk!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProduk spnProduk = (DataProduk) spinnerProduk.getSelectedItem();

                if (spinnerProduk.getSelectedItem() == null || spinnerProduk.getSelectedItem().toString().equals("Pilih Produk Pengadaan") || etJumlahProduk.getText().toString().equals("")) {
                    Toast.makeText(KelolaDetailPenjualanProduk.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    int position = -1;
                    for (int i = 0; i < mItemsProduk.size(); i++) {
                        if (mItemsProduk.get(i).getId_produk().equals(spnProduk.getId_produk())) {
                            position = i;
                            if(mItemsProduk.get(position).getStok_produk() == 0) {
                                Toast.makeText(KelolaDetailPenjualanProduk.this, "Stok Produk Ini Sedang Kosong!", Toast.LENGTH_SHORT).show();
                                return;
                            }else if (mItemsProduk.get(position).getStok_produk() < Integer.parseInt(etJumlahProduk.getText().toString())) {
                                Toast.makeText(KelolaDetailPenjualanProduk.this, "Stok Produk Tersedia Hanya : "+mItemsProduk.get(position).getStok_produk(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // break;  // uncomment to get the first instance
                        }
                    }

                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_produk = spnProduk.getId_produk();
                    Integer sidproduk = Integer.parseInt(id_produk);
                    Integer sjumlah = Integer.parseInt(etJumlahProduk.getText().toString());

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPenjualanProdukDetail> updatePenjualanProdukDetail = api.updatePenjualanProdukDetail(iddata, cookieName, sjumlah, sidproduk);

                    updatePenjualanProdukDetail.enqueue(new Callback<ResponPenjualanProdukDetail>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanProdukDetail> call, Response<ResponPenjualanProdukDetail> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPenjualanProduk.this, "Sukses Update Produk Penjualan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPenjualanProdukDetail> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaDetailPenjualanProduk.this, "Gagal Update Produk Penjualan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProduk spnProduk = (DataProduk) spinnerProduk.getSelectedItem();

                if (spnProduk.getId_produk() == null || spinnerProduk.getSelectedItem() == null || spinnerProduk.getSelectedItem().toString().equals("Pilih Produk Ingin Dibeli") || etJumlahProduk.getText().toString().equals("")) {
                    Toast.makeText(KelolaDetailPenjualanProduk.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.d("ghfh","gjf"+spnProduk );
                    int position = -1;
                    for (int i = 0; i < mItemsProduk.size(); i++) {
                        if (mItemsProduk.get(i).getId_produk().equals(spnProduk.getId_produk())) {
                            position = i;

                           if(mItemsProduk.get(position).getStok_produk() == 0) {
                                Toast.makeText(KelolaDetailPenjualanProduk.this, "Stok Produk Ini Sedang Kosong!", Toast.LENGTH_SHORT).show();
                                return;
                            }else if (mItemsProduk.get(position).getStok_produk() < Integer.parseInt(etJumlahProduk.getText().toString())) {
                               Toast.makeText(KelolaDetailPenjualanProduk.this, "Stok Produk Tersedia Hanya : "+mItemsProduk.get(position).getStok_produk(), Toast.LENGTH_SHORT).show();
                               return;
                           }
                        }
                    }

                    pd.setMessage("Creating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_produk = spnProduk.getId_produk();
                    Integer sidproduk = Integer.parseInt(id_produk);
                    Integer sjumlah = Integer.parseInt(etJumlahProduk.getText().toString());

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPenjualanProdukDetail> createPenjualanProdukDetail = api.sendPenjualanProdukDetail(cookieName, sidproduk, sjumlah);

                    createPenjualanProdukDetail.enqueue(new Callback<ResponPenjualanProdukDetail>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanProdukDetail> call, Response<ResponPenjualanProdukDetail> response) {
                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPenjualanProduk.this, "Sukses Tambah Produk Penjualan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPenjualanProdukDetail> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Create");
                            pd.hide();
                            Toast.makeText(KelolaDetailPenjualanProduk.this, "Gagal Tambah Produk Penjualan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaPenjualanProduk.class);
        startActivity(intent);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
