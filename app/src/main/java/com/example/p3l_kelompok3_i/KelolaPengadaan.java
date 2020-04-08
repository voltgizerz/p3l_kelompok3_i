package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KelolaPengadaan extends AppCompatActivity {

    EditText kode_pengadaan, nama_supplier_pengadaan;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pengadaan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTampil = (Button) findViewById(R.id.btnTampilPengadaan);
        btnCreate = (Button) findViewById(R.id.btnTambahPengadaan);
        btnDelete = (Button) findViewById(R.id.btnDeletePengadaan);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePengadaan);
        kode_pengadaan = (EditText) findViewById(R.id.kode_transaksi_pengadaan);
        nama_supplier_pengadaan = (EditText) findViewById(R.id.nama_supplier_pengadaan);

        Intent data = getIntent();
        iddata = data.getStringExtra("kode_pengadaan");
        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

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

}
