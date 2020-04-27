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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPenjualanProduk;
import com.example.p3l_kelompok3_i.adapter.AdapterPenjualanProdukDetail;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_penjualan_produk.ResponPenjualanProduk;
import com.example.p3l_kelompok3_i.penjualan_produk_detail.DataPenjualanProdukDetail;
import com.example.p3l_kelompok3_i.penjualan_produk_detail.ResponPenjualanProdukDetail;

import net.grandcentrix.tray.AppPreferences;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPenjualanProduk extends AppCompatActivity {

    private AdapterPenjualanProdukDetail mAdapterPenjualan;
    private List<DataPenjualanProdukDetail> mItems = new ArrayList<>();
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    Button btnCreate, btnTampil, btnUpdate, btnDelete,btnTambahProduk;
    String iddata,iddatakode,cekAdaProduk;
    TextView namaPegawai,textbiasa,textKode,tampilKosong,tvJudul;
    Integer idPegawaiLogin;
    Spinner statusPenjualan;
    ProgressDialog pd;
    SessionManager sm;
    private static SharedPreferences prefs, sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // create a preference accessor. This is for global app preferences.
        final AppPreferences appPreferences = new AppPreferences(getApplicationContext()); // this Preference comes for free from the library


        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanProduk", 0);
        final String cookieName = prefs.getString("kode_penjualan_produk", null);

        String statusPenjualanProduk = getApplication().getSharedPreferences("StatusPenjualanProduk", 0).getString("status_penjualan_produk", null);

        btnCreate = (Button) findViewById(R.id.btnTambahPenjualanProduk);
        btnTampil = (Button) findViewById(R.id.btnTampilPenjualanroduk);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePenjualanProduk);
        btnDelete = (Button) findViewById(R.id.btnDeletePenjualanProduk);
        btnTambahProduk = findViewById(R.id.btnTambahProdukDetail);
        namaPegawai = (TextView) findViewById(R.id.tvNamaPegawaiPenjualanProduk);
        textbiasa = (TextView) findViewById(R.id.tvIdPegawaiPenjualanProduk);
        tampilKosong = (TextView) findViewById(R.id.tvProdukMasihKosongPenjualanProduk);
        tvJudul = findViewById(R.id.tvJudulPenjualanProduk);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerDetailPenjualanProduk);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        statusPenjualan = (Spinner) findViewById(R.id.spinnerStatusPenjualanProduk); String[] arrayStatus = new String[]{
                "Belum Selesai", "Sudah Selesai"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, arrayStatus);
        adapter.setDropDownViewResource(R.layout.spinner);
        statusPenjualan.setAdapter(adapter);
        textKode =  findViewById(R.id.tampilKodeTransaksiPenjualanProduk);
        pd = new ProgressDialog(this);

        sm = new SessionManager(KelolaPenjualanProduk.this);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        namaPegawai.setText(map.get(sm.KEY_NAMA));
        textbiasa.setText("Nama Customer Service");

        idPegawaiLogin = Integer.parseInt(map.get(sm.KEY_ID));

        if (cookieName != null) {
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponPenjualanProdukDetail> getPenjualanD = api.getPenjualanProdukDetailSemua();

            getPenjualanD.enqueue(new Callback<ResponPenjualanProdukDetail>() {
                @Override
                public void onResponse(Call<ResponPenjualanProdukDetail> call, Response<ResponPenjualanProdukDetail> response) {
                    pd.hide();
                    Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PRODUK DIBELI!  " + response.body().getData());

                    mItems = response.body().getData();
                    if (mItems.isEmpty() == true) {

                        appPreferences.put("cekProdukPenjualan", "Tidak");
                        final String value = appPreferences.getString("cekProdukPenjualan", "default");
                        cekAdaProduk = appPreferences.getString("cekProdukPenjualan", "default");
                        if (cekAdaProduk.equals("Ada")) {
                            mRecycler.setVisibility(View.VISIBLE);
                        } else {
                            tampilKosong.setVisibility(View.VISIBLE);
                        }
                    } else {

                        appPreferences.put("cekProdukPenjualan", "Ada");
                        final String value = appPreferences.getString("cekProdukPenjualan", "default");
                        cekAdaProduk = appPreferences.getString("cekProdukPenjualan", "default");
                        if (cekAdaProduk.equals("Ada")) {
                            mRecycler.setVisibility(View.VISIBLE);
                        } else {
                            tampilKosong.setVisibility(View.VISIBLE);
                        }
                    }
                    mAdapterPenjualan= new AdapterPenjualanProdukDetail(KelolaPenjualanProduk.this, mItems);
                    mRecycler.setAdapter(mAdapterPenjualan);
                    mAdapterPenjualan.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponPenjualanProdukDetail> call, Throwable t) {
                    pd.hide();
                    if (isInternetAvailable() == false) {
                        Toast.makeText(KelolaPenjualanProduk.this, "Tidak ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(KelolaPenjualanProduk.this, "GAGAL MENAMPILKAN DATA PRODUK DIPESAN!", Toast.LENGTH_SHORT).show();
                        Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENGADAAN! ");
                    }
                }
            });
        }

        Intent data = getIntent();
        iddata = data.getStringExtra("id_transaksi_penjualan_produk");
        iddatakode = data.getStringExtra("kode_transaksi_penjualan_produk");
        if (iddata != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);
            if (data.getStringExtra("status_penjualan").equals("Belum Selesai")) {
                btnTambahProduk.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(data.getStringExtra("kode_transaksi_penjualan_produk"));

        }else if (statusPenjualanProduk != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);
            if (statusPenjualanProduk.equals("Belum Selesai")) {
                btnTambahProduk.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(cookieName);
        }

        btnTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaPenjualanProduk.this, KelolaDetailPenjualanProduk.class);
                startActivity(i);
            }
        });


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating...");
                pd.setCancelable(false);
                pd.show();

                String status = statusPenjualan.getSelectedItem().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponPenjualanProduk> updatePenjualanProduk = api.updatePenjualanProduk(iddata,status,cookieName);
                updatePenjualanProduk.enqueue(new Callback<ResponPenjualanProduk>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanProduk> call, Response<ResponPenjualanProduk> response) {
                        pd.dismiss();
                        ResponPenjualanProduk res = response.body();

                        Log.d("RETRO", "response: " + "Berhasil update");
                        Intent intent = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPenjualanProduk.this, "Sukses Update Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onFailure(Call<ResponPenjualanProduk> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaPenjualanProduk.this, "Gagal Update Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Update Data");
                    }
                });

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(true);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPenjualanProduk> deletePenjualanProduk = api.deletePenjualanProduk(iddata, iddatakode);

                deletePenjualanProduk.enqueue(new Callback<ResponPenjualanProduk>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanProduk> call, Response<ResponPenjualanProduk> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPenjualanProduk.this, "Sukses Hapus Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanProduk> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaPenjualanProduk.this, "Gagal Hapus Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating...");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponPenjualanProduk> sendPenjualanProduk = api.sendPenjualanProduk(idPegawaiLogin,idPegawaiLogin);
                sendPenjualanProduk.enqueue(new Callback<ResponPenjualanProduk>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanProduk> call, Response<ResponPenjualanProduk> response) {
                        pd.dismiss();
                        ResponPenjualanProduk res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPenjualanProduk.this, "Sukses Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        }


                    @Override
                    public void onFailure(Call<ResponPenjualanProduk> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaPenjualanProduk.this, "Gagal Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Tambah Data");


                }
            });
        }
    });
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getApplication().getSharedPreferences("KodePenjualanProduk", 0).edit().clear().commit();
                getApplication().getSharedPreferences("StatusPenjualanProduk", 0).edit().clear().commit();
                pd.dismiss();
                Intent intent = new Intent(KelolaPenjualanProduk.this, MenuAdminTransaksi.class);
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
        getApplication().getSharedPreferences("KodePenjualanProduk", 0).edit().clear().commit();
        getApplication().getSharedPreferences("StatusPenjualanProduk", 0).edit().clear().commit();
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
