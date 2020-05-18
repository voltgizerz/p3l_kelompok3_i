package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_hewan.DataHewan;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPenjualanLayanan extends AppCompatActivity {

    private List<DataHewan> mItems = new ArrayList<>();
    Button btnCreate, btnTampil, btnUpdate, btnDelete, btnTambahProduk;
    String iddata, iddatakode, cekAdaProduk;
    TextView namaPegawai, textbiasa, textKode, tampilKosong, tvJudul;
    Integer idPegawaiLogin,dataIdHewan ;
    Spinner statusPenjualan,spinnerHewan;
    ProgressDialog pd;
    SessionManager sm;
    private static SharedPreferences prefs, sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanLayanan", 0);
        final String cookieName = prefs.getString("kode_penjualan_layanan", null);

        String statusPenjualanLayanan = getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).getString("status_penjualan_produk", null);
        String idPenjualanLayanan = getApplication().getSharedPreferences("IdPenjualanLayanan", 0).getString("id_transaksi_penjualan_produk", null);


        btnCreate = (Button) findViewById(R.id.btnTambahPenjualanLayanan);
        btnTampil = (Button) findViewById(R.id.btnTampilPenjualanLayanan);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePenjualanLayanan);
        btnDelete = (Button) findViewById(R.id.btnDeletePenjualanLayanan);
        btnTambahProduk = findViewById(R.id.btnTambahLayananDetail);
        namaPegawai = (TextView) findViewById(R.id.tvNamaPegawaiPenjualanLayanan);
        textbiasa = (TextView) findViewById(R.id.tvIdPegawaiPenjualanLayanan);
        sm = new SessionManager(KelolaPenjualanLayanan.this);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        namaPegawai.setText(map.get(sm.KEY_NAMA));
        textbiasa.setText("Nama Customer Service");
        pd = new ProgressDialog(this);
        spinnerHewan = (Spinner) findViewById(R.id.spinnerIdHewanPenjualan);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_transaksi_penjualan_layanan");
        iddatakode = data.getStringExtra("kode_transaksi_penjualan_layanan");
        dataIdHewan = data.getIntExtra("id_hewan_penjualan_layanan", 0);
        if (iddata != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);
            if (data.getStringExtra("status_penjualan").equals("Belum Selesai")) {
                btnTambahProduk.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(data.getStringExtra("kode_transaksi_penjualan_produk"));

        } else if (statusPenjualanLayanan != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);

            iddata = idPenjualanLayanan;


            if (statusPenjualanLayanan.equals("Belum Selesai")) {
                btnTambahProduk.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(cookieName);
        }

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponHewan> getHewan = api.getHewanSemua();

        getHewan.enqueue(new Callback<ResponHewan>() {
            @Override
            public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                mItems = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                mItems.add(0, new DataHewan("Pilih Hewan", "0"));
                Log.d("id hewan","sd"+dataIdHewan);

                int position = -1;
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getId_hewan().equals(Integer.toString(dataIdHewan))) {
                        position = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID HEWAN] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID JENIS HEWAN
                ArrayAdapter<DataHewan> adapter = new ArrayAdapter<DataHewan>(KelolaPenjualanLayanan.this, R.layout.spinner, mItems);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerHewan.setAdapter(adapter);
                spinnerHewan.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponHewan> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API HEWAN! ");
            }
        });

        btnTampil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                    startActivity(i);
                }
            });

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String statusPenjualanProduk = getApplication().getSharedPreferences("StatusPenjualanProduk", 0).getString("status_penjualan_produk", null);
        if (statusPenjualanProduk == null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    pd.dismiss();
                    Intent intent = new Intent(KelolaPenjualanLayanan.this, MenuAdminTransaksi.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getApplication().getSharedPreferences("KodePenjualanProduk", 0).edit().clear().commit();
                    getApplication().getSharedPreferences("StatusPenjualanProduk", 0).edit().clear().commit();
                    getApplication().getSharedPreferences("IdPenjualanProduk", 0).edit().clear().commit();
                    pd.dismiss();
                    Intent intent = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {
        String statusPenjualanProduk = getApplication().getSharedPreferences("StatusPenjualanProduk", 0).getString("status_penjualan_produk", null);
        if (statusPenjualanProduk == null) {
            closeOptionsMenu();
            Intent intent = new Intent(this, MenuAdminTransaksi.class);
            startActivity(intent);
        }else{
            closeOptionsMenu();
            getApplication().getSharedPreferences("KodePenjualanProduk", 0).edit().clear().commit();
            getApplication().getSharedPreferences("StatusPenjualanProduk", 0).edit().clear().commit();
            getApplication().getSharedPreferences("IdPenjualanProduk", 0).edit().clear().commit();
            Intent intent = new Intent(this, TampilPenjualanLayanan.class);
            startActivity(intent);
        }
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

