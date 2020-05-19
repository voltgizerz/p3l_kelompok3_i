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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.penjualan_layanan_detail.ResponPenjualanLayananDetail;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDetailPenjualanLayanan extends AppCompatActivity {

    private List<DataLayanan> mItemsLayanan = new ArrayList<>();
    private List<DataLayanan> saringLayanan = new ArrayList<>();
    ProgressDialog pd;
    Spinner spinnerLayanan;
    String iddata;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    TextView tvKode;
    Integer dataIdLayanan;
    private static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_detail_penjualan_layanan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanLayanan", 0);
        final String cookieName = prefs.getString("kode_penjualan_layanan", null);

        spinnerLayanan = (Spinner) findViewById(R.id.spinnerJasaLayananPenjualan);
        pd = new ProgressDialog(this);
        btnTampil = (Button) findViewById(R.id.btnTampilLayananDetailPenjualan);
        btnCreate = (Button) findViewById(R.id.btnTambahLayananDetailPenjualan);
        btnDelete = (Button) findViewById(R.id.btnDeleteLayananDetailPenjualan);
        btnUpdate = (Button) findViewById(R.id.btnUpdateLayananDetailPenjualan);
        tvKode = (TextView) findViewById(R.id.tampilKodeTransaksiDetailPenjualanLayanan);
        tvKode.setText(cookieName);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_detail_transaksi_penjualan_layanan");
        dataIdLayanan = data.getIntExtra("id_jasa_layanan_fk", 0);

        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        }

        //GET LAYANAN UNTUK SPINNER
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponLayanan> getLayanan = api.getJasaLayananSemua();

        getLayanan.enqueue(new Callback<ResponLayanan>() {
            @Override
            public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                mItemsLayanan = response.body().getData();
                List<DataLayanan> a = mItemsLayanan;
                for(DataLayanan data : a){
                    if(!data.getCreated_date().equals("0000-00-00 00:00:00") ){
                        saringLayanan.add(data);
                    }
                }
                Collections.sort(saringLayanan, DataLayanan.BY_NAME_ALPAHBETICAL);
                //ADD DATA HANYA UNTUK HINT SPINNER
                int position = -1;
                for (int i = 0; i < saringLayanan.size(); i++) {
                    if (saringLayanan.get(i).getId_jasa_layanan().equals(Integer.toString(dataIdLayanan))) {
                        position = i + 1;
                        // break;  // uncomment to get the first instance
                    }
                }

                saringLayanan.add(0, new DataLayanan("Pilih Jasa Layanan", "0","",""));
                //SPINNER UNTUK ID LAYANAN
                ArrayAdapter<DataLayanan> adapter = new ArrayAdapter<DataLayanan>(KelolaDetailPenjualanLayanan.this, R.layout.spinner, saringLayanan);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerLayanan.setAdapter(adapter);
                spinnerLayanan.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponLayanan> call, Throwable t) {
                pd.hide();
                if (isInternetAvailable() == false) {
                    Log.d("API", "RESPONSE : TIDAK ADA INTERNET! ");
                } else {
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API LAYANAN! ");
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPenjualanLayananDetail> deleteDetailPenjualanLayanan = api.deleteDetailPenjualanLayanan(iddata, cookieName);
                deleteDetailPenjualanLayanan.enqueue(new Callback<ResponPenjualanLayananDetail>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanLayananDetail> call, Response<ResponPenjualanLayananDetail> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaDetailPenjualanLayanan.this, KelolaPenjualanLayanan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaDetailPenjualanLayanan.this, "Sukses Hapus Jasa Layanan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanLayananDetail> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaDetailPenjualanLayanan.this, "Gagal Hapus Jasa Layanan!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Updating...");
                pd.setCancelable(false);
                pd.show();
                Log.d("RETRO", "response: " + "logg"+iddata+cookieName);
                DataLayanan spinnerIdLayanan = (DataLayanan) spinnerLayanan.getSelectedItem();
                if (spinnerLayanan.getSelectedItem() == null || spinnerLayanan.getSelectedItem().toString().equals("Pilih Jasa Layanan  ")) {
                    pd.dismiss();
                    Toast.makeText(KelolaDetailPenjualanLayanan.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPenjualanLayananDetail> updatePenjualanLayananDetail = api.updatePenjualanLayananDetail(iddata,cookieName,1, Integer.parseInt(spinnerIdLayanan.getId_jasa_layanan()));
                    updatePenjualanLayananDetail.enqueue(new Callback<ResponPenjualanLayananDetail>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanLayananDetail> call, Response<ResponPenjualanLayananDetail> response) {
                            pd.dismiss();
                            ResponPenjualanLayananDetail res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaDetailPenjualanLayanan.this, KelolaPenjualanLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPenjualanLayanan.this, "Sukses Update Jasa Layanan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPenjualanLayananDetail> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaDetailPenjualanLayanan.this, "Gagal Update Jasa Layanan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Update Data");


                        }
                    });
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating...");
                pd.setCancelable(false);
                pd.show();
                DataLayanan spinnerIdLayanan = (DataLayanan) spinnerLayanan.getSelectedItem();
                if (spinnerLayanan.getSelectedItem() == null || spinnerLayanan.getSelectedItem().toString().equals("Pilih Jasa Layanan  ")) {
                    pd.dismiss();
                    Toast.makeText(KelolaDetailPenjualanLayanan.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPenjualanLayananDetail> sendPenjualanLayananDetail = api.sendPenjualanLayananDetail(cookieName, Integer.parseInt(spinnerIdLayanan.getId_jasa_layanan()),1);
                    sendPenjualanLayananDetail.enqueue(new Callback<ResponPenjualanLayananDetail>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanLayananDetail> call, Response<ResponPenjualanLayananDetail> response) {
                            pd.dismiss();
                            ResponPenjualanLayananDetail res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaDetailPenjualanLayanan.this, KelolaPenjualanLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPenjualanLayanan.this, "Sukses Tambah Jasa Layanan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPenjualanLayananDetail> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaDetailPenjualanLayanan.this, "Gagal Tambah Jasa Layanan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Tambah Data");


                        }
                    });
                }
            }
        });


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaDetailPenjualanLayanan.this, KelolaPenjualanLayanan.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaDetailPenjualanLayanan.this, KelolaPenjualanLayanan.class);
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
        Intent intent = new Intent(this, KelolaPenjualanLayanan.class);
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
