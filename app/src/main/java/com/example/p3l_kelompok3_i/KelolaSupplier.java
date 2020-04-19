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
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_supplier.ResponSupplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaSupplier extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText nama_supplier, alamat_supplier, nohp_supplier;
    Button btnTampil, btnCreate, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_supplier);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_supplier = (EditText) findViewById(R.id.nama_supplier);
        alamat_supplier = (EditText) findViewById(R.id.alamat_supplier);
        nohp_supplier = (EditText) findViewById(R.id.nomor_hp_supplier);
        btnCreate = (Button) findViewById(R.id.btnTambahSupplier);
        btnTampil = findViewById(R.id.btnTampilSupplierKelola);
        btnDelete = findViewById(R.id.btnDeleteSupplier);
        btnUpdate = (Button) findViewById(R.id.btnUpdateSupplier);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_supplier");
        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            nama_supplier.setText(data.getStringExtra("nama_supplier"));
            alamat_supplier.setText(data.getStringExtra("alamat_supplier"));
            nohp_supplier.setText(data.getStringExtra("nomor_telepon_supplier"));
        }

        pd = new ProgressDialog(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaSupplier.this, TampilSupplier.class);
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
                Call<ResponSupplier> deleteSupplier = api.deleteSupplier(iddata);

                deleteSupplier.enqueue(new Callback<ResponSupplier>() {
                    @Override
                    public void onResponse(Call<ResponSupplier> call, Response<ResponSupplier> response) {
                        ResponSupplier res = response.body();
                        if (res.getMessage().equals("DATA INI SEDANG DIGUNAKAN!")) {
                            pd.hide();
                            Toast.makeText(KelolaSupplier.this, "Gagal Hapus Data Masih Digunakan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("RETRO", "response: " + "Berhasil Delete");
                            Intent intent = new Intent(KelolaSupplier.this, TampilSupplier.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaSupplier.this, "Sukses Hapus Supplier!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponSupplier> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaSupplier.this, "Gagal Hapus Supplier!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_supplier.getText().toString();
                String salamat = alamat_supplier.getText().toString();
                String snohp = nohp_supplier.getText().toString();

                if (snama.trim().equals("") || salamat.trim().equals("") || snohp.trim().equals("")) {
                    Toast.makeText(KelolaSupplier.this, "Data Supplier Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponSupplier> updateSupplier = api.updateSupplier(iddata, snama,salamat,snohp);
                    updateSupplier.enqueue(new Callback<ResponSupplier>() {
                        @Override
                        public void onResponse(Call<ResponSupplier> call, Response<ResponSupplier> response) {
                            pd.dismiss();
                            ResponSupplier res = response.body();
                            if (res.getMessage().equals("Gagal, Supplier sudah Terdaftar!")) {
                                Toast.makeText(KelolaSupplier.this, "Supplier Sudah Terdaftar!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("RETRO", "response: " + "Berhasil Update");
                                Intent intent = new Intent(KelolaSupplier.this, TampilSupplier.class);
                                pd.hide();
                                startActivity(intent);
                                Toast.makeText(KelolaSupplier.this, "Sukses Edit Data Supplier!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponSupplier> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaSupplier.this, "Gagal Edit Data Supplier!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_supplier.getText().toString();
                String salamat = alamat_supplier.getText().toString();
                String snohp = nohp_supplier.getText().toString();

                if (snama.trim().equals("") || salamat.trim().equals("") || snohp.trim().equals("")) {
                    Toast.makeText(KelolaSupplier.this, "Data Supplier Belum Lengkap", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponSupplier> sendSupplier = api.sendSupplier(snama,salamat,snohp);
                    sendSupplier.enqueue(new Callback<ResponSupplier>() {
                        @Override
                        public void onResponse(Call<ResponSupplier> call, Response<ResponSupplier> response) {
                            pd.dismiss();
                            ResponSupplier res = response.body();
                            if (res.getMessage().equals("GAGAL, SUPPLIER SUDAH ADA!")) {
                                Toast.makeText(KelolaSupplier.this, "Supplier Sudah Terdaftar!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("RETRO", "response: " + "Berhasil Create");
                                Intent intent = new Intent(KelolaSupplier.this, TampilSupplier.class);
                                pd.hide();
                                startActivity(intent);
                                Toast.makeText(KelolaSupplier.this, "Sukses Tambah Data Supplier!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponSupplier> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaSupplier.this, "Gagal Tambah Data Supplier!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Create");
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
                pd.dismiss();
                Intent intent = new Intent(KelolaSupplier.this, MenuAdminTransaksi.class);
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
        Intent intent = new Intent(this, MenuAdminTransaksi.class);
        startActivity(intent);
    }
}
