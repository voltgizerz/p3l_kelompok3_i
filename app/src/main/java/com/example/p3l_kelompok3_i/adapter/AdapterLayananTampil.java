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

import com.example.p3l_kelompok3_i.KelolaLayanan;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_jasa_layanan.DataLayanan;

import java.util.ArrayList;
import java.util.List;

public class AdapterLayananTampil extends RecyclerView.Adapter<AdapterLayananTampil.HolderData> implements Filterable {

    private List<DataLayanan> mList;
    private List<DataLayanan> mListFull;
    private Context ctx;

    public AdapterLayananTampil(Context ctx, List<DataLayanan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterLayananTampil.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlayanantampil,parent,false);
        AdapterLayananTampil.HolderData holder = new AdapterLayananTampil.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLayananTampil.HolderData holder, int position) {
        DataLayanan dl = mList.get(position);
        holder.idLayanan.setText(String.valueOf(dl.getId_jasa_layanan()));
        holder.namaLayanan.setText(dl.getNama_jasa_layanan() + " " + dl.getNama_jenis_hewan() + " " + dl.getUkuran_hewan());
        holder.hargaLayanan.setText(String.valueOf(dl.getHarga_jasa_layanan()));

        holder.dl = dl;

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
                    if(data.getNama_jasa_layanan().toLowerCase().contains(filterPatternLayanan) || data.getNama_jenis_hewan().toLowerCase().contains(filterPatternLayanan) || data.getUkuran_hewan().toLowerCase().contains(filterPatternLayanan) ){
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
        TextView namaLayanan,hargaLayanan,idjenisHewan,idukuranHewan,idLayanan;
        DataLayanan dl;

        public HolderData(View v)
        {
            super(v);
            namaLayanan =(TextView) v.findViewById(R.id.tvNamaLayanan);
            hargaLayanan =(TextView) v.findViewById(R.id.tvHargaLayanan);
            idjenisHewan =(TextView) v.findViewById(R.id.tvIdJenisHewan);
            idukuranHewan =(TextView) v.findViewById(R.id.tvIdUkuranHewan);
            idLayanan =(TextView) v.findViewById(R.id.tvIdLayanan);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaLayanan.class);
                    goInput.putExtra("id_jasa_layanan", dl.getId_jasa_layanan());
                    goInput.putExtra("nama_jasa_layanan", dl.getNama_jasa_layanan());
                    goInput.putExtra("harga_jasa_layanan", dl.getHarga_jasa_layanan());
                    goInput.putExtra("id_jenis_hewan", dl.getId_jenis_hewan());
                    goInput.putExtra("id_ukuran_hewan", dl.getId_ukuran_hewan());
                    ctx.startActivity(goInput);
                }
            });

        }
    }
}
