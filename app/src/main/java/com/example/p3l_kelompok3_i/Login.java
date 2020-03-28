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
import android.widget.Toast;

import com.example.p3l_kelompok3_i.api.ApiClient;
import com.example.p3l_kelompok3_i.api.ApiInterface;
import com.example.p3l_kelompok3_i.model_jasa_layanan.ResponLayanan;
import com.example.p3l_kelompok3_i.model_login.DataLogin;
import com.example.p3l_kelompok3_i.model_login.ResponLogin;
import com.example.p3l_kelompok3_i.model_login.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private SessionManager sm;
    private EditText etUsername, etPassword;
    private Button btnMasukMenu;
    private ProgressDialog pd;
    private static final String TAG = Login.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnMasukMenu = findViewById(R.id.btnMasukMenu);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        pd = new ProgressDialog(Login.this);
        pd.setMessage("Loading...");
        sm = new SessionManager(Login.this);

        btnMasukMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                ApiInterface api = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponLogin> loginPegawai = api.loginPegawai(etUsername.getText().toString(), etPassword.getText().toString());

                loginPegawai.enqueue(new Callback<ResponLogin>() {
                    @Override
                    public void onResponse(Call<ResponLogin> call, Response<ResponLogin> response) {
                        pd.dismiss();
                        Log.d(TAG, "Response : " + response.toString());
                        ResponLogin res = response.body();
                        List<DataLogin> user = res.getData();
                        if (res.getMessage().equals("USERNAME TIDAK TERDAFTAR!")) {
                            Toast.makeText(Login.this, "Username Belum Terdaftar!", Toast.LENGTH_SHORT).show();
                        } else if (res.getMessage().equals("PASSWORD ANDA SALAH!")) {
                            Toast.makeText(Login.this, "Password Anda Salah!", Toast.LENGTH_SHORT).show();
                        } else if (res.getMessage().equals("SUKSES, LOGIN PEGAWAI!")) {

                            sm.storeLogin(user.get(0).getUsername(),user.get(0).getNama_pegawai());

                            Toast.makeText(Login.this, "SELAMAT DATANG", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, MenuAdmin.class);
                            intent.putExtra("id_pegawai", user.get(0).getId_pegawai());
                            intent.putExtra("nama_pegawai", user.get(0).getNama_pegawai());
                            intent.putExtra("role_pegawai", user.get(0).getRole_pegawai());
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "Username / Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponLogin> call, Throwable t) {
                        pd.dismiss();
                        Log.e(TAG, "Error : " + t.getMessage());
                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

