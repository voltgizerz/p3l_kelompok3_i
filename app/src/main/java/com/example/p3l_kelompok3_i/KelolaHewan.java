package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaHewan extends AppCompatActivity {

    private Button btnMasukTambahHewan;
    private Button btnMasukTampilHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukTambahHewan= findViewById(R.id.btnTambahHewan);
        btnMasukTampilHewan = findViewById(R.id.btnTampilHewan);

        btnMasukTambahHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaHewan.this, TambahHewan.class);
                startActivity(i);
            }
        });

        btnMasukTampilHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaHewan.this, TampilHewan.class);
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