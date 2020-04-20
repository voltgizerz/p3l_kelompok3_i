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

import com.example.p3l_kelompok3_i.KelolaSupplier;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_supplier.DataSupplier;

import java.util.ArrayList;
import java.util.List;

public class AdapterSupplierLog extends RecyclerView.Adapter<AdapterSupplierLog.HolderData> implements Filterable {

    private List<DataSupplier> mList;
    private List<DataSupplier> mListFull;
    private List<DataSupplier> saringDataDelete = new ArrayList<>();
    private Context ctx;

    public AdapterSupplierLog(Context ctx, List<DataSupplier> mList) {
        this.ctx = ctx;
        this.mList = mList;
        List<DataSupplier> a = mList;
        for(DataSupplier data : a){
            if(data.getCreated_date().equals("0000-00-00 00:00:00") ){
                saringDataDelete.add(data);
            }
        }
        mListFull = new ArrayList<>(saringDataDelete);
    }

    @NonNull
    @Override
    public AdapterSupplierLog.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutsupplierlog,parent,false);
        AdapterSupplierLog.HolderData holder = new AdapterSupplierLog.HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSupplierLog.HolderData holder, int position) {
        DataSupplier ds = saringDataDelete.get(position);
        holder.namaSupplier.setText(ds.getNama_supplier());
        holder.alamatSupplier.setText(ds.getAlamat_supplier());
        holder.noHpSupllier.setText(String.valueOf(ds.getNomor_telepon_supplier()));
        holder.idSupplier.setText(String.valueOf(ds.getId_supplier()));
        holder.deletedDate.setText(String.valueOf(ds.getDeleted_date()));
        holder.ds = ds;
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
            List<DataSupplier> filteredListSupplier = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListSupplier.addAll(mListFull);
            } else {
                String filterPatternSupplier = constraint.toString().toLowerCase().trim();
                for (DataSupplier data : mListFull) {
                    if (data.getNama_supplier().toLowerCase().contains(filterPatternSupplier)) {
                        filteredListSupplier.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListSupplier;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            saringDataDelete.clear();
            saringDataDelete.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder {
        DataSupplier ds;
        TextView namaSupplier,alamatSupplier, noHpSupllier,idSupplier,deletedDate;

        public HolderData(View v) {
            super(v);
            namaSupplier = (TextView) v.findViewById(R.id.tvNamaSupplier);
            alamatSupplier = (TextView) v.findViewById(R.id.tvAlamatSupplier);
            noHpSupllier = (TextView) v.findViewById(R.id.tvNoTelpSupplier);
            idSupplier = (TextView) v.findViewById(R.id.tvIdSupplier);
            deletedDate  = (TextView) v.findViewById(R.id.tvDeleteDateSupplier);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaSupplier.class);
                    goInput.putExtra("nama_supplier", ds.getNama_supplier());
                    goInput.putExtra("alamat_supplier", ds.getAlamat_supplier());
                    goInput.putExtra("nomor_telepon_supplier", ds.getNomor_telepon_supplier());
                    goInput.putExtra("id_supplier_delete", ds.getId_supplier());
                    ctx.startActivity(goInput);

                }
            });
        }
    }

}


