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

import com.example.p3l_kelompok3_i.adapter.AdapterCustomer;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_customer.DataCustomer;
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilCustomer extends AppCompatActivity {

    private AdapterCustomer mAdapterCustomer;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataCustomer> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerPegawai);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponCustomer> getCustomer = api.getCustomerSemua();

        getCustomer.enqueue(new Callback<ResponCustomer>() {
            @Override
            public void onResponse(Call<ResponCustomer> call, Response<ResponCustomer> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API CUSTOMER!  " + response.body().getData());
                mItems = response.body().getData();


                mAdapterCustomer = new AdapterCustomer(TampilCustomer.this, mItems);
                mRecycler.setAdapter(mAdapterCustomer);
                mAdapterCustomer.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponCustomer> call, Throwable t) {
                pd.hide();
                Toast.makeText(TampilCustomer.this, "GAGAL MENAMPILKAN DATA CUSTOMER!", Toast.LENGTH_SHORT).show();
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API CUSTOMER! ");

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaCustomer.class);
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
                if (mAdapterCustomer != null) {
                    mAdapterCustomer.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaCustomer.class);
        startActivity(intent);
    }
}