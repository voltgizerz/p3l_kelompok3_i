package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.p3l_kelompok3_i.KelolaDetailPenjualanLayanan;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.penjualan_layanan_detail.DataPenjualanLayananDetail;

import java.util.ArrayList;
import java.util.List;

public class AdapterPenjualanLayananDetail extends RecyclerView.Adapter<AdapterPenjualanLayananDetail.HolderData> implements Filterable {

    private List<DataPenjualanLayananDetail> mList;
    private List<DataPenjualanLayananDetail> mListFull;
    private List<DataPenjualanLayananDetail> saringList = new ArrayList<>();
    private static SharedPreferences prefs;
    private Context ctx;

    public AdapterPenjualanLayananDetail(Context ctx, List<DataPenjualanLayananDetail> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);

        prefs = ctx.getSharedPreferences("KodePenjualanLayanan", 0);
        String cookieName = prefs.getString("kode_penjualan_layanan", null);
        List<DataPenjualanLayananDetail> a = mList;
        for(DataPenjualanLayananDetail data : a){
            if(data.getKode_transaksi_penjualan_jasa_layanan_fk().startsWith(cookieName) ){
                saringList.add(data);
            }
        }
    }

    @NonNull
    @Override
    public AdapterPenjualanLayananDetail.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutdetailpenjualanlayanan, parent, false);
        AdapterPenjualanLayananDetail.HolderData holder = new AdapterPenjualanLayananDetail.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualanLayananDetail.HolderData holder, int position) {
        DataPenjualanLayananDetail dp = saringList.get(position);
        holder.namaLayanan.setText(dp.getNama_jasa_layanan()+" "+dp.getNama_jenis_hewan()+" "+dp.getUkuran_hewan());
        holder.subtotal.setText(String.valueOf(dp.getSubtotal()));
        holder.dp = dp;
    }

    @Override
    public int getItemCount() {
        return saringList.size();
    }

    @Override
    public Filter getFilter() {
        return mListFilter;
    }

    private Filter mListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataPenjualanLayananDetail> filteredListPenjualan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPenjualan.addAll(mListFull);
            } else {
                String filterPatternPenjualan = constraint.toString().toLowerCase().trim();
                for (DataPenjualanLayananDetail data : mListFull) {
                    if (data.getKode_transaksi_penjualan_jasa_layanan_fk().toLowerCase().contains(filterPatternPenjualan) ) {
                        filteredListPenjualan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListPenjualan;
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
        TextView namaLayanan,subtotal;
        DataPenjualanLayananDetail dp;

        public HolderData(final View v) {
            super(v);
            namaLayanan = v.findViewById(R.id.tvJasaLayanandetailPenjualanLayanan);
            subtotal = v.findViewById(R.id.tvSubtotaldetailPenjualanLayanan);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String statusPenjualanLayanan = ctx.getSharedPreferences("StatusPenjualanLayanan", 0).getString("status_penjualan_layanan", null);
                    if(statusPenjualanLayanan.equals("Sudah Selesai")){
                        return;
                    }else {
                        Intent goInput = new Intent(ctx, KelolaDetailPenjualanLayanan.class);
                        goInput.putExtra("id_detail_transaksi_penjualan_layanan", dp.getId_detail_penjualan_jasa_layanan());
                        goInput.putExtra("id_jasa_layanan_fk", dp.getId_jasa_layanan_fk());
                        goInput.putExtra("jumlah_jasa_layanan", dp.getJumlah_jasa_layanan());
                        ctx.startActivity(goInput);
                    }

                }
            });
        }
    }


}
