package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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


public class AdapterPengadaan extends RecyclerView.Adapter<AdapterPengadaan.HolderData> implements Filterable {

    private List<DataPengadaan> mList;
    private List<DataPengadaan> mListFull;
    private Context ctx;

    public AdapterPengadaan(Context ctx, List<DataPengadaan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPengadaan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpengadaan,parent,false);
        AdapterPengadaan.HolderData holder = new AdapterPengadaan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPengadaan.HolderData holder, int position) {
        DataPengadaan dp = mList.get(position);
        holder.kodePengadaan.setText(dp.getKode_pengadaan());
        holder.idSupplierPengadaan.setText(dp.getNama_supplier());
        holder.statusPengadaan.setText(String.valueOf(dp.getStatus_pengadaan()));
        holder.tanggalPengadaan.setText(String.valueOf(dp.getTanggal_pengadaan()));
        holder.totalPengadaan.setText(String.valueOf(dp.getTotal_pengadaan()));

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

            if(constraint == null || constraint.length()== 0){
                filteredListPengadaan.addAll(mListFull);
            }else{
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for(DataPengadaan data : mListFull){
                    if(data.getKode_pengadaan().toLowerCase().contains(filterPatternPengadaan ) || String.valueOf(data.getId_supplier()).toLowerCase().contains(filterPatternPengadaan) ){
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


    class HolderData extends RecyclerView.ViewHolder{
        TextView kodePengadaan, idSupplierPengadaan,statusPengadaan,tanggalPengadaan,totalPengadaan;
        DataPengadaan dp;

        public HolderData(View v)
        {
            super(v);
            kodePengadaan =(TextView) v.findViewById(R.id.tvKodePengadaan);
            idSupplierPengadaan =(TextView) v.findViewById(R.id.tvNamaSupplierPengadaan);
            statusPengadaan =(TextView) v.findViewById(R.id.tvStatusPengadaan);
            tanggalPengadaan =(TextView) v.findViewById(R.id.tvTanggalPengadaan);
            totalPengadaan = (TextView) v.findViewById(R.id.tvTotalPengadaan);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaPengadaan.class);
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

