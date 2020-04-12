package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPengadaan;
import com.example.p3l_kelompok3_i.adapter.AdapterPengadaanDetail;
import com.example.p3l_kelompok3_i.adapter.AdapterSupplier;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_pengadaan.DataPengadaan;
import com.example.p3l_kelompok3_i.model_pengadaan.ResponPengadaan;
import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;
import com.example.p3l_kelompok3_i.model_supplier.ResponSupplier;
import com.example.p3l_kelompok3_i.pengadaan_detail.DataPengadaanDetail;
import com.example.p3l_kelompok3_i.pengadaan_detail.ResponPengadaanDetail;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPengadaan extends AppCompatActivity {

    private List<DataSupplier> mItemsSupplier = new ArrayList<>();
    private Spinner spinnerSupplier, spinnerStatus;
    private AdapterPengadaanDetail mAdapterPengadaan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataPengadaanDetail> mItems = new ArrayList<>();

    Button btnCreate, btnTampil, btnUpdate, btnDelete,btnTambahProdukDetail;
    String iddata, iddata_detail, iddataKode, iddata_status;
    Integer iddata_kosong;
    Integer dataIdSupplier;
    TextView namaProduk, tampilKode,tampilKosong;
    ProgressDialog pd;

    private static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pengadaan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        namaProduk = findViewById(R.id.tvJudulPengadaan);
        tampilKode = findViewById(R.id.tampilKodeTransaksi);
        tampilKosong = findViewById(R.id.tvProdukMasihKosong);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerDetailProduk);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePengadaan", 0);
        Log.d("isi dari prefs", "pasd" + prefs);
        String cookieName = prefs.getString("kode_pengadaan", null);

        Intent data = getIntent();
        iddata_detail = data.getStringExtra("kode_pengadaan_fk");
        iddata = data.getStringExtra("id_pengadaan");
        iddataKode = data.getStringExtra("kode_pengadaan");
        iddata_status = data.getStringExtra("status_pengadaan");
        iddata_kosong = data.getIntExtra("total_pengadaan",0);
        dataIdSupplier = data.getIntExtra("id_supplier", 0);

        btnTampil = (Button) findViewById(R.id.btnTampilPengadaan);
        btnCreate = (Button) findViewById(R.id.btnTambahPengadaan);
        btnDelete = (Button) findViewById(R.id.btnDeletePengadaan);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePengadaan);
        btnTambahProdukDetail = (Button) findViewById(R.id.btnTambahProdukDetail);
        spinnerSupplier = (Spinner) findViewById(R.id.spinnerIdSupplier);
        // SETTING SPINNER UNTUK UPDATE STATUS PENGADAAN
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        String[] arrayStatus = new String[]{
                "Belum Diterima", "Sudah Diterima"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, arrayStatus);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinnerStatus.setAdapter(adapter);

        btnTambahProdukDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaPengadaan.this, KelolaDetailPengadaan.class);
                startActivity(i);
            }
        });


        if (iddata != null || iddata_detail != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            namaProduk.setVisibility(View.VISIBLE);
            tampilKode.setVisibility(View.VISIBLE);
            spinnerStatus.setVisibility(View.VISIBLE);

            tampilKode.setText(iddataKode);
            if(iddata_status.equals("Belum Diterima")) {
                spinnerStatus.setSelection(0, true);
                btnTambahProdukDetail.setVisibility(View.VISIBLE);
            }else{
                spinnerStatus.setSelection(1, true);
            }

            if(iddata_kosong == 0){
                tampilKosong.setVisibility(View.VISIBLE);
            }else{
                mRecycler.setVisibility(View.VISIBLE);
            }
        }
        pd = new ProgressDialog(this);

        if (cookieName != null) {
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponPengadaanDetail> getPengadaanDetail = api.getPengadaanDetailSemua();

            getPengadaanDetail.enqueue(new Callback<ResponPengadaanDetail>() {
                @Override
                public void onResponse(Call<ResponPengadaanDetail> call, Response<ResponPengadaanDetail> response) {
                    pd.hide();
                    Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PRODUK DIPESAN!  " + response.body().getData());

                    mItems = response.body().getData();
                    mAdapterPengadaan = new AdapterPengadaanDetail(KelolaPengadaan.this, mItems);
                    mRecycler.setAdapter(mAdapterPengadaan);
                    mAdapterPengadaan.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponPengadaanDetail> call, Throwable t) {
                    pd.hide();
                    if (isInternetAvailable() == false) {
                        Toast.makeText(KelolaPengadaan.this, "Tidak ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(KelolaPengadaan.this, "GAGAL MENAMPILKAN DATA PRODUK DIPESAN!", Toast.LENGTH_SHORT).show();
                        Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENGADAAN! ");
                    }
                }
            });
        }

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponSupplier> getSupplier = api.getSupplierSemua();

        getSupplier.enqueue(new Callback<ResponSupplier>() {
            @Override
            public void onResponse(Call<ResponSupplier> call, Response<ResponSupplier> response) {
                mItemsSupplier = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                mItemsSupplier.add(0, new DataSupplier("0", "Pilih Supplier Pengadaan"));

                int position = -1;
                for (int i = 0; i < mItemsSupplier.size(); i++) {
                    if (mItemsSupplier.get(i).getId_supplier().equals(Integer.toString(dataIdSupplier))) {
                        position = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID Supplier] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID SUPPLIER
                ArrayAdapter<DataSupplier> adapter = new ArrayAdapter<DataSupplier>(KelolaPengadaan.this, R.layout.spinner, mItemsSupplier);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerSupplier.setAdapter(adapter);
                spinnerSupplier.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponSupplier> call, Throwable t) {
                if (isInternetAvailable() == false) {
                    Log.d("API", "RESPONSE : TIDAK ADA KONEKSI INTERNET! ");
                } else {
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API SUPPLIER! ");
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
                Call<ResponPengadaan> deletePengadaan = api.deletePengadaan(iddata, iddataKode);

                deletePengadaan.enqueue(new Callback<ResponPengadaan>() {
                    @Override
                    public void onResponse(Call<ResponPengadaan> call, Response<ResponPengadaan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPengadaan.this, "Sukses Hapus Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPengadaan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaPengadaan.this, "Gagal Hapus Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupplier spnSupplier = (DataSupplier) spinnerSupplier.getSelectedItem();

                pd.setMessage("Updating....");
                pd.setCancelable(false);
                pd.show();

                String id_supplier = spnSupplier.getId_supplier();
                Integer sidsupplier = Integer.parseInt(id_supplier);
                String status = spinnerStatus.getSelectedItem().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPengadaan> updatePengadaan = api.updatePengadaan(iddata, sidsupplier, status, iddataKode);

                updatePengadaan.enqueue(new Callback<ResponPengadaan>() {
                    @Override
                    public void onResponse(Call<ResponPengadaan> call, Response<ResponPengadaan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Update");
                        Intent intent = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPengadaan.this, "Sukses Update Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPengadaan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Update");
                        pd.hide();
                        Toast.makeText(KelolaPengadaan.this, "Gagal Update Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupplier spnSupplier = (DataSupplier) spinnerSupplier.getSelectedItem();

                if (spinnerSupplier.getSelectedItem() == null || spinnerSupplier.getSelectedItem().toString().equals("Pilih Supplier Pengadaan")) {
                    Toast.makeText(KelolaPengadaan.this, "Data Transaksi Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Creating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_supplier = spnSupplier.getId_supplier();
                    Integer sidsupplier = Integer.parseInt(id_supplier);

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPengadaan> createPengadaan = api.sendPengadaan(sidsupplier);

                    createPengadaan.enqueue(new Callback<ResponPengadaan>() {
                        @Override
                        public void onResponse(Call<ResponPengadaan> call, Response<ResponPengadaan> response) {
                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPengadaan.this, "Sukses Tambah Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPengadaan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Create");
                            pd.hide();
                            Toast.makeText(KelolaPengadaan.this, "Gagal Tambah Transaksi Pengadaan!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(KelolaPengadaan.this, MenuAdminTransaksi.class);
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
