package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l_kelompok3_i.KelolaDetailPengadaan;
import com.example.p3l_kelompok3_i.KelolaPengadaan;
import com.example.p3l_kelompok3_i.R;

import com.example.p3l_kelompok3_i.pengadaan_detail.DataPengadaanDetail;

import java.util.ArrayList;
import java.util.List;

public class AdapterPengadaanDetail extends RecyclerView.Adapter<AdapterPengadaanDetail.HolderData> implements Filterable {

    private List<DataPengadaanDetail> mList;
    private List<DataPengadaanDetail> mListFull;
    private Context ctx;

    public AdapterPengadaanDetail(Context ctx, List<DataPengadaanDetail> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPengadaanDetail.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutdetailpengadaan,parent,false);
        AdapterPengadaanDetail.HolderData holder = new AdapterPengadaanDetail.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPengadaanDetail.HolderData holder, int position) {
        DataPengadaanDetail dp = mList.get(position);
        if(dp.getKode_pengadaan_fk().equals("PO-2020-02-02-01")) {
            holder.namaProduk.setText(dp.getNama_produk());
            holder.satuanProduk.setText(dp.getSatuan_pengadaan());
            holder.jumlahPengadaan.setText(String.valueOf(dp.getJumlah_pengadaan()));
        }else{return;}
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
            List<DataPengadaanDetail> filteredListPengadaan = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListPengadaan.addAll(mListFull);
            }else{
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for(DataPengadaanDetail data : mListFull){
                    if(data.getKode_pengadaan_fk().toLowerCase().contains(filterPatternPengadaan )) {
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
        TextView  namaProduk,satuanProduk,jumlahPengadaan;

        DataPengadaanDetail dp;

        public HolderData(View v)
        {
            super(v);
            namaProduk =(TextView) v.findViewById(R.id.tvNamaProdukDetailPengadaan);
            satuanProduk =(TextView) v.findViewById(R.id.tvSatuanPengadaanDetail);
            jumlahPengadaan =(TextView) v.findViewById(R.id.tvJumlahPengadaanDetail);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaDetailPengadaan.class);
                    goInput.putExtra("kode_pengadaan_fk", dp.getKode_pengadaan_fk());
                    goInput.putExtra("id_produk_fk", dp.getId_produk_fk());
                    goInput.putExtra("satuan_pengadaan", dp.getSatuan_pengadaan());
                    goInput.putExtra("jumlah_pengadaan", dp.getJumlah_pengadaan());
                    goInput.putExtra("nama_produk", dp.getNama_produk());
                    ctx.startActivity(goInput);

                }
            });
        }
    }

}

