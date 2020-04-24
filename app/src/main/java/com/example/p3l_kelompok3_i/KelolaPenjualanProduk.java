package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_penjualan_produk.ResponPenjualanProduk;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPenjualanProduk extends AppCompatActivity {

    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    String iddata;
    TextView namaPegawai,textbiasa;
    Integer idPegawaiLogin;
    ProgressDialog pd;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnCreate = (Button) findViewById(R.id.btnTambahPenjualanProduk);
        btnTampil = (Button) findViewById(R.id.btnTampilPenjualanroduk);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePenjualanProduk);
        btnDelete = (Button) findViewById(R.id.btnDeletePenjualanProduk);
        namaPegawai = (TextView) findViewById(R.id.tvNamaPegawaiPenjualanProduk);
        textbiasa = (TextView) findViewById(R.id.tvIdPegawaiPenjualanProduk);
        pd = new ProgressDialog(this);

        sm = new SessionManager(KelolaPenjualanProduk.this);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        namaPegawai.setText(map.get(sm.KEY_NAMA));
        textbiasa.setText("Nama Customer Service");

        idPegawaiLogin = Integer.parseInt(map.get(sm.KEY_ID));
        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                startActivity(i);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating...");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponPenjualanProduk> sendPenjualanProduk = api.sendPenjualanProduk(idPegawaiLogin,idPegawaiLogin);
                sendPenjualanProduk.enqueue(new Callback<ResponPenjualanProduk>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanProduk> call, Response<ResponPenjualanProduk> response) {
                        pd.dismiss();
                        ResponPenjualanProduk res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaPenjualanProduk.this, TampilPenjualanProduk.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPenjualanProduk.this, "Sukses Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        }


                    @Override
                    public void onFailure(Call<ResponPenjualanProduk> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaPenjualanProduk.this, "Gagal Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Tambah Data");


                }
            });
        }
    });
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                pd.dismiss();
                Intent intent = new Intent(KelolaPenjualanProduk.this, MenuAdminTransaksi.class);
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
        Intent intent = new Intent(this, MenuAdminTransaksi.class);
        startActivity(intent);
    }

}
