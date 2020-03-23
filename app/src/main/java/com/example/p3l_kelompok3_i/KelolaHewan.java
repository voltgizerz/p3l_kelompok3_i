package com.example.p3l_kelompok3_i;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p3l_kelompok3_i.adapter.AdapterJenisHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaHewan extends AppCompatActivity {

    private AdapterJenisHewan mAdapterJenisHewan;
    private List<DataJenisHewan> mItems = new ArrayList<>();

    EditText nama_hewan, id_ukuran_hewan, id_customer, tanggal_lahir_hewan;
    Button btncreate, btnTampilHewan, btnUpdate,btnDelete;
    String iddata;
    Integer dataIdJenisHewan;
    private Spinner spinner;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_hewan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_hewan = (EditText) findViewById(R.id.data_nama_hewan);
        id_ukuran_hewan = (EditText) findViewById(R.id.data_id_ukuran_hewan);
        id_customer = (EditText) findViewById(R.id.data_id_customer);
        tanggal_lahir_hewan = (EditText) findViewById(R.id.data_tanggal_lahir_hewan);

        btnDelete = (Button) findViewById(R.id.btnDeleteHewan);
        btncreate = (Button) findViewById(R.id.btnTambahHewanKelola);
        btnTampilHewan = findViewById(R.id.btnTampilHewanKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateHewan) ;

        spinner = (Spinner) findViewById(R.id.spinnerIdJenisHewan);





        final Intent data = getIntent();
        iddata = data.getStringExtra("id_hewan");
        dataIdJenisHewan = data.getIntExtra("id_jenis_hewan",0);

        if(iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilHewan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);


            nama_hewan.setText(data.getStringExtra("nama_hewan"));
            id_ukuran_hewan.setText(String.valueOf(data.getIntExtra("id_ukuran_hewan",0)));
            id_customer.setText(String.valueOf(data.getIntExtra("id_customer",0)));

            tanggal_lahir_hewan.setText(data.getStringExtra("tanggal_lahir_hewan"));
        }

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponJenisHewan> getJenisHewan = api.getJenisHewanSemua();

        getJenisHewan.enqueue(new Callback<ResponJenisHewan>() {
            @Override
            public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                Log.d("API","RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());
                mItems = response.body().getData();
                //SPINNER UNTUK ID JENIS HEWAN
                ArrayAdapter<DataJenisHewan> adapter = new ArrayAdapter<DataJenisHewan>(KelolaHewan.this, android.R.layout.simple_spinner_item,mItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                if(dataIdJenisHewan== 0)
                {
                    spinner.setAdapter(adapter);
                }else {
                    spinner.setAdapter(adapter);
                    spinner.setSelection(dataIdJenisHewan-1,true);
                }
            }
            @Override
            public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                pd.hide();
                Toast.makeText(KelolaHewan.this, "GAGAL MENAMPILKAN DATA JENIS HEWAN!", Toast.LENGTH_SHORT).show();
                Log.d("API","RESPONSE : GAGAL MENDAPATKAN API JENIS HEWAN! ");
            }
        });

        pd = new ProgressDialog(this);

        btnTampilHewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaHewan.this, TampilHewan.class);
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
                Call<ResponHewan> deleteh = api.deleteHewan(iddata);

                deleteh.enqueue(new Callback<ResponHewan>() {
                    @Override
                    public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaHewan.this, TampilHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaHewan.this, "Sukses Hapus Data Hewan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaHewan.this, "Gagal Hapus Data Hewan!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("Updating....");
                pd.setCancelable(false);
                pd.show();

                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinner.getSelectedItem();

                String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponHewan> updateh = api.updateHewan(iddata, nama_hewan.getText().toString(),id_jenis_hewan,id_ukuran_hewan.getText().toString(),id_customer.getText().toString(),tanggal_lahir_hewan.getText().toString() );
                updateh.enqueue(new Callback<ResponHewan>() {
                    @Override
                    public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Update");
                        Intent intent = new Intent(KelolaHewan.this, TampilHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaHewan.this, "Sukses Edit Data Hewan!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<ResponHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Update");
                        pd.hide();
                        Toast.makeText(KelolaHewan.this, "Gagal Edit Data Hewan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating....");
                pd.setCancelable(false);
                pd.show();

                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinner.getSelectedItem();

                String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();

                String snama = nama_hewan.getText().toString();

                Integer sjenishewan = Integer.parseInt(id_jenis_hewan);

                String strukuranhewan = id_ukuran_hewan.getText().toString();
                Integer sukuranhewan = Integer.parseInt(strukuranhewan);

                String strcustomer = id_customer.getText().toString();
                Integer scustomer = Integer.parseInt(strcustomer);

                String stanggal =  tanggal_lahir_hewan.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponHewan> createh = api.sendHewan(snama,sjenishewan,sukuranhewan,scustomer,stanggal);

                createh.enqueue(new Callback<ResponHewan>() {
                    @Override
                    public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Create");
                        Intent intent = new Intent(KelolaHewan.this, TampilHewan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaHewan.this, "Sukses Tambah Data Hewan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponHewan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Create");
                        pd.hide();
                        Toast.makeText(KelolaHewan.this, "Gagal Tambah Data Hewan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dataIdJenisHewan = null;
                Intent intent = new Intent(KelolaHewan.this, MenuAdmin.class);
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
        dataIdJenisHewan = null;
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }
}