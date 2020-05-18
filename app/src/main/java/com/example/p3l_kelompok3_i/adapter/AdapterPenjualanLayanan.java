package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l_kelompok3_i.KelolaPenjualanLayanan;

import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_penjualan_layanan.DataPenjualanLayanan;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AdapterPenjualanLayanan extends RecyclerView.Adapter<AdapterPenjualanLayanan.HolderData> implements Filterable {

    private List<DataPenjualanLayanan> mList;
    private List<DataPenjualanLayanan> mListFull;
    private Context ctx;

    public AdapterPenjualanLayanan(Context ctx, List<DataPenjualanLayanan> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPenjualanLayanan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpenjualanlayanan, parent, false);
        AdapterPenjualanLayanan.HolderData holder = new AdapterPenjualanLayanan.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualanLayanan.HolderData holder, int position) {
        DataPenjualanLayanan dp = mList.get(position);
        Integer panjangTotal = "Belum Selesai".length();
        Integer panjang = String.valueOf(dp.getStatus_penjualan()).length();
        String text = String.valueOf(dp.getStatus_penjualan());
        String textTotal = "Belum Selesai";
        SpannableString mSpan = new SpannableString(text);
        SpannableString mSpanGreen = new SpannableString(text);

        ForegroundColorSpan mRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan mGreen = new ForegroundColorSpan(Color.GREEN);
        mSpan.setSpan(mRed, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpanGreen.setSpan(mGreen, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (String.valueOf(dp.getStatus_penjualan()).equals("Belum Selesai")) {
            holder.status.setText(mSpan);
        } else {
            holder.status.setText(mSpanGreen);
        }
        holder.namahewan.setText(dp.getNama_hewan());
        holder.namacs.setText(dp.getNama_cs());
        holder.kodeTransaksi.setText(String.valueOf(dp.getKode_transaksi_penjualan_jasa_layanan()));
        if (dp.getTotal_penjualan_jasa_layanan() == 0) {
            holder.subTotal.setText(String.valueOf("Belum Ada Jasa Layanan"));
        } else {
            holder.subTotal.setText(String.valueOf("Rp.  " + dp.getTotal_penjualan_jasa_layanan()));
        }
        holder.createdDate.setText(String.valueOf(dp.getCreated_date()));
        if (dp.getUpdated_date().equals("0000-00-00 00:00:00")) {
            holder.updatedDate.setText(String.valueOf("-"));
        } else {
            holder.updatedDate.setText(String.valueOf(dp.getUpdated_date()));
        }
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
            List<DataPenjualanLayanan> filteredListPengadaan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPengadaan.addAll(mListFull);
            } else {
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for (DataPenjualanLayanan data : mListFull) {
                    if (data.getKode_transaksi_penjualan_jasa_layanan().toLowerCase().contains(filterPatternPengadaan) || String.valueOf(data.getId_cs()).toLowerCase().contains(filterPatternPengadaan) || data.getNama_cs().toLowerCase().contains(filterPatternPengadaan) || data.getStatus_penjualan().toLowerCase().contains(filterPatternPengadaan) || data.getNama_hewan().toLowerCase().contains(filterPatternPengadaan)) {
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
        TextView kodeTransaksi,namahewan, subTotal, totalHarga, diskon, status, tanggalPembayaran, createdDate, updatedDate, namacs;
        DataPenjualanLayanan dp;

        public HolderData(final View v) {
            super(v);
            kodeTransaksi = (TextView) v.findViewById(R.id.tvKodePenjualanLayanan);
            namacs = (TextView) v.findViewById(R.id.tvNamaCS);
            namahewan = (TextView) v.findViewById(R.id.tvNamaHewanPenjualan);
            status = (TextView) v.findViewById(R.id.tvStatusPenjualanLayanan);
            subTotal = (TextView) v.findViewById(R.id.tvSubTotalPenjualanLayanan);
            createdDate = (TextView) v.findViewById(R.id.tvCreateDatePenjualanLayanan);
            updatedDate = (TextView) v.findViewById(R.id.tvUpdatedDatePenjualanLayanan);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent goInput = new Intent(ctx, KelolaPenjualanLayanan.class);
                    //simpan kode penjualan di sp
                    SharedPreferences prefs = ctx.getSharedPreferences("KodePenjualanLayanan", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("kode_penjualan_layanan", dp.getKode_transaksi_penjualan_jasa_layanan());
                    editor.apply();

                    SharedPreferences sp_status = ctx.getSharedPreferences("StatusPenjualanLayanan", MODE_PRIVATE);
                    SharedPreferences.Editor sp_status2 = sp_status.edit();
                    sp_status2.putString("status_penjualan_layanan", dp.getStatus_penjualan());
                    sp_status2.apply();

                    SharedPreferences sp_id = ctx.getSharedPreferences("IdPenjualanLayanan", MODE_PRIVATE);
                    SharedPreferences.Editor sp_id2 = sp_id.edit();
                    sp_id2.putString("id_transaksi_penjualan_layanan", dp.getId_transaksi_penjualan_jasa_layanan());
                    sp_id2.apply();

                    goInput.putExtra("id_transaksi_penjualan_layanan", dp.getId_transaksi_penjualan_jasa_layanan());
                    goInput.putExtra("kode_transaksi_penjualan_layanan", dp.getKode_transaksi_penjualan_jasa_layanan());
                    goInput.putExtra("id_hewan_penjualan_layanan", dp.getId_hewan());
                    goInput.putExtra("nama_hewan_penjualan_layanan", dp.getNama_hewan());
                    goInput.putExtra("total_penjualan", dp.getTotal_penjualan_jasa_layanan());
                    goInput.putExtra("status_penjualan", dp.getStatus_penjualan());
                    goInput.putExtra("tanggal_penjualan", dp.getTanggal_penjualan_jasa_layanan());

                    ctx.startActivity(goInput);

                }
            });
        }
    }


}
