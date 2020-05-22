package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.util.HashMap;

public class MenuAdminTransaksi extends AppCompatActivity {

    Button btnSupplier,btnTransaksiPengadaan, btnPenjualanProduk,btnPenjualanLayanan;
    TextView tvNama,tvRole;
    ImageView logo;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin_transaksi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sm = new SessionManager(MenuAdminTransaksi.this);
        sm.checkLogin();

        tvNama = findViewById(R.id.tvNamaPegawai3);
        tvRole = findViewById(R.id.tvRolePegawaiAdmin2);
        logo = findViewById(R.id.imageViewTrx);
        HashMap<String, String> map = sm.getDetailLogin();
        tvNama.setText(map.get(sm.KEY_NAMA));
        tvRole.setText(map.get(sm.KEY_ROLE));



        btnTransaksiPengadaan = (Button) findViewById(R.id.btnTransaksiePengandaan);
        btnPenjualanProduk = (Button) findViewById(R.id.btnTransaksiJualProduk);
        btnPenjualanLayanan = (Button) findViewById(R.id.btnTransaksiJualLayanan);
        btnSupplier = (Button)  findViewById(R.id.btnSupplier);

        if(map.get(sm.KEY_ROLE).equals("Customer Service")){
                btnSupplier.setVisibility(View.GONE);
                btnTransaksiPengadaan.setVisibility(View.GONE);
                logo.setVisibility(View.VISIBLE);
        }
        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdminTransaksi.this, KelolaSupplier.class);
                startActivity(i);
            }
        });

        btnTransaksiPengadaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdminTransaksi.this, KelolaPengadaan.class);
                startActivity(i);
            }
        });

        btnPenjualanProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdminTransaksi.this, KelolaPenjualanProduk.class);
                startActivity(i);
            }
        });

        btnPenjualanLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdminTransaksi.this, KelolaPenjualanLayanan.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MenuAdmin.class);
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
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }

}
