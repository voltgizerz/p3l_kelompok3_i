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
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaHewan extends AppCompatActivity {

    EditText nama_hewan, id_jenis_hewan,  id_ukuran_hewan, id_customer, tanggal_lahir_hewan;
    Button btncreate, btnTampilHewan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_hewan = (EditText) findViewById(R.id.nama_hewan);
        id_jenis_hewan = (EditText) findViewById(R.id.id_jenis_hewan);
        id_ukuran_hewan = (EditText) findViewById(R.id.id_ukuran_hewan);
        id_customer = (EditText) findViewById(R.id.id_customer);
        tanggal_lahir_hewan = (EditText) findViewById(R.id.tanggal_lahir_hewan);
        btncreate = (Button) findViewById(R.id.btnTambahHewanKelola);
        btnTampilHewan = findViewById(R.id.btnTampilHewanKelola);

        pd = new ProgressDialog(this);


        btnTampilHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaHewan.this, TampilHewan.class);
                startActivity(i);
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("creating data..");
                pd.setCancelable(false);
                pd.show();
                String snama = nama_hewan.getText().toString();
                String sjenis = id_jenis_hewan.getText().toString();
                String sukuran = id_ukuran_hewan.getText().toString();
                String scustomer = id_customer.getText().toString();
                String stanggal = tanggal_lahir_hewan.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponHewan> sendhewan = api.sendHewan(snama,sjenis,sukuran,scustomer,stanggal);
                sendhewan.enqueue(new Callback<ResponHewan>() {
                    @Override
                    public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                        pd.hide();
                        Toast.makeText(KelolaHewan.this, "Sukses Tambah Data Hewan!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "response: " + "Berhasil Mendaftar");
                    }

                    @Override
                    public void onFailure(Call<ResponHewan> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaHewan.this, "Gagal Tambah Data Hewan!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Mendaftar");

                    }
                });

            }
        });

    }
}