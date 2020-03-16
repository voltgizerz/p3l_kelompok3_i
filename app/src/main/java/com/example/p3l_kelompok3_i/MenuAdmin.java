package com.example.p3l_kelompok3_i;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuAdmin extends AppCompatActivity {

    private Button  btnKelolaCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        btnKelolaCustomer = findViewById(R.id.btnCustomer);

        btnKelolaCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuAdmin.this, KelolaCustomer.class);
                startActivity(i);
            }
        });
    }


}
