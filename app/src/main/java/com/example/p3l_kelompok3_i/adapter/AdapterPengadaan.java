package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Parcelable;
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


import com.example.p3l_kelompok3_i.KelolaPengadaan;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_pengadaan.DataPengadaan;
import com.example.p3l_kelompok3_i.model_pengadaan.ProdukDibeli;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class AdapterPengadaan extends RecyclerView.Adapter<AdapterPengadaan.HolderData> implements Filterable {

    private List<DataPengadaan> mList;
    private List<DataPengadaan> mListFull;
    private Context ctx;

    public AdapterPengadaan(Context ctx, List<DataPengadaan> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPengadaan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpengadaan, parent, false);
        AdapterPengadaan.HolderData holder = new AdapterPengadaan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPengadaan.HolderData holder, int position) {
        DataPengadaan dp = mList.get(position);
        Integer panjangTotal = String.valueOf("Belum Ada Produk").length();
        Integer panjang = String.valueOf(dp.getStatus_pengadaan()).length();
        String text = String.valueOf(dp.getStatus_pengadaan());
        String textTotal = "Belum Ada Produk";
        SpannableString mSpan = new SpannableString(text);
        SpannableString mSpanOrange = new SpannableString(textTotal);
        SpannableString mSpanGreen = new SpannableString(text);
        ForegroundColorSpan mRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan mGreen = new ForegroundColorSpan(Color.GREEN);
        ForegroundColorSpan mOrange = new ForegroundColorSpan(Color.BLUE);

        mSpanOrange.setSpan(mOrange, 0, panjangTotal, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpan.setSpan(mRed, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpanGreen.setSpan(mGreen, 0, panjang, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (String.valueOf(dp.getTotal_pengadaan()).equals("0")) {
            holder.kodePengadaan.setText(dp.getKode_pengadaan());
            holder.idSupplierPengadaan.setText(dp.getNama_supplier());
            holder.createdDate.setText(dp.getCreated_date());
            holder.updatedDate.setText(dp.getUpdated_date());

            if (String.valueOf(dp.getStatus_pengadaan()).equals("Belum Diterima")) {
                holder.statusPengadaan.setText(mSpan);
            } else {
                holder.statusPengadaan.setText(mSpanGreen);
            }

            holder.tanggalPengadaan.setText(String.valueOf(dp.getTanggal_pengadaan()));
            holder.totalPengadaan.setText(mSpanOrange);
        } else {
            holder.kodePengadaan.setText(dp.getKode_pengadaan());
            holder.idSupplierPengadaan.setText(dp.getNama_supplier());
            holder.createdDate.setText(dp.getCreated_date());
            holder.updatedDate.setText(dp.getUpdated_date());
            if (String.valueOf(dp.getStatus_pengadaan()).equals("Belum Diterima")) {
                holder.statusPengadaan.setText(mSpan);
            } else {
                holder.statusPengadaan.setText(mSpanGreen);
            }
            holder.tanggalPengadaan.setText(String.valueOf(dp.getTanggal_pengadaan()));
            holder.totalPengadaan.setText("Rp. " + String.valueOf(dp.getTotal_pengadaan()));
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
            List<DataPengadaan> filteredListPengadaan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPengadaan.addAll(mListFull);
            } else {
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for (DataPengadaan data : mListFull) {
                    if (data.getKode_pengadaan().toLowerCase().contains(filterPatternPengadaan) || String.valueOf(data.getId_supplier()).toLowerCase().contains(filterPatternPengadaan)) {
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
        TextView kodePengadaan, idSupplierPengadaan, statusPengadaan, tanggalPengadaan, totalPengadaan,createdDate,updatedDate;
        DataPengadaan dp;

        public HolderData(final View v) {
            super(v);
            kodePengadaan = (TextView) v.findViewById(R.id.tvKodePengadaan);
            idSupplierPengadaan = (TextView) v.findViewById(R.id.tvNamaSupplierPengadaan);
            statusPengadaan = (TextView) v.findViewById(R.id.tvStatusPengadaan);
            tanggalPengadaan = (TextView) v.findViewById(R.id.tvTanggalPengadaan);
            totalPengadaan = (TextView) v.findViewById(R.id.tvTotalPengadaan);
            createdDate = (TextView) v.findViewById(R.id.tvCreateDatePengadaan);
            updatedDate = (TextView) v.findViewById(R.id.tvUpdatedDatePengadaan);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent goInput = new Intent(ctx, KelolaPengadaan.class);
                    //simpan kode pengadaan di sp
                    SharedPreferences prefs = ctx.getSharedPreferences("KodePengadaan", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("kode_pengadaan", dp.getKode_pengadaan());
                    editor.apply();

                    SharedPreferences sp_supplier = ctx.getSharedPreferences("SupplierPengadaan", MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sp_supplier.edit();
                    editor2.putInt("supplier_pengadaan", dp.getId_supplier());
                    editor2.apply();

                    SharedPreferences sp_status = ctx.getSharedPreferences("StatusPengadaan", MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sp_status.edit();
                    editor3.putString("status_pengadaan", dp.getStatus_pengadaan());
                    editor3.apply();

                    SharedPreferences sp_total = ctx.getSharedPreferences("TotalPengadaan", MODE_PRIVATE);
                    SharedPreferences.Editor editor4 = sp_total.edit();
                    editor4.putInt("total_pengadaan", dp.getTotal_pengadaan());
                    editor4.apply();

                    SharedPreferences sp_idp = ctx.getSharedPreferences("IdPengadaan", MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = sp_idp.edit();
                    editor5.putString("id_pengadaan", dp.getId_pengadaan());
                    editor5.apply();


                    goInput.putExtra("id_pengadaan", dp.getId_pengadaan());
                    goInput.putExtra("kode_pengadaan", dp.getKode_pengadaan());
                    goInput.putExtra("id_supplier", dp.getId_supplier());
                    goInput.putExtra("status_pengadaan", dp.getStatus_pengadaan());
                    goInput.putExtra("tanggal_pengadaan", dp.getTanggal_pengadaan());
                    goInput.putExtra("total_pengadaan", dp.getTotal_pengadaan());
                    goInput.putExtra("nama_supplier", dp.getNama_supplier());
                    ctx.startActivity(goInput);

                }
            });
        }
    }


}

