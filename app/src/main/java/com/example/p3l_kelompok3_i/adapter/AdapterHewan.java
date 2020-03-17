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
import com.example.p3l_kelompok3_i.model_hewan.DataHewan;

import java.util.ArrayList;
import java.util.List;

public class AdapterHewan  extends RecyclerView.Adapter<AdapterHewan.HolderData> implements Filterable {
    private List<DataHewan> mList;
    private List<DataHewan> mListFull;
    private Context ctx;

    public AdapterHewan(Context ctx, List<DataHewan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterHewan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layouthewan,parent,false);
        AdapterHewan.HolderData holder = new AdapterHewan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHewan.HolderData holder, int position) {
        DataHewan dh = mList.get(position);
        holder.namaHewan.setText(dh.getNama_hewan());
        holder.tanggalLahirHewan.setText(String.valueOf(dh.getTanggal_lahir_hewan()));
        holder.idJenisHewan.setText(String.valueOf(dh.getId_jenis_hewan()));
        holder.idUkuranHewan.setText(String.valueOf(dh.getId_ukuran_hewan()));
        holder.idCustomer.setText(String.valueOf(dh.getId_customer()));
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
            List<DataHewan> filteredListHewan = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListHewan.addAll(mListFull);
            }else{
                String filterPatternHewan = constraint.toString().toLowerCase().trim();
                for(DataHewan data : mListFull){
                    if(data.getNama_hewan().toLowerCase().contains(filterPatternHewan)){
                        filteredListHewan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListHewan;

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
        TextView namaHewan,tanggalLahirHewan,idJenisHewan,idUkuranHewan,idCustomer;

        public HolderData(View v)
        {
            super(v);
            namaHewan =(TextView) v.findViewById(R.id.tvNamaHewan);
            tanggalLahirHewan =(TextView) v.findViewById(R.id.tvTanggalLahirHewan);
            idJenisHewan =(TextView) v.findViewById(R.id.tvIdJenisHewan);
            idUkuranHewan =(TextView) v.findViewById(R.id.tvIdUkuranHewan);
            idCustomer =(TextView) v.findViewById(R.id.tvIdCustomer);
        }
    }
}
