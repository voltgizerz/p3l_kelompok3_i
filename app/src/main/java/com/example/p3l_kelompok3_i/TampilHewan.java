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


import com.example.p3l_kelompok3_i.adapter.AdapterHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;

import com.example.p3l_kelompok3_i.model_hewan.DataHewan;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilHewan extends AppCompatActivity {

    private AdapterHewan mAdapterHewan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataHewan> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerHewan);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponHewan> getHewan = api.getHewanSemua();

        getHewan.enqueue(new Callback<ResponHewan>() {
            @Override
            public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                pd.hide();
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API HEWAN!  " + response.body().getData());
                mItems = response.body().getData();


                mAdapterHewan = new AdapterHewan(TampilHewan.this,mItems);
                mRecycler.setAdapter(mAdapterHewan);
                mAdapterHewan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponHewan> call, Throwable t) {
                pd.hide();
                Toast.makeText(TampilHewan.this, "GAGAL MENAMPILKAN DATA HEWAN!", Toast.LENGTH_SHORT).show();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API HEWAN! ");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaHewan.class);
                startActivity(intent);
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
                if(mAdapterHewan!= null) {
                    mAdapterHewan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed(){
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaHewan.class);
        startActivity(intent);
    }
}