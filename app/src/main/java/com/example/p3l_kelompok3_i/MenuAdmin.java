package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

public class MenuAdmin extends AppCompatActivity {

    private Button btnKelolaCustomer;
    private Button btnKelolaPegawai;
    private Button btnKelolaJenisHewan;
    private Button btnKelolaUkuranHewan;
    private Button btnKelolaHewan;
    private Button btnLogut;
    private TextView tvNama, tvRole;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        btnKelolaCustomer = findViewById(R.id.btnCustomer);
        btnKelolaPegawai = findViewById(R.id.btnPegawai);
        btnKelolaJenisHewan = findViewById(R.id.btnJenisHewan);
        btnKelolaHewan = findViewById(R.id.btnHewan);
        btnKelolaUkuranHewan = findViewById(R.id.btnUkuranHewan);
        btnLogut = findViewById(R.id.btnLogout);
        tvNama = findViewById(R.id.tvNamaPegawai2);
        tvRole = findViewById(R.id.tvRolePegawaiAdmin);

        Intent data = getIntent();
        if(data.getExtras()!= null) {
            tvNama.setText(data.getExtras().getString("nama_pegawai"));
            tvRole.setText(data.getExtras().getString("role_pegawai"));
        }
        sm = new SessionManager(MenuAdmin.this);
        sm.checkLogin();

        btnKelolaCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaCustomer.class);
                startActivity(i);
            }
        });

        btnKelolaUkuranHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaUkuranHewan.class);
                startActivity(i);
            }
        });

        btnKelolaPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaPegawai.class);
                startActivity(i);
            }
        });

        btnKelolaJenisHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaJenisHewan.class);
                startActivity(i);
            }
        });

        btnKelolaHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaHewan.class);
                startActivity(i);
            }
        });

        btnLogut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sm.logout();
                sm.checkLogin();
                Intent i = new Intent(MenuAdmin.this, MainActivity.class);
                startActivity(i);
                Toast.makeText(MenuAdmin.this, "Berhasil Logout!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
