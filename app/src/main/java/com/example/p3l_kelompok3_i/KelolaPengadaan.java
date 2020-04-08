package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPengadaan;
import com.example.p3l_kelompok3_i.adapter.AdapterPengadaanDetail;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_pengadaan.DataPengadaan;
import com.example.p3l_kelompok3_i.model_pengadaan.ResponPengadaan;
import com.example.p3l_kelompok3_i.pengadaan_detail.DataPengadaanDetail;
import com.example.p3l_kelompok3_i.pengadaan_detail.ResponPengadaanDetail;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPengadaan extends AppCompatActivity {

    private AdapterPengadaanDetail mAdapterPengadaan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataPengadaanDetail> mItems = new ArrayList<>();

    EditText kode_pengadaan, nama_supplier_pengadaan;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    String iddata, iddata_detail;
    TextView namaProduk,kosong;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pengadaan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        namaProduk = findViewById(R.id.tvJudulPengadaan);
        kosong = findViewById(R.id.tvJudulKosong);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerDetailProduk);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponPengadaanDetail> getPengadaanDetail = api.getPengadaanDetailSemua();

        getPengadaanDetail.enqueue(new Callback<ResponPengadaanDetail>() {
            @Override
            public void onResponse(Call<ResponPengadaanDetail> call, Response<ResponPengadaanDetail> response) {
                pd.hide();
                Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API PENGADAAN DETAIL!  " + response.body().getData());

                mItems = response.body().getData();
                mAdapterPengadaan = new AdapterPengadaanDetail(KelolaPengadaan.this, mItems);
                mRecycler.setAdapter(mAdapterPengadaan);
                mAdapterPengadaan.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponPengadaanDetail> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Toast.makeText(KelolaPengadaan.this, "Tidak ada Koneksi Internet", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(KelolaPengadaan.this, "GAGAL MENAMPILKAN DATA PENGADAAN!", Toast.LENGTH_SHORT).show();
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENGADAAN! ");
                }
            }
        });

        btnTampil = (Button) findViewById(R.id.btnTampilPengadaan);
        btnCreate = (Button) findViewById(R.id.btnTambahPengadaan);
        btnDelete = (Button) findViewById(R.id.btnDeletePengadaan);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePengadaan);
        kode_pengadaan = (EditText) findViewById(R.id.kode_transaksi_pengadaan);
        nama_supplier_pengadaan = (EditText) findViewById(R.id.nama_supplier_pengadaan);

        Intent data = getIntent();
        iddata_detail = data.getStringExtra("kode_pengadaan_fk");
        iddata = data.getStringExtra("kode_pengadaan");

        if (iddata != null || iddata_detail!=null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            mRecycler.setVisibility(View.VISIBLE);
            namaProduk.setVisibility(View.VISIBLE);
            kosong.setVisibility(View.VISIBLE);

            kode_pengadaan.setText(data.getStringExtra("kode_pengadaan"));
            nama_supplier_pengadaan.setText(data.getStringExtra("nama_supplier"));
        }
        pd = new ProgressDialog(this);


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaPengadaan.this, MenuAdminTransaksi.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, MenuAdminTransaksi.class);
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
