package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.util.HashMap;

public class MenuAdmin extends AppCompatActivity {

    private Button btnKelolaCustomer, btnKelolaLayanan,  btnKelolaProduk;
    private Button btnKelolaPegawai;
    private Button btnKelolaJenisHewan;
    private Button btnKelolaUkuranHewan;
    private Button btnKelolaHewan;
    private Button btnLogut;
    private Button btnPindah;
    private TextView tvNama, tvRole,kasir;
    private ImageView logo;
    private ProgressDialog pd;
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
        btnKelolaLayanan = findViewById(R.id.btnLayanan);
        btnKelolaProduk = findViewById(R.id.btnProduk);
        tvNama = findViewById(R.id.tvNamaPegawai2);
        tvRole = findViewById(R.id.tvRolePegawaiAdmin);
        btnPindah = findViewById(R.id.btnPindahKelola);
        kasir = findViewById(R.id.tvKasir);
        logo = findViewById(R.id.imageViewKasir);
        pd = new ProgressDialog(MenuAdmin.this);
        pd.setMessage("Logging Out...");

        sm = new SessionManager(MenuAdmin.this);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        tvNama.setText(map.get(sm.KEY_NAMA));
        tvRole.setText(map.get(sm.KEY_ROLE));

        if(map.get(sm.KEY_ROLE).equals("Customer Service")){
            btnKelolaCustomer.setVisibility(View.GONE);
            btnKelolaPegawai.setVisibility(View.GONE);
            btnKelolaJenisHewan.setVisibility(View.GONE);
            btnKelolaHewan.setVisibility(View.GONE);
            btnKelolaUkuranHewan.setVisibility(View.GONE);
            btnKelolaLayanan.setVisibility(View.GONE);
            btnKelolaProduk.setVisibility(View.GONE);

        }else if(map.get(sm.KEY_ROLE).equals("Kasir")){
            btnKelolaCustomer.setVisibility(View.GONE);
            btnKelolaPegawai.setVisibility(View.GONE);
            btnKelolaJenisHewan.setVisibility(View.GONE);
            btnKelolaHewan.setVisibility(View.GONE);
            btnKelolaUkuranHewan.setVisibility(View.GONE);
            btnKelolaLayanan.setVisibility(View.GONE);
            btnKelolaProduk.setVisibility(View.GONE);
            btnPindah.setVisibility(View.GONE);
            kasir.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
        }

        btnKelolaProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdmin.this, KelolaProduk.class);
                startActivity(i);
            }
        });

        btnKelolaLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdmin.this, KelolaLayanan.class);
                startActivity(i);
            }
        });

        btnPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdmin.this, MenuAdminTransaksi.class);
                startActivity(i);
            }
        });

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
                pd.show();
                sm.logout();
                sm.checkLogin();
                Intent i = new Intent(MenuAdmin.this, MainActivity.class);
                startActivity(i);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                }, 4000);
                Toast.makeText(MenuAdmin.this, "Berhasil Logout!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
