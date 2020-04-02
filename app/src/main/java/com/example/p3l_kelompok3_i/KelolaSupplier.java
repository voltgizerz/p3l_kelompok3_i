package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class KelolaSupplier extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    EditText nama_supplier, alamat_supplier, nohp_supplier;
    Button btnTampil, btnCreate, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_supplier);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_supplier = (EditText) findViewById(R.id.nama_supplier);
        alamat_supplier = (EditText) findViewById(R.id.alamat_supplier);
        nohp_supplier = (EditText) findViewById(R.id.nomor_hp_supplier);
        btnCreate = (Button) findViewById(R.id.btnTambahSupplier);
        btnTampil = findViewById(R.id.btnTampilSupplierKelola);
        btnDelete = findViewById(R.id.btnDeleteSupplier);
        btnUpdate = (Button) findViewById(R.id.btnUpdateSupplier);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_supplier");
        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            nama_supplier.setText(data.getStringExtra("nama_supplier"));
            alamat_supplier.setText(data.getStringExtra("alamat_supplier"));
            nohp_supplier.setText(data.getStringExtra("nomor_telepon_supplier"));
        }

        pd = new ProgressDialog(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaSupplier.this, TampilSupplier.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaSupplier.this, MenuAdminTransaksi.class);
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
