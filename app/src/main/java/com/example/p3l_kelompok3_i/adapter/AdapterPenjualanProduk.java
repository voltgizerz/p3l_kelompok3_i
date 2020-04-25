package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l_kelompok3_i.KelolaPengadaan;
import com.example.p3l_kelompok3_i.KelolaPenjualanProduk;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_penjualan_produk.DataPenjualanProduk;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AdapterPenjualanProduk extends RecyclerView.Adapter<AdapterPenjualanProduk.HolderData> implements Filterable {

    private List<DataPenjualanProduk> mList;
    private List<DataPenjualanProduk> mListFull;
    private Context ctx;

    public AdapterPenjualanProduk(Context ctx, List<DataPenjualanProduk> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPenjualanProduk.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpenjualanproduk, parent, false);
        AdapterPenjualanProduk.HolderData holder = new AdapterPenjualanProduk.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualanProduk.HolderData holder, int position) {
        DataPenjualanProduk dp = mList.get(position);
        holder.status.setText(dp.getStatus_penjualan());
        holder.namacs.setText(dp.getNama_cs());
        holder.kodeTransaksi.setText(String.valueOf(dp.getKode_transaksi_penjualan_produk()));
        if(dp.getTotal_penjualan_produk() == 0){
            holder.subTotal.setText(String.valueOf("Belum Ada Produk"));
        }else{
            holder.subTotal.setText(String.valueOf("Rp.  "+dp.getTotal_penjualan_produk()));
        }
        holder.createdDate.setText(String.valueOf(dp.getCreated_date()));
        holder.updatedDate.setText(String.valueOf(dp.getUpdated_date()));
        holder.dp = dp;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return mListFilter;
    }

    private Filter mListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataPenjualanProduk> filteredListPengadaan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPengadaan.addAll(mListFull);
            } else {
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for (DataPenjualanProduk data : mListFull) {
                    if (data.getKode_transaksi_penjualan_produk().toLowerCase().contains(filterPatternPengadaan) || String.valueOf(data.getId_cs()).toLowerCase().contains(filterPatternPengadaan) ||data.getNama_cs().toLowerCase().contains(filterPatternPengadaan)  || data.getStatus_penjualan().toLowerCase().contains(filterPatternPengadaan) ) {
                        filteredListPengadaan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListPengadaan;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder {
        TextView  kodeTransaksi, subTotal, totalHarga, diskon, status, tanggalPembayaran,createdDate,updatedDate,namaHewan,namacs;
        DataPenjualanProduk dp;

        public HolderData(final View v) {
            super(v);
            kodeTransaksi = (TextView) v.findViewById(R.id.tvKodePenjualanProduk);
            namacs = (TextView) v.findViewById(R.id.tvNamaCS);
            status = (TextView) v.findViewById(R.id.tvStatusPenjualanProduk);
            subTotal = (TextView) v.findViewById(R.id.tvSubTotalPenjualanProduk);
            createdDate = (TextView) v.findViewById(R.id.tvCreateDatePenjualanProduk);
            updatedDate = (TextView) v.findViewById(R.id.tvUpdatedDatePenjualanProduk);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent goInput = new Intent(ctx, KelolaPenjualanProduk.class);
                    //simpan kode penjualan di sp
                    SharedPreferences prefs = ctx.getSharedPreferences("KodePenjualanProduk", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("kode_penjualan_produk", dp.getKode_transaksi_penjualan_produk());
                    editor.apply();

                    goInput.putExtra("id_transaksi_penjualan_produk", dp.getId_transaksi_penjualan_produk());
                    goInput.putExtra("kode_transaksi_penjualan_produk", dp.getKode_transaksi_penjualan_produk());
                    goInput.putExtra("total_penjualan", dp.getTotal_penjualan_produk());
                    goInput.putExtra("status_penjualan", dp.getStatus_penjualan());
                    goInput.putExtra("tanggal_penjualan", dp.getTanggal_penjualan_produk());

                    ctx.startActivity(goInput);

                }
            });
        }
    }



}
