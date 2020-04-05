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

import com.example.p3l_kelompok3_i.adapter.AdapterProduk;
import com.example.p3l_kelompok3_i.adapter.AdapterProdukTampil;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilProduk extends AppCompatActivity {

    private AdapterProdukTampil mAdapterProduk;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private Button btnSortHarga,btnSortStok;
    private List<DataProduk> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerProduk);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
        btnSortStok = findViewById(R.id.btnSortingStokProduk);
        btnSortHarga = findViewById(R.id.btnSortingHargaProduk);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        btnSortHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mItems,DataProduk.BY_NAME_HARGA);
                mAdapterProduk = new AdapterProdukTampil(TampilProduk.this, mItems);
                mRecycler.setAdapter(mAdapterProduk);
                mAdapterProduk.notifyDataSetChanged();
                pd.hide();
            }
        });

        btnSortStok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(mItems,DataProduk.BY_NAME_STOK);
                mAdapterProduk = new AdapterProdukTampil(TampilProduk.this, mItems);
                mRecycler.setAdapter(mAdapterProduk);
                mAdapterProduk.notifyDataSetChanged();
                pd.hide();
            }
        });

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponProduk> getProduk = api.getProdukSemua();

        getProduk.enqueue(new Callback<ResponProduk>() {
            @Override
            public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PRODUK!  " + response.body().getData());
                mItems = response.body().getData();
                Collections.sort(mItems,DataProduk.BY_NAME_ALPAHBETICAL);
                mAdapterProduk = new AdapterProdukTampil(TampilProduk.this, mItems);
                mRecycler.setAdapter(mAdapterProduk);
                mAdapterProduk.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponProduk> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(TampilProduk.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TampilProduk.this, "GAGAL MENAMPILKAN DAFTAR PRODUK!", Toast.LENGTH_SHORT).show();
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PRODUK! ");
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaProduk.class);
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
                if (mAdapterProduk != null) {
                    mAdapterProduk.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaProduk.class);
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
