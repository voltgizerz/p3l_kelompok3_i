package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaPenjualanProduk extends AppCompatActivity {

    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnCreate = (Button) findViewById(R.id.btnTambahPenjualanProduk);
        btnTampil = (Button) findViewById(R.id.btnTampilPenjualanroduk);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePenjualanProduk);
        btnDelete = (Button) findViewById(R.id.btnDeletePenjualanProduk);
        pd = new ProgressDialog(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pd.dismiss();
                Intent intent = new Intent(KelolaPenjualanProduk.this, MenuAdminTransaksi.class);
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

}
