package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterProdukTampil;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_pengadaan.ResponPengadaan;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;
import com.example.p3l_kelompok3_i.pengadaan_detail.ResponPengadaanDetail;

import org.w3c.dom.Text;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaDetailPengadaan extends AppCompatActivity {

    private List<DataProduk> mItemsProduk = new ArrayList<>();
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    EditText jumlah_pengadaan;
    Button btnCreate, btnTampil, btnUpdate, btnDelete;
    Spinner spinnerProduk,spinnerSatuan;
    String iddata, dataSatuan;
    TextView tvTampilKode;
    Integer dataIdProduk;
    ProgressDialog pd;
    private static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_detail_pengadaan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePengadaan", 0);
        final String cookieName = prefs.getString("kode_pengadaan", null);

        spinnerProduk = (Spinner) findViewById(R.id.spinnerProdukPengadaan);
        jumlah_pengadaan = findViewById(R.id.jumlah_pengadaan);
        btnTampil = (Button) findViewById(R.id.btnTampilProdukPengadaan);
        btnCreate = (Button) findViewById(R.id.btnTambahProdukPengadaan);
        btnDelete = (Button) findViewById(R.id.btnDeleteProdukPengadaan);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProdukPengadaan);
        tvTampilKode = (TextView) findViewById(R.id.tampilKodeTransaksiDetail);
        tvTampilKode.setText(cookieName);
        pd = new ProgressDialog(this);

        // SETTING SPINNER UNTUK SATUAN PENGADAAN
        spinnerSatuan = (Spinner) findViewById(R.id.spinnerSatuan);
        String[] arrayStatus = new String[]{
                "Pack", "Botol","Sachet"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, arrayStatus);
        adapter.setDropDownViewResource(R.layout.spinner);
        spinnerSatuan.setAdapter(adapter);

        Intent data = getIntent();
        iddata= data.getStringExtra("id_detail_pengadaan");
        dataIdProduk = data.getIntExtra("id_produk_fk",0);
        dataSatuan = data.getStringExtra("satuan_pengadaan");

        if (iddata != null) {
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            if(dataSatuan.equals("Pack")) {
                spinnerSatuan.setSelection(0, true);
            }else if(dataSatuan.equals("Botol")){
                spinnerSatuan.setSelection(1, true);
            } else {
                spinnerSatuan.setSelection(2, true);
            }
            jumlah_pengadaan.setText(String.valueOf(data.getIntExtra("jumlah_pengadaan",0)));
        }

        //GET PRODUK UNTUK SPINNER PRODUK
        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponProduk> getProduk = api.getProdukSemua();

        getProduk.enqueue(new Callback<ResponProduk>() {
            @Override
            public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                mItemsProduk = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                mItemsProduk .add(0, new DataProduk("Pilih Produk Pengadaan" ,"0"));
                int position = -1;
                for (int i = 0; i < mItemsProduk.size(); i++) {
                    if (mItemsProduk.get(i).getId_produk().equals(Integer.toString(dataIdProduk))) {
                        position = i;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID Produk] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API PRODUK  " + response.body().getData());

                //SPINNER UNTUK ID SUPPLIER
                ArrayAdapter<DataProduk> adapter = new ArrayAdapter<DataProduk>(KelolaDetailPengadaan.this, R.layout.spinner, mItemsProduk);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerProduk.setAdapter(adapter);
                spinnerProduk.setSelection(position, true);
            }

            @Override
            public void onFailure(Call<ResponProduk> call, Throwable t) {
                pd.hide();
                if(isInternetAvailable() == false)
                {
                    Log.d("API", "RESPONSE : TIDAK ADA INTERNET! ");
                }else {
                     Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PRODUK! ");
                }

            }
        });

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaDetailPengadaan.this, KelolaPengadaan.class);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProduk spnProduk = (DataProduk) spinnerProduk.getSelectedItem();

                if (spinnerProduk.getSelectedItem() == null || spinnerProduk.getSelectedItem().toString().equals("Pilih Produk Pengadaan") || jumlah_pengadaan.getText().toString().equals(null)) {
                    Toast.makeText(KelolaDetailPengadaan.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Updating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_produk = spnProduk.getId_produk();
                    Integer sidproduk = Integer.parseInt(id_produk);
                    Integer sjumlah = Integer.parseInt(jumlah_pengadaan.getText().toString());

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPengadaanDetail> updatePengadaan = api.updatePengadaanDetail(iddata, sidproduk, sjumlah, spinnerSatuan.getSelectedItem().toString(), cookieName);

                    updatePengadaan.enqueue(new Callback<ResponPengadaanDetail>() {
                        @Override
                        public void onResponse(Call<ResponPengadaanDetail> call, Response<ResponPengadaanDetail> response) {
                            Log.d("RETRO", "response: " + "Berhasil Update");
                            Intent intent = new Intent(KelolaDetailPengadaan.this, KelolaPengadaan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPengadaan.this, "Sukses Update Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPengadaanDetail> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Update");
                            pd.hide();
                            Toast.makeText(KelolaDetailPengadaan.this, "Gagal Update Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProduk spnProduk = (DataProduk) spinnerProduk.getSelectedItem();


                if (spinnerProduk.getSelectedItem() == null || spinnerProduk.getSelectedItem().toString().equals("Pilih Produk Pengadaan") || jumlah_pengadaan.getText().toString().equals("")) {
                    Toast.makeText(KelolaDetailPengadaan.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Creating....");
                    pd.setCancelable(false);
                    pd.show();

                    String id_produk = spnProduk.getId_produk();
                    Integer sidproduk = Integer.parseInt(id_produk);
                    Integer sjumlah = Integer.parseInt(jumlah_pengadaan.getText().toString());

                    ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponPengadaanDetail> createPengadaanDetail = api.sendPengadaanDetail(sidproduk,cookieName,spinnerSatuan.getSelectedItem().toString(),sjumlah);

                    createPengadaanDetail.enqueue(new Callback<ResponPengadaanDetail>() {
                        @Override
                        public void onResponse(Call<ResponPengadaanDetail> call, Response<ResponPengadaanDetail> response) {
                            Log.d("RETRO", "response: " + "Berhasil Create");

                            Intent intent = new Intent(KelolaDetailPengadaan.this, KelolaPengadaan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaDetailPengadaan.this, "Sukses Tambah Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponPengadaanDetail> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal Create");
                            pd.hide();
                            Toast.makeText(KelolaDetailPengadaan.this, "Gagal Tambah Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(false);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponPengadaanDetail> deletePengadaanDetail= api.deletePengadaanDetail(iddata, cookieName);

                deletePengadaanDetail.enqueue(new Callback<ResponPengadaanDetail>() {
                    @Override
                    public void onResponse(Call<ResponPengadaanDetail> call, Response<ResponPengadaanDetail> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaDetailPengadaan.this, KelolaPengadaan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaDetailPengadaan.this, "Sukses Hapus Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPengadaanDetail> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaDetailPengadaan.this, "Gagal Hapus Produk Pengadaan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getApplication().getSharedPreferences("KodePengadaan", 0).edit().clear().commit();
                Intent intent = new Intent(KelolaDetailPengadaan.this, KelolaPengadaan.class);
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
        getApplication().getSharedPreferences("KodePengadaan", 0).edit().clear().commit();
        Intent intent = new Intent(this, KelolaPengadaan.class);
        startActivity(intent);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
}
