package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.util.HashMap;

public class MenuAdminTransaksi extends AppCompatActivity {

    Button btnSupplier;
    TextView tvNama,tvRole;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin_transaksi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sm = new SessionManager(MenuAdminTransaksi.this);
        sm.checkLogin();

        tvNama = findViewById(R.id.tvNamaPegawai3);
        tvRole = findViewById(R.id.tvRolePegawaiAdmin2);
        HashMap<String, String> map = sm.getDetailLogin();
        tvNama.setText(map.get(sm.KEY_NAMA));
        tvRole.setText(map.get(sm.KEY_ROLE));

        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuAdminTransaksi.this, KelolaSupplier.class);
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

    @Override
    public void onBackPressed() {
        closeOptionsMenu();
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }

}
