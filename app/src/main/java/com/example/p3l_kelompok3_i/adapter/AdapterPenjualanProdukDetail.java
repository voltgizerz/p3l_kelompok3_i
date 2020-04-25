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

import com.example.p3l_kelompok3_i.KelolaPenjualanProduk;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.penjualan_produk_detail.DataPenjualanProdukDetail;

import java.util.ArrayList;
import java.util.List;

public class AdapterPenjualanProdukDetail   extends RecyclerView.Adapter<AdapterPenjualanProdukDetail.HolderData> implements Filterable {

    private List<DataPenjualanProdukDetail> mList;
    private List<DataPenjualanProdukDetail> mListFull;
    private Context ctx;

    public AdapterPenjualanProdukDetail(Context ctx, List<DataPenjualanProdukDetail> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public AdapterPenjualanProdukDetail.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutpenjualanprodukdetail, parent, false);
        AdapterPenjualanProdukDetail.HolderData holder = new AdapterPenjualanProdukDetail.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualanProdukDetail.HolderData holder, int position) {
        DataPenjualanProdukDetail dp = mList.get(position);

        holder.dp = dp;
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
            List<DataPenjualanProdukDetail> filteredListPengadaan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPengadaan.addAll(mListFull);
            } else {
                String filterPatternPengadaan = constraint.toString().toLowerCase().trim();
                for (DataPenjualanProdukDetail data : mListFull) {
                    if (data.getKode_transaksi_penjualan_produk_fk().toLowerCase().contains(filterPatternPengadaan) ) {
                        filteredListPengadaan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListPengadaan;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mList.clear();
            mList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    class HolderData extends RecyclerView.ViewHolder {
        TextView kodeTransaksi, subTotal, totalHarga, diskon, status, tanggalPembayaran,createdDate,updatedDate,namaHewan,namacs;
        DataPenjualanProdukDetail dp;

        public HolderData(final View v) {
            super(v);
            kodeTransaksi = (TextView) v.findViewById(R.id.tvKodePenjualanProduk);
            namacs = (TextView) v.findViewById(R.id.tvNamaCS);
            status = (TextView) v.findViewById(R.id.tvStatusPenjualanProduk);
            subTotal = (TextView) v.findViewById(R.id.tvSubTotalPenjualanProduk);
            createdDate = (TextView) v.findViewById(R.id.tvCreateDatePenjualanProduk);
            updatedDate = (TextView) v.findViewById(R.id.tvUpdatedDatePenjualanProduk);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent goInput = new Intent(ctx, KelolaPenjualanProduk.class);
                    goInput.putExtra("id_detail_transaksi_penjualan_produk", dp.getId_detail_penjualan_produk());
                    ctx.startActivity(goInput);

                }
            });
        }
    }


}
