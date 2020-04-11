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

import com.example.p3l_kelompok3_i.adapter.AdapterPengadaan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_pengadaan.DataPengadaan;
import com.example.p3l_kelompok3_i.model_pengadaan.ResponPengadaan;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilPengadaan extends AppCompatActivity {

    private AdapterPengadaan mAdapterPengadaan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataPengadaan> mItems = new ArrayList<>();
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pengadaan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sm = new SessionManager(TampilPengadaan.this);
        sm.checkLogin();

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTransaksiPengadaan);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponPengadaan> getPengadaan = api.getPengadaanSemua();

        getPengadaan.enqueue(new Callback<ResponPengadaan>() {
            @Override
            public void onResponse(Call<ResponPengadaan> call, Response<ResponPengadaan> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENGADAAN!  " + response.body().getData());
                mItems = response.body().getData();
                Collections.sort(mItems, DataPengadaan.BY_NAME_ALPAHBETICAL);
                mAdapterPengadaan = new AdapterPengadaan(TampilPengadaan.this, mItems);
                mRecycler.setAdapter(mAdapterPengadaan);
                mAdapterPengadaan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPengadaan> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(TampilPengadaan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TampilPengadaan.this, "GAGAL MENAMPILKAN DATA PENGADAAN!", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, KelolaPengadaan.class);
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
                if (mAdapterPengadaan != null) {
                    mAdapterPengadaan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaPengadaan.class);
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