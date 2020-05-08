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

import com.example.p3l_kelompok3_i.KelolaUkuranHewan;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_ukuran_hewan.DataUkuranHewan;

import java.util.ArrayList;
import java.util.List;

public class AdapterUkuranHewan extends RecyclerView.Adapter<AdapterUkuranHewan.HolderData> implements Filterable {

    private List<DataUkuranHewan> mList;
    private List<DataUkuranHewan> mListFull;
    private List<DataUkuranHewan> saringDataDelete = new ArrayList<>();
    private Context ctx;

    public AdapterUkuranHewan(Context ctx, List<DataUkuranHewan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        List<DataUkuranHewan> a = mList;
        for(DataUkuranHewan data : a){
            if(!data.getCreated_date().equals("0000-00-00 00:00:00") ){
                saringDataDelete.add(data);
            }
        }
        mListFull = new ArrayList<>(saringDataDelete);
    }

    @NonNull
    @Override
    public AdapterUkuranHewan.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutukuranhewan,parent,false);
        AdapterUkuranHewan.HolderData holder = new AdapterUkuranHewan.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUkuranHewan.HolderData holder, int position) {
        DataUkuranHewan du = saringDataDelete.get(position);
        holder.ukuranHewan.setText(du.getUkuran_hewan());
        holder.idUkuranHewan.setText(String.valueOf(du.getId_ukuran_hewan()));
        holder.createdDate.setText(String.valueOf(du.getCreated_date()));
        if (du.getUpdated_date().equals("0000-00-00 00:00:00")) {
            holder.updatedDate.setText(String.valueOf("-"));
        } else {
            holder.updatedDate.setText(String.valueOf(du.getUpdated_date()));
        }
        holder.du = du;
    }

    @Override
    public int getItemCount() {
        return saringDataDelete.size();
    }

    @Override
    public Filter getFilter() {
        return mListFilter;
    }

    private Filter mListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<DataUkuranHewan> filteredListUkuranHewan = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListUkuranHewan.addAll(mListFull);
            }else{
                String filterPatterntUkuranHewan = constraint.toString().toLowerCase().trim();
                for(DataUkuranHewan data : mListFull){
                    if(data.getUkuran_hewan().toLowerCase().contains(filterPatterntUkuranHewan)){
                        filteredListUkuranHewan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListUkuranHewan;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            saringDataDelete.clear();
            saringDataDelete.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder{
        TextView ukuranHewan, idUkuranHewan,createdDate,updatedDate;
        DataUkuranHewan du;

        public HolderData(View v)
        {
            super(v);
            ukuranHewan =(TextView) v.findViewById(R.id.tvUkuranHewan);
            idUkuranHewan = (TextView) v.findViewById(R.id.tvIdUkuranHewanTampil);
            createdDate  = (TextView) v.findViewById(R.id.tvCreateDateUkuranHewan);
            updatedDate  = (TextView) v.findViewById(R.id.tvUpdatedDateUkuranHewan);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goIpnput = new Intent(ctx, KelolaUkuranHewan.class);
                    goIpnput.putExtra("ukuran_hewan", du.getUkuran_hewan());
                    goIpnput.putExtra("id_ukuran_hewan", du.getId_ukuran_hewan());

                    ctx.startActivity(goIpnput);
                }
            });
        }
    }
}