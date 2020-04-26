package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button btnCekLayanan;
    private Button btnCekProduk;
    private Button btnLogin;
    private Button btnInformasi;
    private SessionManager sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm = new SessionManager(MainActivity.this);
        getApplication().getSharedPreferences("KodePengadaan", 0).edit().clear().commit();
        getApplication().getSharedPreferences("TotalPengadaan", 0).edit().clear().commit();
        getApplication().getSharedPreferences("StatusPengadaan", 0).edit().clear().commit();
        getApplication().getSharedPreferences("SupplierPengadaan", 0).edit().clear().commit();
        getApplication().getSharedPreferences("IdPengadaan", 0).edit().clear().commit();

        getApplication().getSharedPreferences("StatusPenjualanProduk", 0).edit().clear().commit();
        btnCekProduk = findViewById(R.id.btnCekProduk);
        btnCekLayanan = findViewById(R.id.btnCekLayanan);
        btnLogin = findViewById(R.id.btnLogin);
        btnInformasi = findViewById(R.id.btnInfromasi);

        btnCekProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DaftarProduk.class);
                startActivity(i);
            }
        });

        btnCekLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DaftarLayanan.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        btnInformasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Informasi.class);
                startActivity(i);
            }
        });

    }


}
