package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaPegawai extends AppCompatActivity {

    private Button btnMasukTambahPegawai;
    private Button btnMasukTampilPegawai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pegawai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukTambahPegawai = findViewById(R.id.btnTambahPegawai);
        btnMasukTampilPegawai = findViewById(R.id.btnTampilPegawai);

        btnMasukTambahPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPegawai.this, TambahPegawai.class);
                startActivity(i);
            }
        });

        btnMasukTampilPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPegawai.this, TampilPegawai.class);
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
}

