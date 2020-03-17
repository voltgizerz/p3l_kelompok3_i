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

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaCustomer extends AppCompatActivity {
    EditText nama_customer, alamat_customer,  nomor_hp_customer, tanggal_lahir_customer;
    Button btncreate, btnTampilCustomer;
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

        pd = new ProgressDialog(this);


        btnTampilCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaCustomer.this, TampilCustomer.class);
                startActivity(i);
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