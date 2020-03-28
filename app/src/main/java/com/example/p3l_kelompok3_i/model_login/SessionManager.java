package com.example.p3l_kelompok3_i.model_login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.p3l_kelompok3_i.Login;
import com.example.p3l_kelompok3_i.MenuAdmin;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_NAMA = "nama";
    private static final String is_login ="loggiatau";
    private final String SHARE_NAME = "loginsession";
    private final int MODE_PRIVATE = 0;
    private Context _context;

    public SessionManager(Context context)
    {
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME,MODE_PRIVATE);
        editor = sp.edit();
    }

    public void storeLogin(String user, String nama)
    {
        editor.putBoolean(is_login,true);
        editor.putString(KEY_USERNAME,user);
        editor.putString(KEY_NAMA,nama);
        editor.commit();
    }

    public HashMap getDetailLogin(){
        HashMap<String, String> map = new HashMap<>();
        map.put(KEY_NAMA, sp.getString(KEY_NAMA,null));
        map.put(KEY_USERNAME, sp.getString(KEY_USERNAME,null));
        return map;
    }

    public void checkLogin(){
        if(!this.Loggin()){
            Intent login = new Intent(_context, Login.class);
            login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(login);
        }
    }


    public Boolean Loggin(){
        return sp.getBoolean(is_login , false);
    }
}
