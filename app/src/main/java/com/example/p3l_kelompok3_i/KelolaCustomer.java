package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class KelolaCustomer extends AppCompatActivity {

    private Button btnMasukTambahCustomer;
    private Button btnMasukTampilCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukTambahCustomer = findViewById(R.id.btnTambahCustomer);
        btnMasukTampilCustomer = findViewById(R.id.btnTampilCustomer);

        btnMasukTambahCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaCustomer.this, TambahCustomer.class);
                startActivity(i);
            }
        });

        btnMasukTampilCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaCustomer.this, TampilCustomer.class);
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

