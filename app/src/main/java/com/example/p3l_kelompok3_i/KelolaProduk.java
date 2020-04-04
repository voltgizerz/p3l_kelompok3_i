package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class KelolaProduk extends AppCompatActivity {

    EditText nama_produk, harga_produk,stok_produk, stok_minimal_produk;
    Button btncreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_produk = (EditText) findViewById(R.id.nama_produk);
        harga_produk = (EditText) findViewById(R.id.harga_produk);
        stok_produk= (EditText) findViewById(R.id.stok_produk);
        stok_minimal_produk = (EditText) findViewById(R.id.stok_minimal_produk);

        btncreate = (Button) findViewById(R.id.btnTambahProduk);
        btnTampil = (Button) findViewById(R.id.btnTampilProdukKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProduk);
        btnDelete = (Button) findViewById(R.id.btnDeleteProduk);
        pd = new ProgressDialog(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaProduk.this, TampilProduk.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaProduk.this, MenuAdmin.class);
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
