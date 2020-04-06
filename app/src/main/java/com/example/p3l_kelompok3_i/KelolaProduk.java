package com.example.p3l_kelompok3_i;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_pegawai.ResponPegawai;
import com.example.p3l_kelompok3_i.model_produk.ResponProduk;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaProduk extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE =1 ;
    EditText nama_produk, harga_produk,stok_produk, stok_minimal_produk;
    Button btncreate, btnTampil, btnUpdate, btnDelete, btnGaleri;
    ImageView imgHolder;
    String iddata, dataNamaProduk,dataGambarProduk;
    Integer dataStokProduk,dataStokMinimalProduk ,dataHargaProduk;
    ProgressDialog pd;
    final int REQUEST_GALLERY = 9544;
    String part_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_produk);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama_produk = (EditText) findViewById(R.id.nama_produk);
        harga_produk = (EditText) findViewById(R.id.harga_produk);
        stok_produk= (EditText) findViewById(R.id.stok_produk);
        stok_minimal_produk = (EditText) findViewById(R.id.stok_minimal_produk);

        btncreate = (Button) findViewById(R.id.btnTambahProduk);
        btnTampil = (Button) findViewById(R.id.btnTampilProdukKelola);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProduk);
        btnDelete = (Button) findViewById(R.id.btnDeleteProduk);

        btnGaleri = (Button) findViewById(R.id.btnOpenGalery);
        imgHolder = (ImageView) findViewById(R.id.imgViewHolder);

        final Intent data = getIntent();
        iddata = data.getStringExtra("id_produk");
        dataNamaProduk = data.getStringExtra("nama_produk");
        dataHargaProduk = data.getIntExtra("harga_produk",0);
        dataStokProduk = data.getIntExtra("stok_produk", 0);
        dataStokMinimalProduk = data.getIntExtra("stok_minimal_produk", 0);
        dataGambarProduk = data.getStringExtra("gambar_produk");

        if (iddata != null) {
            btncreate.setVisibility(View.GONE);
            btnTampil.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            nama_produk.setText(data.getStringExtra("nama_produk"));
            harga_produk.setText(String.valueOf(dataHargaProduk));
            stok_produk.setText(String.valueOf(dataStokProduk));
            stok_minimal_produk.setText(String.valueOf(dataStokMinimalProduk));
            Picasso.get().load(dataGambarProduk).into(imgHolder);
        }


        pd = new ProgressDialog(this);

        btnTampil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KelolaProduk.this, TampilProduk.class);
                startActivity(i);
            }
        });

        btnGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_GALLERY);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Deleting....");
                pd.setCancelable(true);
                pd.show();
                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponProduk> deleteProduk = api.deleteProduk(iddata);

                deleteProduk.enqueue(new Callback<ResponProduk>() {
                    @Override
                    public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                        Log.d("RETRO", "response: " + "Berhasil Delete");
                        Intent intent = new Intent(KelolaProduk.this, TampilProduk.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaProduk.this, "Sukses Hapus Data Produk!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponProduk> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Delete");
                        pd.hide();
                        Toast.makeText(KelolaProduk.this, "Gagal Hapus Data Produk!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String snama = nama_produk.getText().toString();
                String sharga = harga_produk.getText().toString();
                String sstok = stok_produk.getText().toString();
                String sstokm = stok_minimal_produk.getText().toString();

                if (snama.equals("") || harga_produk.getText().toString().equals("") || stok_produk.getText().toString().equals("") || stok_minimal_produk.getText().toString().equals("")) {
                    Toast.makeText(KelolaProduk.this, "Data Produk Belum Lengkap!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!sharga.matches("^[0-9]*$")){
                    Toast.makeText(KelolaProduk.this, "Input Harga Produk tidak Valid!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!sstok.matches("^[0-9]*$")){
                    Toast.makeText(KelolaProduk.this, "Input Stok tidak Produk Valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!sstokm.matches("^[0-9]*$")){
                    Toast.makeText(KelolaProduk.this, "Input Stok Minimal Produk tidak Valid!", Toast.LENGTH_SHORT).show();
                    return;
                } else if(part_image == null){
                    Toast.makeText(KelolaProduk.this, "Gambar Produk tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                pd.setMessage("Creating....");
                pd.setCancelable(false);
                pd.show();

                File imagefile = new File(part_image);
                //MENGGUNAKAN REQUEST BODY KARENA ADA FILE UPLOAD
                RequestBody namap = RequestBody.create(MediaType.parse("text/plain"), snama);
                RequestBody hargap = RequestBody.create(MediaType.parse("text/plain"), harga_produk.getText().toString());
                RequestBody stokp = RequestBody.create(MediaType.parse("text/plain"), stok_produk.getText().toString());
                RequestBody stokm = RequestBody.create(MediaType.parse("text/plain"), stok_minimal_produk.getText().toString());
                RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);

                MultipartBody.Part partImage = MultipartBody.Part.createFormData("gambar_produk", imagefile.getName(), reqBody);

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponProduk> createProduk = api.sendProduk(namap, hargap, partImage, stokp, stokm);

                createProduk.enqueue(new Callback<ResponProduk>() {
                    @Override
                    public void onResponse(Call<ResponProduk> call, Response<ResponProduk> response) {
                        Log.d("RETRO", "response: " + "Berhasil Tambah Data Produk!");
                        Intent intent = new Intent(KelolaProduk.this, TampilProduk.class);
                        pd.hide();
                        startActivity(intent);
                        Toast.makeText(KelolaProduk.this, "Sukses Tambah Data Produk!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponProduk> call, Throwable t) {
                        Log.d("RETRO", "Failure: " + "Gagal Tambah Produk");
                        pd.hide();
                        Toast.makeText(KelolaProduk.this, "Gagal Tambah Data Produk!", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ContextCompat.checkSelfPermission(KelolaProduk.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(KelolaProduk.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(KelolaProduk.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE);
            }
        }

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY) {
                Uri dataimage = data.getData();
                String[] imageprojection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(dataimage, imageprojection, null, null, null);

                if (cursor != null) {
                    cursor.moveToFirst();
                    int indexImage = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    Log.d("RETRO", "INDEX  IMAGE: " + indexImage);

                    part_image = cursor.getString(indexImage);

                    Log.d("RETRO", "LOKASI GAMBAR: " + part_image);
                    if (part_image != null) {

                        File image = new File(part_image);
                        imgHolder.setImageBitmap(BitmapFactory.decodeFile(image.getAbsolutePath()));
                    }else{
                        Log.d("RETRO", "response: " + "PART IMAGE NULL");
                    }
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(KelolaProduk.this, MenuAdmin.class);
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
