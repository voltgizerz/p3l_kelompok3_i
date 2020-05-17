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
import android.widget.SearchView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPenjualanProduk;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_penjualan_produk.DataPenjualanProduk;
import com.example.p3l_kelompok3_i.model_penjualan_produk.ResponPenjualanProduk;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilPenjualanProduk extends AppCompatActivity {

    private AdapterPenjualanProduk mAdapterPenjualanProduk;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataPenjualanProduk> mItems = new ArrayList<>();
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_penjualan_produk);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sm = new SessionManager(TampilPenjualanProduk.this);
        sm.checkLogin();

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTransaksiPenjualanProduk);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponPenjualanProduk> getPenjualanProduk = api.getPenjualanProdukSemua();

        getPenjualanProduk.enqueue(new Callback<ResponPenjualanProduk>() {
            @Override
            public void onResponse(Call<ResponPenjualanProduk> call, Response<ResponPenjualanProduk> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENJUALAN!  " + response.body().getData());
                mItems = response.body().getData();
                Collections.sort(mItems, DataPenjualanProduk.BY_NAME_ALPAHBETICAL);
                mAdapterPenjualanProduk = new AdapterPenjualanProduk(TampilPenjualanProduk.this, mItems);
                mRecycler.setAdapter(mAdapterPenjualanProduk);
                mAdapterPenjualanProduk.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPenjualanProduk> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(TampilPenjualanProduk.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TampilPenjualanProduk.this, "GAGAL MENAMPILKAN DATA PENGADAAN!", Toast.LENGTH_SHORT).show();
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENGADAAN! ");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaPenjualanProduk.class);
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
                if (mAdapterPenjualanProduk != null) {
                    mAdapterPenjualanProduk.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaPenjualanProduk.class);
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