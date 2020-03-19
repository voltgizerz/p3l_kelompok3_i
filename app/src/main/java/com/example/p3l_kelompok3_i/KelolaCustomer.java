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
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaCustomer extends AppCompatActivity {
    EditText nama_customer, alamat_customer,  nomor_hp_customer, tanggal_lahir_customer;
    Button btncreate, btnTampilCustomer, btnUpdate;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_customer = (EditText) findViewById(R.id.nama_customer);
        alamat_customer = (EditText) findViewById(R.id.alamat_customer);
        tanggal_lahir_customer = (EditText) findViewById(R.id.tanggal_lahir_customer);
        nomor_hp_customer = (EditText) findViewById(R.id.nomor_hp_customer);
        btncreate = (Button) findViewById(R.id.btn_create_customer);
        btnTampilCustomer = findViewById(R.id.btnTampilCustomerKelola);

        btnUpdate = (Button) findViewById(R.id.btnUpdateCustomer) ;

        Intent data = getIntent();
        iddata = data.getStringExtra("id_customer");
        if(iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilCustomer.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            nama_customer.setText(data.getStringExtra("nama_customer"));
            alamat_customer.setText(data.getStringExtra("alamat_customer"));
            tanggal_lahir_customer.setText(data.getStringExtra("tanggal_lahir_customer"));
            nomor_hp_customer.setText(data.getStringExtra("nomor_hp_customer"));
        }

        pd = new ProgressDialog(this);


        btnTampilCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaCustomer.this, TampilCustomer.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Updating....");
                pd.setCancelable(false);
                pd.show();
                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponCustomer> updatecus = api.updateCustomer(iddata, nama_customer.getText().toString(), alamat_customer.getText().toString(),tanggal_lahir_customer.getText().toString(), nomor_hp_customer.getText().toString());
                updatecus.enqueue(new Callback<ResponCustomer>() {
                    @Override
                    public void onResponse(Call<ResponCustomer> call, Response<ResponCustomer> response) {
                        Log.d("RETRO", "response: " + "Berhasil Update");
                        Intent intent = new Intent(KelolaCustomer.this, TampilCustomer.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaCustomer.this, "Sukses Edit Data Customer!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponCustomer> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Update");
                        pd.hide();
                        Toast.makeText(KelolaCustomer.this, "Gagal Edit Data Customer!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("creating data..");
                pd.setCancelable(false);
                pd.show();
                String snama = nama_customer.getText().toString();
                String salamat = alamat_customer.getText().toString();
                String stanggal_lahir = tanggal_lahir_customer.getText().toString();
                String sno_hp = nomor_hp_customer.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponCustomer> sendcustomer = api.sendCustomer(snama,salamat,stanggal_lahir,sno_hp);
                sendcustomer.enqueue(new Callback<ResponCustomer>() {
                    @Override
                    public void onResponse(Call<ResponCustomer> call, Response<ResponCustomer> response) {
                        pd.hide();
                        Toast.makeText(KelolaCustomer.this, "Sukses Tambah Data Customer!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "response: " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponCustomer> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaCustomer.this, "Gagal Tambah Data Customer!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Mendaftar");

                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaCustomer.this, MenuAdmin.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}