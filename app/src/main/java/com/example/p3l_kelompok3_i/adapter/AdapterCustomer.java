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
import com.example.p3l_kelompok3_i.model_customer.DataCustomer;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.HolderData> implements Filterable {

    private List<DataCustomer> mList;
    private List<DataCustomer> mListFull;
    private Context ctx;

    public AdapterCustomer(Context ctx, List<DataCustomer> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterCustomer.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutcustomer,parent,false);
        AdapterCustomer.HolderData holder = new AdapterCustomer.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCustomer.HolderData holder, int position) {
        DataCustomer dc = mList.get(position);
        holder.namaCustomer.setText(dc.getNama_customer());
        holder.alamatCustomer.setText(String.valueOf(dc.getAlamat_customer()));
        holder.tanggalLahirCustomer.setText(String.valueOf(dc.getTanggal_lahir_customer()));
        holder.nomorHpCustomer.setText(String.valueOf(dc.getNomor_hp_customer()));
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
            List<DataCustomer> filteredListCustomer = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListCustomer.addAll(mListFull);
            }else{
                String filterPatternCustomer = constraint.toString().toLowerCase().trim();
                for(DataCustomer data : mListFull){
                    if(data.getNama_customer().toLowerCase().contains(filterPatternCustomer)){
                        filteredListCustomer.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListCustomer;

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
        TextView namaCustomer,alamatCustomer,tanggalLahirCustomer,nomorHpCustomer;

        public HolderData(View v)
        {
            super(v);
            namaCustomer =(TextView) v.findViewById(R.id.tvNamaCustomer);
            alamatCustomer =(TextView) v.findViewById(R.id.tvAlamatCustomer);
            tanggalLahirCustomer =(TextView) v.findViewById(R.id.tvTanggalLahirCustomer);
            nomorHpCustomer =(TextView) v.findViewById(R.id.tvNoTelpCustomer);
        }
    }
}