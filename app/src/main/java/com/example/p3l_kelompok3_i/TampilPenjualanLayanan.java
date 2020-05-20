package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPenjualanLayanan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_penjualan_layanan.ResponPenjualanLayanan;
import com.example.p3l_kelompok3_i.model_penjualan_layanan.DataPenjualanLayanan;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilPenjualanLayanan extends AppCompatActivity {

    private AdapterPenjualanLayanan mAdapterPenjualanLayanan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private Button btnSudahSelesai, btnBelumSelesai;
    ProgressDialog pd;
    private List<DataPenjualanLayanan> mItems = new ArrayList<>();
    private List<DataPenjualanLayanan> saringLayanan = new ArrayList<>();
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_penjualan_layanan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sm = new SessionManager(TampilPenjualanLayanan.this);
        sm.checkLogin();

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTransaksiPenjualanLayanan);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
        btnSudahSelesai = findViewById(R.id.btnSortingSudahSelesai);
        btnBelumSelesai = findViewById(R.id.btnSortingBelumSelesai);

        btnBelumSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPenjualanLayanan> getPenjualanLayanan = api.getPenjualanLayananSemua();

                getPenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                        saringLayanan.clear();
                        pd.hide();
                        Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENJUALAN!  " + response.body().getData());
                        mItems = response.body().getData();
                        mItems = response.body().getData();
                        List<DataPenjualanLayanan> a = mItems;
                        for(DataPenjualanLayanan data : a){
                            if(data.getStatus_penjualan().equals("Belum Selesai") ){
                                saringLayanan.add(data);
                            }
                        }
                        Collections.sort(saringLayanan, DataPenjualanLayanan.BY_NAME_ALPAHBETICAL);
                        mAdapterPenjualanLayanan = new AdapterPenjualanLayanan(TampilPenjualanLayanan.this, saringLayanan);
                        mRecycler.setAdapter(mAdapterPenjualanLayanan);
                        mAdapterPenjualanLayanan.notifyDataSetChanged();
                        Toast.makeText(TampilPenjualanLayanan.this, "Transaksi Penjualan Belum Selesai", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                        pd.hide();
                        if(isInternetAvailable() == false)
                        {
                            Toast.makeText(TampilPenjualanLayanan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TampilPenjualanLayanan.this, "GAGAL MENAMPILKAN DATA PENJUALAN LAYANAN!", Toast.LENGTH_SHORT).show();
                            Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENJUALAN LAYANAN! ");
                        }
                    }
                });

            }
        });


        btnSudahSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Loading...");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPenjualanLayanan> getPenjualanLayanan = api.getPenjualanLayananSemua();

                getPenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                        saringLayanan.clear();
                        pd.hide();
                        Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENJUALAN!  " + response.body().getData());
                        mItems = response.body().getData();
                        mItems = response.body().getData();
                        List<DataPenjualanLayanan> a = mItems;
                        for(DataPenjualanLayanan data : a){
                            if(data.getStatus_penjualan().equals("Sudah Selesai") ){
                                saringLayanan.add(data);
                            }
                        }
                        Collections.sort(saringLayanan, DataPenjualanLayanan.BY_NAME_ALPAHBETICAL);
                        mAdapterPenjualanLayanan = new AdapterPenjualanLayanan(TampilPenjualanLayanan.this, saringLayanan);
                        mRecycler.setAdapter(mAdapterPenjualanLayanan);
                        mAdapterPenjualanLayanan.notifyDataSetChanged();
                        Toast.makeText(TampilPenjualanLayanan.this, "Transaksi Penjualan Sudah Selesai", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                        pd.hide();
                        if(isInternetAvailable() == false)
                        {
                            Toast.makeText(TampilPenjualanLayanan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TampilPenjualanLayanan.this, "GAGAL MENAMPILKAN DATA PENJUALAN LAYANAN!", Toast.LENGTH_SHORT).show();
                            Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENJUALAN LAYANAN! ");
                        }
                    }
                });

            }
        });

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponPenjualanLayanan> getPenjualanLayanan = api.getPenjualanLayananSemua();

        getPenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
            @Override
            public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENJUALAN!  " + response.body().getData());
                mItems = response.body().getData();
                Collections.sort(mItems, DataPenjualanLayanan.BY_NAME_ALPAHBETICAL);
                mAdapterPenjualanLayanan = new AdapterPenjualanLayanan(TampilPenjualanLayanan.this, mItems);
                mRecycler.setAdapter(mAdapterPenjualanLayanan);
                mAdapterPenjualanLayanan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(TampilPenjualanLayanan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TampilPenjualanLayanan.this, "GAGAL MENAMPILKAN DATA PENJUALAN LAYANAN!", Toast.LENGTH_SHORT).show();
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENJUALAN LAYANAN! ");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaPenjualanLayanan.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapterPenjualanLayanan != null) {
                    mAdapterPenjualanLayanan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaPenjualanLayanan.class);
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