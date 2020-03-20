package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterHewan;
import com.example.p3l_kelompok3_i.adapter.AdapterJenisHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_hewan.DataHewan;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaHewan extends AppCompatActivity {

    private AdapterJenisHewan mAdapterJenisHewan;
    private List<DataJenisHewan> mItems = new ArrayList<>();

    EditText nama_hewan,id_jenis_hewan, id_ukuran_hewan, id_customer, tanggal_lahir_hewan;
    Button btncreate, btnTampilHewan, btnUpdate;
    String iddata;
    private Spinner spinner;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_hewan = (EditText) findViewById(R.id.data_nama_hewan);
        id_ukuran_hewan = (EditText) findViewById(R.id.data_id_ukuran_hewan);
        id_customer = (EditText) findViewById(R.id.data_id_customer);
        id_jenis_hewan = (EditText) findViewById(R.id.data_id_jenis_hewan);
        tanggal_lahir_hewan = (EditText) findViewById(R.id.data_tanggal_lahir_hewan);
        btncreate = (Button) findViewById(R.id.btnTambahHewanKelola);
        btnTampilHewan = findViewById(R.id.btnTampilHewanKelola);

        btnUpdate = (Button) findViewById(R.id.btnUpdateHewan) ;

        Intent data = getIntent();
        iddata = data.getStringExtra("id_hewan");
        if(iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilHewan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            nama_hewan.setText(data.getStringExtra("nama_hewan"));
        }

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

                String snama = nama_hewan.getText().toString();
                String sjenis = id_jenis_hewan.getText().toString();
                String sukuran = id_ukuran_hewan.getText().toString();
                String scustomer = id_customer.getText().toString();
                String stanggal = tanggal_lahir_hewan.getText().toString();

                if (snama.trim().equals("") || sjenis.trim().equals("")) {
                    Toast.makeText(KelolaHewan.this, "Masih Ada Data yang Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponHewan> sendh= api.sendHewan(snama,sjenis,sukuran,scustomer,stanggal);
                    sendh.enqueue(new Callback<ResponHewan>() {
                        @Override
                        public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                            pd.hide();
                            Toast.makeText(KelolaHewan.this, "BANGSAT KAU!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "response: " + "Berhasil Mendaftar");

                        }
                        @Override
                        public void onFailure(Call<ResponHewan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaHewan.this, "Gagal Tambah Data Jenis Hewan!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(KelolaHewan.this, MenuAdmin.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}