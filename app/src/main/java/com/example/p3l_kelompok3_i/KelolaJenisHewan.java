package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jenis_hewan.ResponJenisHewan;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KelolaJenisHewan extends AppCompatActivity {

    EditText nama_jenis_hewan;
    Button btncreate;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_jenis_hewan);

        nama_jenis_hewan = (EditText) findViewById(R.id.nama_jenis_hewan);
        btncreate =(Button) findViewById(R.id.btn_create_jenis_hewan);
        pd = new ProgressDialog(this);

        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setMessage("creating data..");
                pd.setCancelable(false);
                pd.show();
                String snama = nama_jenis_hewan.getText().toString();

                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);

                Call<ResponJenisHewan> sendjenishewan = api.sendJenisHewan(snama);
                sendjenishewan.enqueue(new Callback<ResponJenisHewan>() {
                    @Override
                    public void onResponse(Call<ResponJenisHewan> call, Response<ResponJenisHewan> response) {
                        pd.hide();
                        Log.d("RETRO", "response: " + "Berhasil Mendaftar");

                    }
                    @Override
                    public void onFailure(Call<ResponJenisHewan> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Failure: " + "Gagal Mendaftar");
                    }

                });
            }
        });
    }
}
