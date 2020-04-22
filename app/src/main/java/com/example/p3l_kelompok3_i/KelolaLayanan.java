package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_customer.DataCustomer;
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaLayanan extends AppCompatActivity {

    private List<DataJenisHewan> mItems = new ArrayList<>();
    private List<DataUkuranHewan> mItemsUkuran = new ArrayList<>();
    EditText nama_layanan, harga_layanan;
    Integer dataIdJenisHewan, dataIdUkuranHewan;
    Button btncreate, btnTampil, btnUpdate, btnDelete,btnRestore,btnDeletePerm;
    String iddata,iddatalog;
    private Spinner spinnerUH, spinnerJH;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_layanan = (EditText) findViewById(R.id.data_nama_layanan);
        harga_layanan = (EditText) findViewById(R.id.data_harga_layanan);
        spinnerJH = (Spinner) findViewById(R.id.spinnerKelolaLayananJH);
        spinnerUH = (Spinner) findViewById(R.id.spinnerKelolaLayananUH);

        btncreate = (Button) findViewById(R.id.btnTambahLayananKelola);
        btnTampil = (Button) findViewById(R.id.btnTampilLayananKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateLayanan);
        btnDelete = (Button) findViewById(R.id.btnDeleteLayanan);
        btnRestore =  (Button) findViewById(R.id.btnRestoreLayanan);
        btnDeletePerm = (Button) findViewById(R.id.btnDeletePermLayanan);
        pd = new ProgressDialog(this);

        final Intent data = getIntent();
        iddata = data.getStringExtra("id_jasa_layanan");
        iddatalog = data.getStringExtra("id_jasa_layanan_delete");
        dataIdJenisHewan = data.getIntExtra("id_jenis_hewan", 0);
        dataIdUkuranHewan = data.getIntExtra("id_ukuran_hewan", 0);

        if (iddatalog != null) {
            btncreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnRestore.setVisibility(View.VISIBLE);
            btnDeletePerm.setVisibility(View.VISIBLE);

            nama_layanan.setText(data.getStringExtra("nama_jasa_layanan"));
            harga_layanan.setText(data.getStringExtra("harga_jasa_layanan"));
        }


        if (iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            nama_layanan.setText(data.getStringExtra("nama_jasa_layanan"));
            harga_layanan.setText(data.getStringExtra("harga_jasa_layanan"));
        }

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponJenisHewan> getJenisHewan = api.getJenisHewanSemua();

        getJenisHewan.enqueue(new Callback<ResponJenisHewan>() {
            @Override
            public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                mItems = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                mItems.add(0, new DataJenisHewan("Pilih Jenis Hewan", "0"));

                int position = -1;
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getId_jenis_hewan().equals(Integer.toString(dataIdJenisHewan))) {
                        position = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID JENIS HEWAN] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID JENIS HEWAN
                ArrayAdapter<DataJenisHewan> adapter = new ArrayAdapter<DataJenisHewan>(KelolaLayanan.this, R.layout.spinner, mItems);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerJH.setAdapter(adapter);
                spinnerJH.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API JENIS HEWAN! ");
            }
        });

        Call<ResponUkuranHewan> getUkuranHewan = api.getUkuranHewanSemua();

        getUkuranHewan.enqueue(new Callback<ResponUkuranHewan>() {
            @Override
            public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                mItemsUkuran = response.body().getData();

                //ADD DATA HANYA UNTUK HINT SPINNER
                mItemsUkuran.add(0, new DataUkuranHewan("Pilih Ukuran Hewan", "0"));

                int positionUH = -1;
                for (int i = 0; i < mItemsUkuran.size(); i++) {
                    if (mItemsUkuran.get(i).getId_ukuran_hewan().equals(Integer.toString(dataIdUkuranHewan))) {
                        positionUH = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID UKURAN HEWAN] :" + Integer.toString(positionUH), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID UKURAN HEWAN
                ArrayAdapter<DataUkuranHewan> adapter2 = new ArrayAdapter<DataUkuranHewan>(KelolaLayanan.this, R.layout.spinner, mItemsUkuran);
                adapter2.setDropDownViewResource(R.layout.spinner);
                spinnerUH.setAdapter(adapter2);
                spinnerUH.setSelection(positionUH, true);
            }

            @Override
            public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API UKURAN HEWAN! ");
            }
        });


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaLayanan.this, TampilLayanan.class);
                startActivity(i);
            }
        });

        btnDeletePerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponLayanan> deletePermanentlayanan = api.deletePermanentLayanan(iddatalog);

                deletePermanentlayanan.enqueue(new Callback<ResponLayanan>() {
                    @Override
                    public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                        ResponLayanan res = response.body();
                        if (res.getMessage().equals("DATA INI SEDANG DIGUNAKAN!")) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Hapus Data Masih Digunakan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("RETRO", "response: " + "Berhasil Delete");
                            Intent intent = new Intent(KelolaLayanan.this, TampilLogLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaLayanan.this, "Sukses Delete Permanent Layanan!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponLayanan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaLayanan.this, "Gagal Delete Permanent Layanan!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Restoring....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponLayanan> restorelayanan = api.restoreLayanan(iddatalog);

                restorelayanan.enqueue(new Callback<ResponLayanan>() {
                    @Override
                    public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                        ResponLayanan res = response.body();
                        if (res.getMessage().equals("DATA INI SEDANG DIGUNAKAN!")) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Hapus Data Masih Digunakan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("RETRO", "response: " + "Berhasil Restore");
                            Intent intent = new Intent(KelolaLayanan.this, TampilLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaLayanan.this, "Sukses Restore Layanan!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponLayanan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Restore");
                        pd.hide();
                        Toast.makeText(KelolaLayanan.this, "Gagal Restore Layanan!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponLayanan> deletelayanan = api.deleteLayanan(iddata);

                deletelayanan.enqueue(new Callback<ResponLayanan>() {
                    @Override
                    public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                        ResponLayanan res = response.body();
                        if (res.getMessage().equals("DATA INI SEDANG DIGUNAKAN!")) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Hapus Data Masih Digunakan!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("RETRO", "response: " + "Berhasil Delete");
                            Intent intent = new Intent(KelolaLayanan.this, TampilLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaLayanan.this, "Sukses Hapus Layanan!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponLayanan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaLayanan.this, "Gagal Hapus Layanan!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinnerJH.getSelectedItem();
                DataUkuranHewan spnUkuranHewan = (DataUkuranHewan) spinnerUH.getSelectedItem();


                String snama = nama_layanan.getText().toString();
                String sharga = harga_layanan.getText().toString();

                if (snama.trim().equals("") || spinnerJH.getSelectedItem() == null || spinnerUH.getSelectedItem() == null || sharga.trim().equals("") || spinnerJH.getSelectedItem().toString().equals("Pilih Jenis Hewan") || spinnerUH.getSelectedItem().toString().equals("Pilih Ukuran Hewan")  ) {
                    Toast.makeText(KelolaLayanan.this, "Data Layanan Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!sharga.matches("^[0-9]*$")) {
                    Toast.makeText(KelolaLayanan.this, "Harga Layanan Tidak Valid!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();
                    Integer sjenishewan = Integer.parseInt(id_jenis_hewan);

                    String id_ukuran_hewan = spnUkuranHewan.getId_ukuran_hewan();
                    Integer sukuranhewan = Integer.parseInt(id_ukuran_hewan);

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponLayanan> updatejh = api.updateLayanan(iddata, nama_layanan.getText().toString(),harga_layanan.getText().toString(),sjenishewan,sukuranhewan);
                    updatejh.enqueue(new Callback<ResponLayanan>() {
                        @Override
                        public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                            pd.dismiss();
                            ResponLayanan res = response.body();
                            if (res.getMessage().equals("Gagal, Jasa Layanan sudah Terdaftar!")) {
                                Toast.makeText(KelolaLayanan.this, "Layanan Sudah Terdaftar!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("RETRO", "response: " + "Berhasil Update");
                                Intent intent = new Intent(KelolaLayanan.this, TampilLayanan.class);
                                pd.hide();
                                startActivity(intent);
                                Toast.makeText(KelolaLayanan.this, "Sukses Edit Data Layanan!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponLayanan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update Layanan");
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Edit Data Layanan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinnerJH.getSelectedItem();
                DataUkuranHewan spnUkuranHewan = (DataUkuranHewan) spinnerUH.getSelectedItem();


                String snama = nama_layanan.getText().toString();
                String sharga = harga_layanan.getText().toString();



                if (snama.trim().equals("") || spinnerJH.getSelectedItem() == null || spinnerUH.getSelectedItem() == null || sharga.trim().equals("") || spinnerJH.getSelectedItem().toString().equals("Pilih Jenis Hewan") || spinnerUH.getSelectedItem().toString().equals("Pilih Ukuran Hewan")   ) {
                    Toast.makeText(KelolaLayanan.this, "Data Layanan Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!sharga.matches("^[0-9]*$")) {
                    Toast.makeText(KelolaLayanan.this, "Harga Layanan Tidak Valid!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    pd.setMessage("Creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();
                    Integer sjenishewan = Integer.parseInt(id_jenis_hewan);

                    String id_ukuran_hewan = spnUkuranHewan.getId_ukuran_hewan();
                    Integer sukuranhewan = Integer.parseInt(id_ukuran_hewan);

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponLayanan> sendlayanan = api.sendLayanan(snama,sharga,sjenishewan,sukuranhewan);
                    sendlayanan.enqueue(new Callback<ResponLayanan>() {
                        @Override
                        public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {

                            pd.dismiss();
                            ResponLayanan res = response.body();
                            if (res.getMessage().equals("GAGAL, JASA LAYANAN SUDAH ADA!")) {
                                Toast.makeText(KelolaLayanan.this, "Layanan Sudah Terdaftar!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.d("RETRO", "response: " + "Berhasil Create");
                                Intent intent = new Intent(KelolaLayanan.this, TampilLayanan.class);
                                pd.hide();
                                startActivity(intent);
                                Toast.makeText(KelolaLayanan.this, "Sukses Tambah Data Layanan!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponLayanan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaLayanan.this, "Gagal Tambah Data Layanan!", Toast.LENGTH_SHORT).show();
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
                pd.dismiss();
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