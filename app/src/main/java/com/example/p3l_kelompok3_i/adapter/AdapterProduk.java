package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.List;

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.HolderData> {

    private List<DataProduk> mList;
    private Context ctx;

    public AdapterProduk(Context ctx, List<DataProduk> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataProduk dp = mList.get(position);
        holder.namaProduk.setText(dp.getNama_produk());
        holder.hargaProduk.setText(dp.getHarga_produk());
        holder.stokProduk.setText(dp.getStok_produk());
        holder.stokMinimalProduk.setText(dp.getStok_minimal_produk());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView namaProduk, hargaProduk, stokProduk,stokMinimalProduk;

        public HolderData(View v)
        {
            super(v);

            namaProduk =(TextView) v.findViewById(R.id.tvNamaProduk);
            hargaProduk =(TextView) v.findViewById(R.id.tvHargaProduk);
            stokProduk =(TextView) v.findViewById(R.id.tvStokProduk);
            stokMinimalProduk =(TextView) v.findViewById(R.id.tvStokMinimal);

        }

    }


}
