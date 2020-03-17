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

import com.example.p3l_kelompok3_i.model_pegawai.DataPegawai;

import java.util.ArrayList;
import java.util.List;

public class AdapterPegawai extends RecyclerView.Adapter<AdapterPegawai.HolderData> implements Filterable {

    private List<DataPegawai> mList;
    private List<DataPegawai> mListFull;
    private Context ctx;

    public AdapterPegawai(Context ctx, List<DataPegawai> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPegawai.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpegawai,parent,false);
        AdapterPegawai.HolderData holder = new AdapterPegawai.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPegawai.HolderData holder, int position) {
        DataPegawai dp = mList.get(position);
        holder.namaPegawai.setText(dp.getNama_pegawai());
        holder.usernamePegawai.setText(dp.getUsername());
        holder.alamatPegawai.setText(String.valueOf(dp.getAlamat_pegawai()));
        holder.tanggalLahirPegawai.setText(String.valueOf(dp.getTanggal_lahir_pegawai()));
        holder.nomorHpPegawai.setText(String.valueOf(dp.getNomor_hp_pegawai()));
        holder.rolePegawai.setText(String.valueOf(dp.getRole_pegawai()));
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
            List<DataPegawai> filteredListPegawai = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListPegawai.addAll(mListFull);
            }else{
                String filterPatternPegawai = constraint.toString().toLowerCase().trim();
                for(DataPegawai data : mListFull){
                    if(data.getNama_pegawai().toLowerCase().contains(filterPatternPegawai)){
                        filteredListPegawai.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListPegawai;

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
        TextView namaPegawai,alamatPegawai,tanggalLahirPegawai,usernamePegawai,nomorHpPegawai,rolePegawai;

        public HolderData(View v)
        {
            super(v);
            namaPegawai =(TextView) v.findViewById(R.id.tvNamaPegawai);
            alamatPegawai =(TextView) v.findViewById(R.id.tvAlamatPegawai);
            tanggalLahirPegawai =(TextView) v.findViewById(R.id.tvTanggalLahirPegawai);
            nomorHpPegawai =(TextView) v.findViewById(R.id.tvNoTelpPegawai);
            usernamePegawai = (TextView) v.findViewById(R.id.tvUsernamePegawai);
            rolePegawai = (TextView) v.findViewById(R.id.tvRolePegawai);
        }
    }


}