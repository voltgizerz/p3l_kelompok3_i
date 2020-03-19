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
    private Context ctx;

    public AdapterUkuranHewan(Context ctx, List<DataUkuranHewan> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
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
        DataUkuranHewan du = mList.get(position);
        holder.ukuranHewan.setText(du.getUkuran_hewan());

        holder.du = du;
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
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder{
        TextView ukuranHewan;
        DataUkuranHewan du;

        public HolderData(View v)
        {
            super(v);
            ukuranHewan =(TextView) v.findViewById(R.id.tvUkuranHewan);

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