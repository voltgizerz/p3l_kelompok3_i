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
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPegawai extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tanggal_lahir_pegawai;
    String iddata;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    EditText nama_pegawai, alamat_pegawai,  nomor_hp_pegawai,role_pegawai,username,password;
    Button btncreate, btnTampilPegawai, btnUpdate, btnDelete;
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

        btnDelete = findViewById(R.id.btnDeletePegawai);

        btnUpdate = (Button) findViewById(R.id.btnUpdatePegawai) ;

        Intent data = getIntent();
        iddata = data.getStringExtra("id_pegawai");
        if(iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilPegawai.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            nama_pegawai.setText(data.getStringExtra("nama_pegawai"));
            alamat_pegawai.setText(data.getStringExtra("alamat_pegawai"));
            tanggal_lahir_pegawai.setText(data.getStringExtra("tanggal_lahir_pegawai"));
            nomor_hp_pegawai.setText(data.getStringExtra("nomor_hp_pegawai"));
            role_pegawai.setText(data.getStringExtra("role_pegawai"));
            username.setText(data.getStringExtra("username"));
            password.setText(data.getStringExtra("password"));
        }

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

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPegawai> updatepg = api.deletePegawai(iddata);

                updatepg.enqueue(new Callback<ResponPegawai>() {
                    @Override
                    public void onResponse(Call<ResponPegawai> call, Response<ResponPegawai> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaPegawai.this, TampilPegawai.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPegawai.this, "Sukses Hapus Data Pegawai!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<ResponPegawai> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaPegawai.this, "Gagal Hapus Data Pegawai!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snama = nama_pegawai.getText().toString();
                String salamat = alamat_pegawai.getText().toString();
                String stanggal_lahir = tanggal_lahir_pegawai.getText().toString();
                String sno_hp = nomor_hp_pegawai.getText().toString();
                String srole = role_pegawai.getText().toString();
                String susername = username.getText().toString();
                String spassword = password.getText().toString();

                if (snama.trim().equals("") || salamat.trim().equals("") || stanggal_lahir.trim().equals("") || sno_hp.trim().equals("") || srole.trim().equals("") || susername.trim().equals("") || spassword.trim().equals("")) {
                    Toast.makeText(KelolaPegawai.this, "Data Pegawai Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(sno_hp.length()<10 || sno_hp.length()>13 || !sno_hp.matches("^08[0-9]{10,}$") )
                    {
                        Toast.makeText(KelolaPegawai.this, "Nomor Handphone Minimal 10-13 Karakter!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();
                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPegawai> updatepg = api.updatePegawai(iddata, nama_pegawai.getText().toString(), alamat_pegawai.getText().toString(), tanggal_lahir_pegawai.getText().toString(), nomor_hp_pegawai.getText().toString(), role_pegawai.getText().toString(), username.getText().toString(), password.getText().toString());
                    updatepg.enqueue(new Callback<ResponPegawai>() {
                        @Override
                        public void onResponse(Call<ResponPegawai> call, Response<ResponPegawai> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaPegawai.this, TampilPegawai.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPegawai.this, "Sukses Edit Data Pegawai!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPegawai> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaPegawai.this, "Gagal Edit Data Pegawai!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String snama = nama_pegawai.getText().toString();
                String salamat = alamat_pegawai.getText().toString();
                String stanggal_lahir = tanggal_lahir_pegawai.getText().toString();
                String sno_hp = nomor_hp_pegawai.getText().toString();
                String srole = role_pegawai.getText().toString();
                String susername = username.getText().toString();
                String spassword = password.getText().toString();

                if (snama.trim().equals("") || salamat.trim().equals("") || stanggal_lahir.trim().equals("") || sno_hp.trim().equals("") || srole.trim().equals("") || susername.trim().equals("") || spassword.trim().equals("") ) {
                    Toast.makeText(KelolaPegawai.this, "Data Pegawai Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(sno_hp.length()<10 || sno_hp.length()>13 || !sno_hp.matches("^08[0-9]{10,}$") )
                    {
                        Toast.makeText(KelolaPegawai.this, "Nomor Handphone Minimal 10-13 Karakter!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pd.setMessage("creating data..");
                    pd.setCancelable(false);
                    pd.show();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                    Call<ResponPegawai> sendPegawai = api.sendPegawai(snama, salamat, stanggal_lahir, sno_hp, srole, susername, spassword);
                    sendPegawai.enqueue(new Callback<ResponPegawai>() {
                        @Override
                        public void onResponse(Call<ResponPegawai> call, Response<ResponPegawai> response) {
                            pd.hide();
                            Log.d("RETRO", "response: " + response.body().toString());
                            Intent intent = new Intent(KelolaPegawai.this, TampilCustomer.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPegawai.this, "Sukses Tambah Data Customer!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPegawai> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaPegawai.this, "Gagal Tambah Data Pegawai!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Mendaftar");
                        }
                    });
                }
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

    @Override
    public void onBackPressed(){
        closeOptionsMenu();
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }
}