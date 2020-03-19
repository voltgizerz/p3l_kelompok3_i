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

import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_jenis_hewan.DataJenisHewan;

import java.util.ArrayList;
import java.util.List;

public class AdapterJenisHewan extends RecyclerView.Adapter<AdapterJenisHewan.HolderData> implements Filterable {

    private List<DataJenisHewan> mList;
    private List<DataJenisHewan> mListFull;
    private Context ctx;

    public AdapterJenisHewan(Context ctx, List<DataJenisHewan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterJenisHewan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutjenishewan,parent,false);
        AdapterJenisHewan.HolderData holder = new AdapterJenisHewan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJenisHewan.HolderData holder, int position) {
        DataJenisHewan dj = mList.get(position);
        holder.namaJenisHewan.setText(dj.getNama_jenis_hewan());
        holder.dj = dj;
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
            List<DataJenisHewan> filteredListJenisHewan = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListJenisHewan.addAll(mListFull);
            }else{
                String filterPatternJenisHewan = constraint.toString().toLowerCase().trim();
                for(DataJenisHewan data : mListFull){
                    if(data.getNama_jenis_hewan().toLowerCase().contains(filterPatternJenisHewan)){
                        filteredListJenisHewan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListJenisHewan;

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
        TextView namaJenisHewan;
        DataJenisHewan dj;

        public HolderData(View v)
        {
            super(v);
            namaJenisHewan =(TextView) v.findViewById(R.id.tvNamaJenisHewan);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goInput = new Intent(ctx.KelolaJenisHewan.class);
                    goInput.putExtra("nama_jenis_hewan", dj.getNama_jenis_hewan());
                    goInput.putExtra("id_jenis_hewan",dj.getId_jenis_hewan());

                    ctx.startActivity(goInput);
                }
            });
        }
    }
}