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

public class AdapterLayananSudahSelesai extends RecyclerView.Adapter<AdapterLayananSudahSelesai.HolderData> implements Filterable {

    private List<DataPenjualanLayanan> mList;
    private List<DataPenjualanLayanan> mListFull;
    private Context ctx;

    public AdapterLayananSudahSelesai(Context ctx, List<DataPenjualanLayanan> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterLayananSudahSelesai.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutsudahselesai, parent, false);
        AdapterLayananSudahSelesai.HolderData holder = new AdapterLayananSudahSelesai.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterLayananSudahSelesai.HolderData holder, int position) {
        DataPenjualanLayanan dp = mList.get(position);
        Integer panjangTotal = "Belum Selesai".length();
        Integer panjang = String.valueOf(dp.getStatus_layanan()).length();
        String text = String.valueOf(dp.getStatus_layanan());
        String textTotal = "Belum Selesai";
        SpannableString mSpan = new SpannableString(text);
        SpannableString mSpanGreen = new SpannableString(text);

        ForegroundColorSpan mRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan mGreen = new ForegroundColorSpan(Color.GREEN);
        mSpan.setSpan(mRed, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpanGreen.setSpan(mGreen, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.namahewan.setText(dp.getNama_hewan());
        if (String.valueOf(dp.getStatus_layanan()).equals("Belum Selesai")) {
            holder.status.setText(mSpan);
        } else {
            holder.status.setText(mSpanGreen);
        }
        holder.kodeTransaksi.setText(String.valueOf(dp.getKode_transaksi_penjualan_jasa_layanan()));
        if (dp.getTotal_penjualan_jasa_layanan() == 0) {
            holder.subTotal.setText(String.valueOf("Jasa Layanan Kosong"));
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
        TextView kodeTransaksi,namahewan, subTotal, status,createdDate, updatedDate, namacs;
        DataPenjualanLayanan dp;

        public HolderData(final View v) {
            super(v);
            kodeTransaksi = (TextView) v.findViewById(R.id.tvKodeSudahSelesai);
            namahewan = (TextView) v.findViewById(R.id.tvNamaHewanSudahSelesai);
            status = (TextView) v.findViewById(R.id.tvStatusLayananSudahSelesai);
            subTotal = (TextView) v.findViewById(R.id.tvSubTotalPenjualanLayanan);
            createdDate = (TextView) v.findViewById(R.id.tvCreateDateSudahSelesai);
            updatedDate = (TextView) v.findViewById(R.id.tvUpdatedDateSudahSelesai);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
        }
    }


}
