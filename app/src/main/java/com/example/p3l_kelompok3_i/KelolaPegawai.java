package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPegawai extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tanggal_lahir_pegawai;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText nama_pegawai, alamat_pegawai,  nomor_hp_pegawai,role_pegawai,username,password;
    Button btncreate, btnTampilPegawai;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_pegawai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_pegawai = (EditText) findViewById(R.id.nama_pegawai);
        alamat_pegawai = (EditText) findViewById(R.id.alamat_pegawai);
        tanggal_lahir_pegawai = (TextView) findViewById(R.id.tanggal_lahir_pegawai);
        nomor_hp_pegawai = (EditText) findViewById(R.id.nomor_hp_pegawai);
        role_pegawai = (EditText) findViewById(R.id.role_pegawai);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        btncreate = (Button) findViewById(R.id.btnTambahPegawai);
        btnTampilPegawai = findViewById(R.id.btnTampilPegawai);

        pd = new ProgressDialog(this);


            tanggal_lahir_pegawai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            KelolaPegawai.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,
                            year,month,day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = year + "-" + month + "-" + day;
                tanggal_lahir_pegawai.setText(date);
            }
        };


        btnTampilPegawai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPegawai.this, TampilPegawai.class);
                startActivity(i);
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("creating data..");
                pd.setCancelable(false);
                pd.show();
                String snama = nama_pegawai.getText().toString();
                String salamat = alamat_pegawai.getText().toString();
                String stanggal_lahir = tanggal_lahir_pegawai.getText().toString();
                String sno_hp = nomor_hp_pegawai.getText().toString();
                String srole = role_pegawai.getText().toString();
                String susername = username.getText().toString();
                String spassword= password.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponPegawai> sendPegawai = api.sendPegawai(snama,salamat,stanggal_lahir,sno_hp,srole,susername,spassword);
                sendPegawai.enqueue(new Callback<ResponPegawai>() {
                    @Override
                    public void onResponse(Call<ResponPegawai> call, Response<ResponPegawai> response) {
                        pd.hide();
                        Toast.makeText(KelolaPegawai.this, "Sukses Tambah Data Pegawai!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "response: " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ResponPegawai> call, Throwable t) {
                        pd.hide();
                        Toast.makeText(KelolaPegawai.this, "Gagal Tambah Data Pegawai!", Toast.LENGTH_SHORT).show();
                        Log.d("RETRO", "Failure: " + "Gagal Mendaftar");
                    }
                });
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaPegawai.this, MenuAdmin.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}