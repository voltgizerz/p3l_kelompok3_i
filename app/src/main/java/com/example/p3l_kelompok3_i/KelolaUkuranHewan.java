package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaUkuranHewan extends AppCompatActivity {

    private Button btnMasukTambahUkuranHewan;
    private Button btnMasukTampilUkuranHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_ukuran_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukTambahUkuranHewan = findViewById(R.id.btnTambahUkuranHewan);
        btnMasukTampilUkuranHewan = findViewById(R.id.btnTampilUkuranHewan);

        btnMasukTambahUkuranHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaUkuranHewan.this, TambahUkuranHewan.class);
                startActivity(i);
            }
        });

        btnMasukTampilUkuranHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaUkuranHewan.this, TampilUkuranHewan.class);
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