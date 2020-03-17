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


import com.example.p3l_kelompok3_i.adapter.AdapterPegawai;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_pegawai.DataPegawai;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilPegawai extends AppCompatActivity {

    private AdapterPegawai mAdapterPegawai;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataPegawai> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_pegawai);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerPegawai);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponPegawai> getPegawai = api.getPegawaiSemua();

        getPegawai.enqueue(new Callback<ResponPegawai>() {
            @Override
            public void onResponse(Call<ResponPegawai> call, Response<ResponPegawai> response) {
                pd.hide();
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API PEGAWAI!  " + response.body().getData());
                mItems = response.body().getData();

                mAdapterPegawai = new AdapterPegawai(TampilPegawai.this,mItems);
                mRecycler.setAdapter(mAdapterPegawai);
                mAdapterPegawai.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPegawai> call, Throwable t) {
                pd.hide();
                Toast.makeText(TampilPegawai.this, "GAGAL MENAMPILKAN DATA PEGAWAI!", Toast.LENGTH_SHORT).show();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API PEGAWAI! ");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, KelolaPegawai.class);
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
                if(mAdapterPegawai!= null) {
                    mAdapterPegawai.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }
}