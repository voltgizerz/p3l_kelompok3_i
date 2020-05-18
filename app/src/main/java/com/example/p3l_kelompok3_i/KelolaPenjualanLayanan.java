package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.net.InetAddress;
import java.util.HashMap;

public class KelolaPenjualanLayanan extends AppCompatActivity {

    Button btnCreate, btnTampil, btnUpdate, btnDelete, btnTambahProduk;
    String iddata, iddatakode, cekAdaProduk;
    TextView namaPegawai, textbiasa, textKode, tampilKosong, tvJudul;
    Integer idPegawaiLogin;
    Spinner statusPenjualan;
    ProgressDialog pd;
    SessionManager sm;
    private static SharedPreferences prefs, sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
