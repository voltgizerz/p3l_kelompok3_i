package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class KelolaPengadaan extends AppCompatActivity {

    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pengadaan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnTampil = (Button) findViewById(R.id.btnTampilPengadaan);

        pd = new ProgressDialog(this);


        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPengadaan.this, TampilPengadaan.class);
                startActivity(i);
            }
        });
    }



}
