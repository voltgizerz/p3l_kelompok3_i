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

import com.example.p3l_kelompok3_i.KelolaCustomer;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_customer.DataCustomer;

import java.util.ArrayList;
import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.HolderData> implements Filterable {

    private List<DataCustomer> mList;
    private List<DataCustomer> mListFull;
    private List<DataCustomer> saringDataDelete = new ArrayList<>();
    private Context ctx;

    public AdapterCustomer(Context ctx, List<DataCustomer> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        List<DataCustomer> a = mList;
        for(DataCustomer data : a){
            if(!data.getCreated_date().equals("0000-00-00 00:00:00") ){
                saringDataDelete.add(data);
            }
        }
        mListFull = new ArrayList<>(saringDataDelete);
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
        DataCustomer dc = saringDataDelete.get(position);
        holder.namaCustomer.setText(dc.getNama_customer());
        holder.alamatCustomer.setText(String.valueOf(dc.getAlamat_customer()));
        holder.tanggalLahirCustomer.setText(String.valueOf(dc.getTanggal_lahir_customer()));
        holder.nomorHpCustomer.setText(String.valueOf(dc.getNomor_hp_customer()));
        holder.idCustomer.setText(String.valueOf(dc.getId_customer()));
        holder.createdDate.setText(String.valueOf(dc.getCreated_date()));
        holder.updatedDate.setText(String.valueOf(dc.getUpdated_date()));
        holder.dc = dc;
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
            List<DataCustomer> filteredListCustomer = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListCustomer.addAll(mListFull);
            }else{
                String filterPatternCustomer = constraint.toString().toLowerCase().trim();
                for(DataCustomer data : mListFull){
                    if(data.getNama_customer().toLowerCase().contains(filterPatternCustomer) || data.getId_customer().toLowerCase().contains(filterPatternCustomer)){
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
            saringDataDelete.clear();
            saringDataDelete.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder{
        TextView namaCustomer,alamatCustomer,tanggalLahirCustomer,nomorHpCustomer,idCustomer,createdDate,updatedDate;
        DataCustomer dc;

        public HolderData(View v)
        {
            super(v);
            namaCustomer =(TextView) v.findViewById(R.id.tvNamaCustomer);
            alamatCustomer =(TextView) v.findViewById(R.id.tvAlamatCustomer);
            tanggalLahirCustomer =(TextView) v.findViewById(R.id.tvTanggalLahirCustomer);
            nomorHpCustomer =(TextView) v.findViewById(R.id.tvNoTelpCustomer);
            idCustomer = (TextView) v.findViewById(R.id.tvIdCustomerTampil);
            createdDate  = (TextView) v.findViewById(R.id.tvCreateDateCustomer);
            updatedDate  = (TextView) v.findViewById(R.id.tvUpdatedDateCustomer);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaCustomer.class);
                    goInput.putExtra("nama_customer", dc.getNama_customer());
                    goInput.putExtra("alamat_customer", dc.getAlamat_customer());
                    goInput.putExtra("tanggal_lahir_customer", dc.getTanggal_lahir_customer());
                    goInput.putExtra("nomor_hp_customer", dc.getNomor_hp_customer());
                    goInput.putExtra("id_customer", dc.getId_customer());
                    ctx.startActivity(goInput);
                }
            });
        }
    }
}
