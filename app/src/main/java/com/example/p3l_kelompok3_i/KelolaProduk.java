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

import java.io.File;

public class KelolaProduk extends AppCompatActivity {

    private static final int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE =1 ;
    EditText nama_produk, harga_produk,stok_produk, stok_minimal_produk;
    Button btncreate, btnTampil, btnUpdate, btnDelete, btnGaleri;
    ImageView imgHolder;
    String iddata;
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
