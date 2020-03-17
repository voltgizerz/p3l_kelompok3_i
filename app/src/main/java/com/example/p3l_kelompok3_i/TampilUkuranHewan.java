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


import com.example.p3l_kelompok3_i.adapter.AdapterUkuranHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;

import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilUkuranHewan extends AppCompatActivity {

    private AdapterUkuranHewan mAdapterUkuranHewan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataUkuranHewan> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_ukuran_hewan);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerUkuranHewan);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponUkuranHewan> getUkuranHewan = api.getUkuranHewanSemua();

        getUkuranHewan.enqueue(new Callback<ResponUkuranHewan>() {
            @Override
            public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                pd.hide();
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API UKURAN HEWAN!  " + response.body().getData());
                mItems = response.body().getData();


                mAdapterUkuranHewan = new AdapterUkuranHewan(TampilUkuranHewan.this,mItems);
                mRecycler.setAdapter(mAdapterUkuranHewan);
                mAdapterUkuranHewan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                pd.hide();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API UKURAN HEWAN! ");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, KelolaUkuranHewan.class);
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
                if(mAdapterUkuranHewan!= null) {
                    mAdapterUkuranHewan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }
}