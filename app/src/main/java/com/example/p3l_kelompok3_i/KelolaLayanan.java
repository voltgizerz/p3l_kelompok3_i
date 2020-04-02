package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaLayanan extends AppCompatActivity {

    EditText nama_layanan, harga_layanan, jenis_layanan_hewan, ukuran_layanan_hewan;
    Button btncreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_layanan = (EditText) findViewById(R.id.data_nama_layanan);
        harga_layanan = (EditText) findViewById(R.id.data_harga_layanan);
        jenis_layanan_hewan = (EditText) findViewById(R.id.data_id_jenis_hewan);
        ukuran_layanan_hewan = (EditText) findViewById(R.id.data_id_ukuran_hewan);

        btncreate = (Button) findViewById(R.id.btnTambahLayananKelola);
        btnTampil = (Button) findViewById(R.id.btnTampilLayananKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateLayanan);
        btnDelete = (Button) findViewById(R.id.btnDeleteLayanan);


        pd = new ProgressDialog(this);


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaLayanan.this, TampilLayanan.class);
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
                Call<ResponJenisHewan> deletejh = api.deleteJenisHewan(iddata);

                deletejh.enqueue(new Callback<ResponJenisHewan>() {
                    @Override
                    public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaLayanan.this, TampilJenisHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaLayanan.this, "Sukses Hapus Jenis Hewan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaLayanan.this, "Gagal Hapus Jenis Hewan!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_layanan.getText().toString();
                if (snama.trim().equals("")) {
                    Toast.makeText(KelolaLayanan.this, "Nama Jenis Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponJenisHewan> updatejh = api.updateJenisHewan(iddata, nama_layanan.getText().toString());
                    updatejh.enqueue(new Callback<ResponJenisHewan>() {
                        @Override
                        public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaLayanan.this, TampilJenisHewan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaLayanan.this, "Sukses Edit Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Edit Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_layanan.getText().toString();
                String sharga = harga_layanan.getText().toString();
                String sjenis = jenis_layanan_hewan.getText().toString();
                String sukuran = ukuran_layanan_hewan.getText().toString();

                if (snama.trim().equals("")) {
                    Toast.makeText(KelolaLayanan.this, "Nama Jenis Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponLayanan> sendlayanan = api.sendLayanan(snama,sharga,sjenis,sukuran);
                    sendlayanan.enqueue(new Callback<ResponLayanan>() {
                        @Override
                        public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Sukses Tambah Data Jasa Layanan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "response: " + "Berhasil Tambah Data");

                        }

                        @Override
                        public void onFailure(Call<ResponLayanan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Tambah Data Jasa Layanan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Tambah Data");
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
                Intent intent = new Intent(KelolaLayanan.this, MenuAdmin.class);
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
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }
}