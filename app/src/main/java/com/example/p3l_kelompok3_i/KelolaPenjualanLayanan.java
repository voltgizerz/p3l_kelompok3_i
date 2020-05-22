package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.adapter.AdapterPenjualanLayananDetail;
import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_hewan.DataHewan;
import com.example.p3l_kelompok3_i.model_hewan.ResponHewan;
import com.example.p3l_kelompok3_i.model_login.SessionManager;
import com.example.p3l_kelompok3_i.model_penjualan_layanan.ResponPenjualanLayanan;
import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;
import com.example.p3l_kelompok3_i.penjualan_layanan_detail.DataPenjualanLayananDetail;
import com.example.p3l_kelompok3_i.penjualan_layanan_detail.ResponPenjualanLayananDetail;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.grandcentrix.tray.AppPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaPenjualanLayanan extends AppCompatActivity {

    private AdapterPenjualanLayananDetail mAdapterPenjualan;
    private List<DataPenjualanLayananDetail> mItems = new ArrayList<>();
    private List<DataPenjualanLayananDetail> saringList = new ArrayList<>();
    private List<DataHewan> mItemsHewan = new ArrayList<>();
    private List<DataHewan> saringhewan = new ArrayList<>();
    Button btnCreate, btnTampil, btnUpdate, btnDelete, btnTambahLayanan,btnTampiLayananSelesai;
    String iddata, iddatakode, cekAdaLayanan;
    TextView namaPegawai, textbiasa, textKode, tampilKosong, tvJudul;
    Integer idPegawaiLogin,dataIdHewan ;
    Spinner statusPenjualan,spinnerHewan;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    ProgressDialog pd;
    SessionManager sm;
    private static SharedPreferences prefs, sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_penjualan_layanan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AppPreferences appPreferences = new AppPreferences(getApplicationContext()); // this Preference comes for free from the library

        //GET KODE TRANSAKSI DARI SHAREDPREFENCE
        prefs = getApplication().getSharedPreferences("KodePenjualanLayanan", 0);
        final String cookieName = prefs.getString("kode_penjualan_layanan", null);

        String statusPenjualanLayanan = getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).getString("status_penjualan_layanan", null);
        String idPenjualanLayanan = getApplication().getSharedPreferences("IdPenjualanLayanan", 0).getString("id_transaksi_penjualan_layanan", null);


        btnCreate = (Button) findViewById(R.id.btnTambahPenjualanLayanan);
        btnTampil = (Button) findViewById(R.id.btnTampilPenjualanLayanan);
        btnUpdate = (Button) findViewById(R.id.btnUpdatePenjualanLayanan);
        btnDelete = (Button) findViewById(R.id.btnDeletePenjualanLayanan);
        btnTambahLayanan = findViewById(R.id.btnTambahLayananDetail);
        namaPegawai = (TextView) findViewById(R.id.tvNamaPegawaiPenjualanLayanan);
        textbiasa = (TextView) findViewById(R.id.tvIdPegawaiPenjualanLayanan);
        textKode = findViewById(R.id.tampilKodeTransaksiPenjualanLayanan);
        tampilKosong = (TextView) findViewById(R.id.tvLayananMasihKosongPenjualanLayanan);
        tvJudul = findViewById(R.id.tvJudulPenjualanLayanan);
        btnTampiLayananSelesai = findViewById(R.id.btnTampilLayananSelesai);

        mRecycler = (RecyclerView) findViewById(R.id.recyclerDetailPenjualanLayanan);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        sm = new SessionManager(KelolaPenjualanLayanan.this);
        sm.checkLogin();
        HashMap<String, String> map = sm.getDetailLogin();
        namaPegawai.setText(map.get(sm.KEY_NAMA));
        textbiasa.setText("Nama Customer Service");
        pd = new ProgressDialog(this);
        spinnerHewan = (Spinner) findViewById(R.id.spinnerIdHewanPenjualan);
        idPegawaiLogin = Integer.parseInt(map.get(sm.KEY_ID));

        if (cookieName != null) {
            ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponPenjualanLayananDetail> getPenjualanD = api.getPenjualanLayananDetailSemua();

            getPenjualanD.enqueue(new Callback<ResponPenjualanLayananDetail>() {
                @Override
                public void onResponse(Call<ResponPenjualanLayananDetail> call, Response<ResponPenjualanLayananDetail> response) {
                    pd.hide();
                    mItems = response.body().getData();
                    List<DataPenjualanLayananDetail> a = mItems;
                    for (DataPenjualanLayananDetail data : a) {
                        if (data.getKode_transaksi_penjualan_jasa_layanan_fk().startsWith(cookieName)) {
                            saringList.add(data);
                        }
                    }
                    Log.d("API", "RESPONSE : SUKSES MENDAPATKAN API LAYANAN DIBELI!  " + saringList);
                    if (saringList.isEmpty() == true) {

                        appPreferences.put("cekLayananPenjualan", "Tidak");
                        final String value = appPreferences.getString("cekLayananPenjualan", "default");
                        cekAdaLayanan = appPreferences.getString("cekLayananPenjualan", "default");
                        if (cekAdaLayanan.equals("Ada")) {
                            mRecycler.setVisibility(View.VISIBLE);
                        } else {
                            tampilKosong.setVisibility(View.VISIBLE);
                        }
                    } else {
                        appPreferences.put("cekLayananPenjualan", "Ada");
                        final String value = appPreferences.getString("cekLayananPenjualan", "default");
                        cekAdaLayanan = appPreferences.getString("cekLayananPenjualan", "default");
                        if (cekAdaLayanan.equals("Ada")) {
                            mRecycler.setVisibility(View.VISIBLE);
                        } else {
                            tampilKosong.setVisibility(View.VISIBLE);
                        }
                    }
                    mAdapterPenjualan = new AdapterPenjualanLayananDetail(KelolaPenjualanLayanan.this, mItems);
                    mRecycler.setAdapter(mAdapterPenjualan);
                    mAdapterPenjualan.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ResponPenjualanLayananDetail> call, Throwable t) {
                    pd.hide();
                    if (isInternetAvailable() == false) {
                        Toast.makeText(KelolaPenjualanLayanan.this, "Tidak ada Koneksi Internet!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(KelolaPenjualanLayanan.this, "GAGAL MENAMPILKAN DATA LAYANAN DIPESAN!", Toast.LENGTH_SHORT).show();
                        Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API PENGADAAN! ");
                    }
                }
            });
        }

        statusPenjualan = (Spinner) findViewById(R.id.spinnerStatusPenjualanLayanan);
        String[] arrayStatus = new String[]{
                "Belum Selesai", "Sudah Selesai"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, arrayStatus);
        adapter.setDropDownViewResource(R.layout.spinner);
        statusPenjualan.setAdapter(adapter);

        Intent data = getIntent();
        iddata = data.getStringExtra("id_transaksi_penjualan_layanan");
        iddatakode = data.getStringExtra("kode_transaksi_penjualan_layanan");
        dataIdHewan = data.getIntExtra("id_hewan_penjualan_layanan", 0);
        if (iddata != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);

            btnTampiLayananSelesai.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);
            if (data.getStringExtra("status_penjualan").equals("Belum Selesai")) {
                btnTambahLayanan.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(data.getStringExtra("kode_transaksi_penjualan_layanan"));

        } else if (statusPenjualanLayanan != null) {
            textbiasa.setVisibility(View.GONE);
            namaPegawai.setVisibility(View.GONE);
            btnTampiLayananSelesai.setVisibility(View.GONE);
            btnCreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            textKode.setVisibility(View.VISIBLE);
            tvJudul.setVisibility(View.VISIBLE);
            statusPenjualan.setVisibility(View.VISIBLE);

            iddata = idPenjualanLayanan;


            if (statusPenjualanLayanan.equals("Belum Selesai")) {
                btnTambahLayanan.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                statusPenjualan.setSelection(0, true);
            } else {
                statusPenjualan.setSelection(1, true);
            }
            btnDelete.setVisibility(View.VISIBLE);

            textKode.setText(cookieName);
        }

        ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponHewan> getHewan = api.getHewanSemua();

        getHewan.enqueue(new Callback<ResponHewan>() {
            @Override
            public void onResponse(Call<ResponHewan> call, Response<ResponHewan> response) {
                mItemsHewan = response.body().getData();
                //ADD DATA HANYA UNTUK HINT SPINNER
                List<DataHewan> a = mItemsHewan;
                for(DataHewan data : a){
                    if(!data.getCreated_date().equals("0000-00-00 00:00:00") ){
                        saringhewan.add(data);
                    }
                }
                Collections.sort(saringhewan, DataHewan.BY_NAME_ALPAHBETICAL);
                //ADD DATA HANYA UNTUK HINT SPINNER
                int position = -1;
                for (int i = 0; i < saringhewan.size(); i++) {
                    if (saringhewan.get(i).getId_hewan().equals(Integer.toString(dataIdHewan))) {
                        position = i + 1;
                        // break;  // uncomment to get the first instance
                    }
                }
                Log.d("[POSISI ID Supplier] :" + Integer.toString(position), "RESPONSE : SUKSES MENDAPATKAN API JENIS HEWAN!  " + response.body().getData());
                saringhewan.add(0, new DataHewan("Pilih Hewan", "0"));
                //SPINNER UNTUK ID SUPPLIER
                ArrayAdapter<DataHewan> adapter = new ArrayAdapter<DataHewan>(KelolaPenjualanLayanan.this, R.layout.spinner, saringhewan);
                adapter.setDropDownViewResource(R.layout.spinner);
                adapter.notifyDataSetChanged();
                spinnerHewan.setAdapter(adapter);
                spinnerHewan.setSelection(position, true);
            }
            @Override
            public void onFailure(Call<ResponHewan> call, Throwable t) {
                if (isInternetAvailable() == false) {
                    Log.d("API", "RESPONSE : TIDAK ADA KONEKSI INTERNET! ");
                } else {
                    Log.d("API", "RESPONSE : GAGAL MENDAPATKAN API HEWAN! ");
                }

            }
        });

        btnTampiLayananSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KelolaPenjualanLayanan.this, TampilLayananSudahSelesai.class);
                startActivity(i);
            }
        });

        btnTambahLayanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaPenjualanLayanan.this, KelolaDetailPenjualanLayanan.class);
                startActivity(i);
            }
        });

        btnTampil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                    startActivity(i);
                }
            });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Updating...");
                pd.setCancelable(false);
                pd.show();

                String status = statusPenjualan.getSelectedItem().toString();
                DataHewan spinnerIdHewan = (DataHewan) spinnerHewan.getSelectedItem();
                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                if (saringList.isEmpty() == true || spinnerHewan.getSelectedItem() == null || spinnerHewan.getSelectedItem().toString().equals("Pilih Hewan") ) {
                    pd.dismiss();
                    Toast.makeText(KelolaPenjualanLayanan.this, "Data Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Call<ResponPenjualanLayanan> updatePenjualanLayanan = api.updatePenjualanLayanan(iddata, status, cookieName,Integer.parseInt(spinnerIdHewan.getId_hewan()));
                    updatePenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                            pd.dismiss();
                            ResponPenjualanLayanan res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil update");
                            Intent intent = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPenjualanLayanan.this, "Sukses Update Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaPenjualanLayanan.this, "Gagal Update Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Update Data");
                        }
                    });
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(true);
                pd.show();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Log.d("RETRO", "response: " + "Berhasil Delete"+iddata+cookieName);
                Call<ResponPenjualanLayanan> deletePenjualanLayanan = api.deletePenjualanLayanan(iddata, cookieName);

                deletePenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
                    @Override
                    public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        getApplication().getSharedPreferences("KodePenjualanLayanan", 0).edit().clear().commit();
                        getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).edit().clear().commit();
                        getApplication().getSharedPreferences("IdPenjualanLayanan", 0).edit().clear().commit();
                        Intent intent = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaPenjualanLayanan.this, "Sukses Hapus Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Hapus");
                        pd.hide();
                        Toast.makeText(KelolaPenjualanLayanan.this, "Gagal Hapus Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataHewan spinnerIdHewan = (DataHewan) spinnerHewan.getSelectedItem();
                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                if (spinnerHewan.getSelectedItem() == null || spinnerHewan.getSelectedItem().toString().equals("Pilih Hewan") ) {
                    Toast.makeText(KelolaPenjualanLayanan.this, "Data Hewan Belum Ada!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    pd.setMessage("Creating...");
                    pd.setCancelable(false);
                    pd.show();
                    Call<ResponPenjualanLayanan> sendPenjualanLayanan = api.sendPenjualanLayanan(idPegawaiLogin, idPegawaiLogin, Integer.parseInt(spinnerIdHewan.getId_hewan()) );
                    sendPenjualanLayanan.enqueue(new Callback<ResponPenjualanLayanan>() {
                        @Override
                        public void onResponse(Call<ResponPenjualanLayanan> call, Response<ResponPenjualanLayanan> response) {
                            pd.dismiss();
                            ResponPenjualanLayanan res = response.body();

                            Log.d("RETRO", "response: " + "Berhasil Create");
                            Intent intent = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                            pd.hide();
                            startActivity(intent);
                            Toast.makeText(KelolaPenjualanLayanan.this, "Sukses Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onFailure(Call<ResponPenjualanLayanan> call, Throwable t) {
                            pd.hide();
                            Toast.makeText(KelolaPenjualanLayanan.this, "Gagal Tambah Transaksi Penjualan!", Toast.LENGTH_SHORT).show();
                            Log.d("RETRO", "Failure: " + "Gagal Tambah Data");


                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String statusPenjualanLayanan = getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).getString("status_penjualan_layanan", null);
        if (statusPenjualanLayanan == null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    pd.dismiss();
                    Intent intent = new Intent(KelolaPenjualanLayanan.this, MenuAdminTransaksi.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            switch (item.getItemId()) {
                case android.R.id.home:
                    getApplication().getSharedPreferences("KodePenjualanLayanan", 0).edit().clear().commit();
                    getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).edit().clear().commit();
                    getApplication().getSharedPreferences("IdPenjualanLayanan", 0).edit().clear().commit();
                    pd.dismiss();
                    Intent intent = new Intent(KelolaPenjualanLayanan.this, TampilPenjualanLayanan.class);
                    startActivity(intent);
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }

    @Override
    public void onBackPressed() {
        String statusPenjualanLayanan = getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).getString("status_penjualan_layanan", null);
        if (statusPenjualanLayanan == null) {
            closeOptionsMenu();
            Intent intent = new Intent(this, MenuAdminTransaksi.class);
            startActivity(intent);
        }else{
            closeOptionsMenu();
            getApplication().getSharedPreferences("KodePenjualanLayanan", 0).edit().clear().commit();
            getApplication().getSharedPreferences("StatusPenjualanLayanan", 0).edit().clear().commit();
            getApplication().getSharedPreferences("IdPenjualanLayanan", 0).edit().clear().commit();
            Intent intent = new Intent(this, TampilPenjualanLayanan.class);
            startActivity(intent);
        }
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

