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

import com.example.p3l_kelompok3_i.adapter.AdapterJenisHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilJenisHewan extends AppCompatActivity {

    private AdapterJenisHewan mAdapterJenisHewan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataJenisHewan> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_jenis_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerJenisHewan);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponJenisHewan> getJenisHewan = api.getJenisHewanSemua();

        getJenisHewan.enqueue(new Callback<ResponJenisHewan>() {
            @Override
            public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                pd.hide();
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());
                mItems = response.body().getData();

                mAdapterJenisHewan = new AdapterJenisHewan(TampilJenisHewan.this,mItems);
                mRecycler.setAdapter(mAdapterJenisHewan);
                mAdapterJenisHewan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                pd.hide();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API JENIS HEWAN! ");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, KelolaJenisHewan.class);
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
                if(mAdapterJenisHewan!= null) {
                    mAdapterJenisHewan.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }
}
