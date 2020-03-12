package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.p3l_kelompok3_i.adapter.AdapterProduk;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiProdukInterface;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DaftarProduk extends AppCompatActivity {

    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    private List<DataProduk> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_produk);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerProduk);
        mManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiProdukInterface api = ApiClient.getClient().create(ApiProdukInterface.class);
        Call<ResponProduk> getProduk = api.getProduk();
        getProduk.enqueue(new Callback<ResponProduk>() {
            @Override
            public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                    pd.hide();
                    Log.d("API","RESPONSE : SUKSES MENDAPATKAN API PRODUK!  " + response.body().getData());
                    mItems = response.body().getData();

                    mAdapter = new AdapterProduk(DaftarProduk.this,mItems);
                    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponProduk> call, Throwable t) {
                    pd.hide();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API PRODUK! ");

            }
        });
    }
}
