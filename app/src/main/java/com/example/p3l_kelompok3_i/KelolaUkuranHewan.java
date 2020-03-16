package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.ResponUkuranHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaUkuranHewan extends AppCompatActivity {

    EditText ukuran_hewan;
    Button btncreate;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_ukuran_hewan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ukuran_hewan = (EditText) findViewById(R.id.ukuran_hewan);
        btncreate =(Button) findViewById(R.id.btnTambahUkuranHewan);
        pd = new ProgressDialog(this);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("creating data..");
                pd.setCancelable(false);
                pd.show();
                String sukuran = ukuran_hewan.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponUkuranHewan> sendukuranhewan = api.sendUkuranHewan(sukuran);
                sendukuranhewan.enqueue(new Callback<ResponUkuranHewan>() {
                    @Override
                    public void onResponse(Call<ResponUkuranHewan> call, Response<ResponUkuranHewan> response) {
                        pd.hide();
                        Log.d("RETRO", "response: " + "Berhasil Mendaftar");
                    }

                    @Override
                    public void onFailure(Call<ResponUkuranHewan> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Failure: " + "Gagal Mendaftar");
                    }
                });
            }
        });
    }

}
