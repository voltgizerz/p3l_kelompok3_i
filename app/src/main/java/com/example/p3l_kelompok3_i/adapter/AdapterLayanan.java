package com.example.p3l_kelompok3_i.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;

import java.util.ArrayList;
import java.util.List;

public class AdapterLayanan extends RecyclerView.Adapter<AdapterLayanan.HolderData> implements Filterable {

    private List<DataLayanan> mList;
    private List<DataLayanan> mListFull;
    private Context ctx;

    public AdapterLayanan(Context ctx, List<DataLayanan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterLayanan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlayanan,parent,false);
        AdapterLayanan.HolderData holder = new AdapterLayanan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLayanan.HolderData holder, int position) {
        DataLayanan dl = mList.get(position);
        holder.namaLayanan.setText(dl.getNama_jasa_layanan());
        holder.hargaLayanan.setText(String.valueOf(dl.getHarga_jasa_layanan()));
        holder.ukuranHewan.setText(String.valueOf(dl.getId_ukuran_hewan()));
        holder.jenisHewan.setText(String.valueOf(dl.getId_jenis_hewan()));
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
            List<DataLayanan> filteredListLayanan = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListLayanan.addAll(mListFull);
            }else{
                String filterPatternLayanan = constraint.toString().toLowerCase().trim();
                for(DataLayanan data : mListFull){
                    if(data.getNama_jasa_layanan().toLowerCase().contains(filterPatternLayanan)){
                        filteredListLayanan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListLayanan;

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
        TextView namaLayanan,hargaLayanan,jenisHewan,ukuranHewan;

        public HolderData(View v)
        {
            super(v);
            namaLayanan =(TextView) v.findViewById(R.id.tvNamaLayanan);
            hargaLayanan =(TextView) v.findViewById(R.id.tvHargaLayanan);
            jenisHewan =(TextView) v.findViewById(R.id.tvJenisHewanLayanan);
            ukuranHewan =(TextView) v.findViewById(R.id.tvUkuranLayanan);
        }
    }
}
