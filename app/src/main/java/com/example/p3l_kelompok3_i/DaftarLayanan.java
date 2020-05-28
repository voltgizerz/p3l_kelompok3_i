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

import com.example.p3l_kelompok3_i.adapter.AdapterLayanan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLayanan extends AppCompatActivity {

    private AdapterLayanan mAdapterLayanan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    Button btnTampiLayananSelesai;
    ProgressDialog pd;
    private List<DataLayanan> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerLayanan);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);
        btnTampiLayananSelesai = findViewById(R.id.btnTampilLayananSelesai);

        btnTampiLayananSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DaftarLayanan.this, TampilLayananSudahSelesai.class);
                startActivity(i);
            }
        });


        if (cekApi() == true) {
            Toast.makeText(DaftarLayanan.this, "Mohon Maaf Sedang Maintenance!", Toast.LENGTH_SHORT).show();
        } else {
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();

            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponLayanan> getLayanan = api.getJasaLayananSemua();

            getLayanan.enqueue(new Callback<ResponLayanan>() {
                @Override
                public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                    pd.hide();
                    Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API JASA LAYANAN!  " + response.body().getData());
                    mItems = response.body().getData();
                    Collections.sort(mItems, DataLayanan.BY_NAME_ALPAHBETICAL);
                    mAdapterLayanan = new AdapterLayanan(DaftarLayanan.this, mItems);
                    mRecycler.setAdapter(mAdapterLayanan);
                    mAdapterLayanan.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponLayanan> call, Throwable t) {
                    pd.hide();
                    if (isInternetAvailable() == false) {
                        Toast.makeText(DaftarLayanan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DaftarLayanan.this, "GAGAL MENAMPILKAN DAFTAR JASA LAYANAN!", Toast.LENGTH_SHORT).show();
                        Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API JASA LAYANAN! ");
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pd.dismiss();
                closeOptionsMenu();
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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
                if (mAdapterLayanan != null) {
                    mAdapterLayanan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        pd.dismiss();
        closeOptionsMenu();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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

    public boolean cekApi() {
        try {
            InetAddress.getByName("apip3landroid2.000webhostapp.com").isReachable(3000); //Replace with your name
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
