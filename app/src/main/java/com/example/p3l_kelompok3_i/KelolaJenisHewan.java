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
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaJenisHewan extends AppCompatActivity {

    EditText nama_jenis_hewan;
    Button btncreate, btnTampilJenisHewanKelola, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_jenis_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_jenis_hewan = (EditText) findViewById(R.id.nama_jenis_hewan);
        btncreate = (Button) findViewById(R.id.btn_create_jenis_hewan);
        btnTampilJenisHewanKelola = (Button) findViewById(R.id.btnTampilJenisHewanKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateJH);
        btnDelete = (Button) findViewById(R.id.btnDeleteJH);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_jenis_hewan");
        if (iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilJenisHewanKelola.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            nama_jenis_hewan.setText(data.getStringExtra("nama_jenis_hewan"));
        }
        pd = new ProgressDialog(this);


        btnTampilJenisHewanKelola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaJenisHewan.this, TampilJenisHewan.class);
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
                        Intent intent = new Intent(KelolaJenisHewan.this, TampilJenisHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaJenisHewan.this, "Sukses Hapus Jenis Hewan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaJenisHewan.this, "Gagal Hapus Jenis Hewan!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_jenis_hewan.getText().toString();
                if (snama.trim().equals("")) {
                    Toast.makeText(KelolaJenisHewan.this, "Nama Jenis Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponJenisHewan> updatejh = api.updateJenisHewan(iddata, nama_jenis_hewan.getText().toString());
                    updatejh.enqueue(new Callback<ResponJenisHewan>() {
                        @Override
                        public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaJenisHewan.this, TampilJenisHewan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaJenisHewan.this, "Sukses Edit Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaJenisHewan.this, "Gagal Edit Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_jenis_hewan.getText().toString();

                if (snama.trim().equals("")) {
                    Toast.makeText(KelolaJenisHewan.this, "Nama Jenis Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponJenisHewan> sendjenishewan = api.sendJenisHewan(snama);
                    sendjenishewan.enqueue(new Callback<ResponJenisHewan>() {
                        @Override
                        public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                            pd.hide();
                            Toast.makeText(KelolaJenisHewan.this, "Sukses Tambah Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "response: " + "Berhasil Mendaftar");

                        }

                        @Override
                        public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaJenisHewan.this, "Gagal Tambah Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Mendaftar");
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
                Intent intent = new Intent(KelolaJenisHewan.this, MenuAdmin.class);
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
