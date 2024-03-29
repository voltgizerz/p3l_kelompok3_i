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

import com.example.p3l_kelompok3_i.adapter.AdapterSupplier;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;
import com.example.p3l_kelompok3_i.model_supplier.ResponSupplier;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilSupplier extends AppCompatActivity {

    private AdapterSupplier mAdapterSupplier;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataSupplier> mItems = new ArrayList<>();
    SessionManager sm;
    Button btnLogDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_supplier);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sm = new SessionManager(TampilSupplier.this);
        sm.checkLogin();
        btnLogDelete = (Button) findViewById(R.id.btnLogSupplier);
        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerSupplier);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        btnLogDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TampilSupplier.this, TampilLogSupplier.class);
                startActivity(i);
            }
        });

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponSupplier> getSupplier = api.getSupplierSemua();

        getSupplier.enqueue(new Callback<ResponSupplier>() {
            @Override
            public void onResponse(Call<ResponSupplier> call, Response<ResponSupplier> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API SUPPLIER!  " + response.body().getData());
                mItems = response.body().getData();

                Collections.sort(mItems, DataSupplier.BY_NAME_ALPAHBETICAL);
                mAdapterSupplier = new AdapterSupplier(TampilSupplier.this, mItems);
                mRecycler.setAdapter(mAdapterSupplier);
                mAdapterSupplier.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponSupplier> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(TampilSupplier.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TampilSupplier.this, "GAGAL MENAMPILKAN DATA SUPPLIER!", Toast.LENGTH_SHORT).show();
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API SUPPLIER! ");
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeOptionsMenu();
                Intent intent = new Intent(this, KelolaSupplier.class);
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
                if (mAdapterSupplier != null) {
                    mAdapterSupplier.getFilter().filter(newText);
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, KelolaSupplier.class);
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
