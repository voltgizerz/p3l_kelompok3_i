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

public class KelolaUkuranHewan extends AppCompatActivity {

    EditText ukuran_hewan;
    Button btncreate, btnTampilUkuranHewan, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_ukuran_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ukuran_hewan = (EditText) findViewById(R.id.ukuran_hewan);
        btncreate = (Button) findViewById(R.id.btnTambahUkuranHewan);
        btnTampilUkuranHewan = (Button) findViewById(R.id.btnTampilUkuranHewan);
        btnUpdate = (Button) findViewById(R.id.btnUpdateUH);
        btnDelete = (Button) findViewById(R.id.btnHapusUH);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_ukuran_hewan");
        if (iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilUkuranHewan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            ukuran_hewan.setText(data.getStringExtra("ukuran_hewan"));
        }

        pd = new ProgressDialog(this);

        btnTampilUkuranHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaUkuranHewan.this, TampilUkuranHewan.class);
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
                Call<ResponUkuranHewan> deleteuh = api.deleteUkuranHewan(iddata);

                deleteuh.enqueue(new Callback<ResponUkuranHewan>() {
                    @Override
                    public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaUkuranHewan.this, TampilUkuranHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaUkuranHewan.this, "Sukses Hapus Ukuran Hewan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaUkuranHewan.this, "Gagal Hapus Ukuran Hewan!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sukuran = ukuran_hewan.getText().toString();
                if (sukuran.trim().equals("")) {
                    Toast.makeText(KelolaUkuranHewan.this, "Ukuran Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponUkuranHewan> updateuh = api.updateUkuranHewan(iddata, ukuran_hewan.getText().toString());
                    updateuh.enqueue(new Callback<ResponUkuranHewan>() {
                        @Override
                        public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaUkuranHewan.this, TampilUkuranHewan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaUkuranHewan.this, "Sukses Edit Data Ukuran Hewan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaUkuranHewan.this, "Gagal Edit Data Ukuran Hewan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sukuran = ukuran_hewan.getText().toString();

                if (sukuran.trim().equals("")) {
                    Toast.makeText(KelolaUkuranHewan.this, "Ukuran Hewan Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    pd.setMessage("creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponUkuranHewan> sendukuranhewan = api.sendUkuranHewan(sukuran);
                    sendukuranhewan.enqueue(new Callback<ResponUkuranHewan>() {
                        @Override
                        public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                            pd.hide();
                            Toast.makeText(KelolaUkuranHewan.this, "Sukses Tambah Data Ukuran Hewan!", Toast.LENGTH_SHORT).show();

                            Log.d("RETRO", "response: " + "Berhasil Mendaftar");
                        }

                        @Override
                        public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaUkuranHewan.this, "Gagal Tambah Data Ukuran Hewan!", Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(KelolaUkuranHewan.this, MenuAdmin.class);
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
