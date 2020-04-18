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

import com.example.p3l_kelompok3_i.KelolaHewan;
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
        holder.idHewan.setText(String.valueOf(dh.getId_hewan()));
        holder.namaHewan.setText(dh.getNama_hewan());
        holder.tanggalLahirHewan.setText(String.valueOf(dh.getTanggal_lahir_hewan()));
        holder.idJenisHewan.setText(String.valueOf(dh.getNama_jenis_hewan()));
        holder.idUkuranHewan.setText(String.valueOf(dh.getUkuran_hewan()));
        holder.idCustomer.setText(String.valueOf(dh.getNama_customer()));
        holder.createdDate.setText(String.valueOf(dh.getCreated_date()));
        holder.updatedDate.setText(String.valueOf(dh.getUpdated_date()));
        holder.dh=dh;
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
                    if(data.getNama_hewan().toLowerCase().contains(filterPatternHewan) || data.getNama_customer().toLowerCase().contains(filterPatternHewan) || data.getNama_jenis_hewan().toLowerCase().contains(filterPatternHewan)){
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
        DataHewan dh;
        TextView namaHewan,tanggalLahirHewan,idJenisHewan,idUkuranHewan,idCustomer,idHewan,createdDate,updatedDate;

        public HolderData(View v)
        {
            super(v);
            namaHewan =(TextView) v.findViewById(R.id.tvNamaHewan);
            tanggalLahirHewan =(TextView) v.findViewById(R.id.tvTanggalLahirHewan);
            idJenisHewan =(TextView) v.findViewById(R.id.tvIdJenisHewan);
            idUkuranHewan =(TextView) v.findViewById(R.id.tvIdUkuranHewan);
            idCustomer =(TextView) v.findViewById(R.id.tvIdCustomer);
            idHewan = (TextView) v.findViewById(R.id.tvIdHewan);
            createdDate  = (TextView) v.findViewById(R.id.tvCreateDateHewan);
            updatedDate  = (TextView) v.findViewById(R.id.tvUpdatedDateHewan);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, KelolaHewan.class);
                    goInput.putExtra("nama_hewan", dh.getNama_hewan());
                    goInput.putExtra("tanggal_lahir_hewan", dh.getTanggal_lahir_hewan());
                    goInput.putExtra("id_jenis_hewan", dh.getId_jenis_hewan());
                    goInput.putExtra("id_ukuran_hewan", dh.getId_ukuran_hewan());
                    goInput.putExtra("id_customer", dh.getId_customer());
                    goInput.putExtra("id_hewan", dh.getId_hewan());

                    ctx.startActivity(goInput);
                }
            });
        }
    }
}
