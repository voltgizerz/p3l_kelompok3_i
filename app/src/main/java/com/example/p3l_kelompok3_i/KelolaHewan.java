package com.example.p3l_kelompok3_i;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.p3l_kelompok3_i.adapter.AdapterJenisHewan;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_customer.DataCustomer;
import com.example.p3l_kelompok3_i.model_customer.ResponCustomer;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KelolaHewan extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<DataJenisHewan> mItems = new ArrayList<>();
    private List<DataUkuranHewan> mItemsUkuran = new ArrayList<>();
    private List<DataCustomer> mItemsCustomer = new ArrayList<>();

    EditText nama_hewan;
    Button btncreate, btnTampilHewan, btnUpdate, btnDelete;
    String iddata;
    Integer dataIdJenisHewan, dataIdUkuranHewan, dataIdCustomer;
    TextView tanggal_lahir_hewan;
    private Spinner spinner, spinnerUH, spinnerC;
    ProgressDialog pd;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nama_hewan = (EditText) findViewById(R.id.data_nama_hewan);
        tanggal_lahir_hewan = (TextView) findViewById(R.id.tanggal_lahir_hewan);

        btnDelete = (Button) findViewById(R.id.btnDeleteHewan);
        btncreate = (Button) findViewById(R.id.btnTambahHewanKelola);
        btnTampilHewan = findViewById(R.id.btnTampilHewanKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateHewan);

        spinner = (Spinner) findViewById(R.id.spinnerIdJenisHewan);
        spinnerUH = (Spinner) findViewById(R.id.spinnerIdUkuranHewan);
        spinnerC = (Spinner) findViewById(R.id.spinnerIdCustomer);


        final Intent data = getIntent();
        iddata = data.getStringExtra("id_hewan");
        dataIdJenisHewan = data.getIntExtra("id_jenis_hewan", 0);
        dataIdUkuranHewan = data.getIntExtra("id_ukuran_hewan", 0);
        dataIdCustomer = data.getIntExtra("id_customer", 0);

        if (iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampilHewan.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);


            nama_hewan.setText(data.getStringExtra("nama_hewan"));
            tanggal_lahir_hewan.setText(data.getStringExtra("tanggal_lahir_hewan"));
        }

        tanggal_lahir_hewan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        KelolaHewan.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
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
                tanggal_lahir_hewan.setText(date);
            }
        };


        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponJenisHewan> getJenisHewan = api.getJenisHewanSemua();

        getJenisHewan.enqueue(new Callback<ResponJenisHewan>() {
            @Override
            public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                mItems = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                mItems.add(0, new DataJenisHewan("Pilih Jenis Hewan", "0"));

                int position = -1;
                for (int i = 0; i < mItems.size(); i++) {
                    if (mItems.get(i).getId_jenis_hewan().equals(Integer.toString(dataIdJenisHewan))) {
                        position = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID JENIS HEWAN] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID JENIS HEWAN
                ArrayAdapter<DataJenisHewan> adapter = new ArrayAdapter<DataJenisHewan>(KelolaHewan.this, R.layout.spinner_item, mItems);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
                spinner.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API JENIS HEWAN! ");
            }
        });

        Call<ResponUkuranHewan> getUkuranHewan = api.getUkuranHewanSemua();

        getUkuranHewan.enqueue(new Callback<ResponUkuranHewan>() {
            @Override
            public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                mItemsUkuran = response.body().getData();

                //ADD DATA HANYA UNTUK HINT SPINNER
                mItemsUkuran.add(0, new DataUkuranHewan("Pilih Ukuran Hewan", "0"));

                int positionUH = -1;
                for (int i = 0; i < mItemsUkuran.size(); i++) {
                    if (mItemsUkuran.get(i).getId_ukuran_hewan().equals(Integer.toString(dataIdUkuranHewan))) {
                        positionUH = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID UKURAN HEWAN] :" + Integer.toString(positionUH), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());

                //SPINNER UNTUK ID UKURAN HEWAN
                ArrayAdapter<DataUkuranHewan> adapter2 = new ArrayAdapter<DataUkuranHewan>(KelolaHewan.this, R.layout.spinner_item, mItemsUkuran);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerUH.setAdapter(adapter2);
                spinnerUH.setSelection(positionUH, true);
            }

            @Override
            public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API UKURAN HEWAN! ");
            }
        });

        Call<ResponCustomer> getCustomer = api.getCustomerSemua();
        getCustomer.enqueue(new Callback<ResponCustomer>() {
            @Override
            public void onResponse(Call<ResponCustomer> call, Response<ResponCustomer> response) {
                mItemsCustomer = response.body().getData();

                //ADD DATA HANYA UNTUK HINT SPINNER
                mItemsCustomer.add(0, new DataCustomer("0", "Pilih Customer"));

                int positionC = -1;
                for (int i = 0; i < mItemsCustomer.size(); i++) {
                    if (mItemsCustomer.get(i).getId_customer().equals(Integer.toString(dataIdCustomer))) {
                        positionC = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID CUSTOMER :" + Integer.toString(positionC), "RESPONSE : SUKSES MENDAPATKAN API CUSTOMER!  " + response.body().getData());

                //SPINNER UNTUK ID CUSTOMER
                ArrayAdapter<DataCustomer> adapter3 = new ArrayAdapter<DataCustomer>(KelolaHewan.this, R.layout.spinner_item, mItemsCustomer);
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerC.setAdapter(adapter3);
                spinnerC.setSelection(positionC, true);
            }

            @Override
            public void onFailure(Call<ResponCustomer> call, Throwable t) {
                Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API CUSTOMER! ");
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
                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinner.getSelectedItem();
                DataUkuranHewan spnUkuranHewan = (DataUkuranHewan) spinnerUH.getSelectedItem();
                DataCustomer spnCustomer = (DataCustomer) spinnerC.getSelectedItem();

                String snama = nama_hewan.getText().toString();

                if (snama.trim().equals("") || spinner.getSelectedItem() == null || spinnerUH.getSelectedItem() == null || spinnerC.getSelectedItem() == null || spinner.getSelectedItem().toString().equals("Pilih Jenis Hewan") || spinnerUH.getSelectedItem().toString().equals("Pilih Ukuran Hewan") || spinnerC.getSelectedItem().toString().equals("Pilih Customer")) {
                    Toast.makeText(KelolaHewan.this, "Data Hewan Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_ukuran_hewan = spnUkuranHewan.getId_ukuran_hewan();
                    String id_customer = spnCustomer.getId_customer();
                    Integer idcs = Integer.parseInt(id_customer);
                    String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponHewan> updateh = api.updateHewan(iddata, nama_hewan.getText().toString(), Integer.parseInt(id_jenis_hewan), Integer.parseInt(id_ukuran_hewan), idcs, tanggal_lahir_hewan.getText().toString());
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
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataJenisHewan spnJenisHewan = (DataJenisHewan) spinner.getSelectedItem();
                DataUkuranHewan spnUkuranHewan = (DataUkuranHewan) spinnerUH.getSelectedItem();
                DataCustomer spnCustomer = (DataCustomer) spinnerC.getSelectedItem();

                String stanggal = tanggal_lahir_hewan.getText().toString();
                String snama = nama_hewan.getText().toString();
                if (snama.trim().equals("") || stanggal.trim().equals("") || spinner.getSelectedItem() == null || spinnerUH.getSelectedItem() == null || spinnerC.getSelectedItem() == null || spinner.getSelectedItem().toString().equals("Pilih Jenis Hewan") || spinnerUH.getSelectedItem().toString().equals("Pilih Ukuran Hewan") || spinnerC.getSelectedItem().toString().equals("Pilih Customer")) {
                    Toast.makeText(KelolaHewan.this, "Data Hewan Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Creating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_jenis_hewan = spnJenisHewan.getId_jenis_hewan();
                    Integer sjenishewan = Integer.parseInt(id_jenis_hewan);

                    String id_ukuran_hewan = spnUkuranHewan.getId_ukuran_hewan();
                    Integer sukuranhewan = Integer.parseInt(id_ukuran_hewan);

                    String id_customer = spnCustomer.getId_customer();
                    Integer scustomer = Integer.parseInt(id_customer);


                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponHewan> createh = api.sendHewan(snama, sjenishewan, sukuranhewan, scustomer, stanggal);

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
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaHewan.this, MenuAdmin.class);
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