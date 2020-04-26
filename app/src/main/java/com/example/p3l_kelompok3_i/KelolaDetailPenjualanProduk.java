package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class KelolaDetailPenjualanProduk extends AppCompatActivity {

    private List<DataProduk> mItemsProduk = new ArrayList<>();
    ProgressDialog pd;
    Spinner spinnerProduk;
    String iddata;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    EditText etJumlahProduk;
    TextView tvKode;
    private static SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_detail_penjualan_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanProduk", 0);
        final String cookieName = prefs.getString("kode_penjualan_produk", null);

        spinnerProduk = (Spinner) findViewById(R.id.spinnerProdukPenjualan);
        pd = new ProgressDialog(this);
        btnTampil = (Button) findViewById(R.id.btnTampilProdukDetailPenjualan);
        btnCreate = (Button) findViewById(R.id.btnTambahProdukDetailPenjualan);
        btnDelete = (Button) findViewById(R.id.btnDeleteProdukDetailPenjualan);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProdukDetailPenjualan);
        etJumlahProduk = findViewById(R.id.jumlah_penjualan_produk_detail);
        tvKode = (TextView) findViewById(R.id.tampilKodeTransaksiDetailPenjualanProduk);
        tvKode.setText(cookieName);
        Intent data = getIntent();
        iddata= data.getStringExtra("id_detail_transaksi_penjualan_produk");

        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            etJumlahProduk.setText(String.valueOf(data.getIntExtra("jumlah_produk",0)));
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaDetailPenjualanProduk.this, KelolaPenjualanProduk.class);
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
        Intent intent = new Intent(this, KelolaPenjualanProduk.class);
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
