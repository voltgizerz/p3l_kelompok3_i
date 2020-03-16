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

import com.example.p3l_kelompok3_i.adapter.AdapterLayanan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarLayanan extends AppCompatActivity {

    private AdapterLayanan mAdapterLayanan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;

    ProgressDialog pd;
    private List<DataLayanan> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerLayanan);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponLayanan> getLayanan = api.getJasaLayananSemua();

        getLayanan.enqueue(new Callback<ResponLayanan>() {
            @Override
            public void onResponse(Call<ResponLayanan> call, Response<ResponLayanan> response) {
                pd.hide();
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API JASA LAYANAN!  " + response.body().getData());
                mItems = response.body().getData();


                mAdapterLayanan = new AdapterLayanan(DaftarLayanan.this,mItems);
                mRecycler.setAdapter(mAdapterLayanan);
                mAdapterLayanan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponLayanan> call, Throwable t) {
                pd.hide();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API JASA LAYANAN! ");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
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
        inflater.inflate(R.menu.example_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(mAdapterLayanan!= null) {
                    mAdapterLayanan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }
}