package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaJenisHewan extends AppCompatActivity {

    private Button btnMasukTambahJenisHewan;
    private Button btnMasukTampilJenisHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_jenis_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukTambahJenisHewan = findViewById(R.id.btnTambahJenisHewan);
        btnMasukTampilJenisHewan = findViewById(R.id.btnTampilJenisHewan);

        btnMasukTambahJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaJenisHewan.this, TambahJenisHewan.class);
                startActivity(i);
            }
        });

        btnMasukTampilJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaJenisHewan.this, TampilJenisHewan.class);
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
