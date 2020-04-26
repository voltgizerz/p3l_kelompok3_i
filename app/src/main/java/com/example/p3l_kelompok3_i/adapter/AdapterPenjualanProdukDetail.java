package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutdetailpenjualanproduk, parent, false);
        AdapterPenjualanProdukDetail.HolderData holder = new AdapterPenjualanProdukDetail.HolderData(layout);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterPenjualanProdukDetail.HolderData holder, int position) {
        DataPenjualanProdukDetail dp = mList.get(position);
        holder.namaProduk.setText(dp.getNama_produk());
        holder.jumlahDibeli.setText(String.valueOf(dp.getJumlah_produk()));
        holder.subtotal.setText(String.valueOf(dp.getSubtotal()));
        Glide.with(ctx).load(dp.getGambar_produk()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imgProduk);

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
            List<DataPenjualanProdukDetail> filteredListPenjualan = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredListPenjualan.addAll(mListFull);
            } else {
                String filterPatternPenjualan = constraint.toString().toLowerCase().trim();
                for (DataPenjualanProdukDetail data : mListFull) {
                    if (data.getKode_transaksi_penjualan_produk_fk().toLowerCase().contains(filterPatternPenjualan) ) {
                        filteredListPenjualan.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListPenjualan;
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
        TextView namaProduk,jumlahDibeli,subtotal;
        DataPenjualanProdukDetail dp;
        ImageView imgProduk;

        public HolderData(final View v) {
            super(v);
            imgProduk = v.findViewById(R.id.imgProdukDetail);
            namaProduk = v.findViewById(R.id.tvNamaProdukDetailPenjualan);
            jumlahDibeli = v.findViewById(R.id.tvJumalhDibeliPenjualanProduk);
            subtotal = v.findViewById(R.id.tvHargaProdukDetail);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent goInput = new Intent(ctx, KelolaPenjualanProduk.class);
                    goInput.putExtra("id_detail_transaksi_penjualan_produk", dp.getId_detail_penjualan_produk());
                    goInput.putExtra("gambar_produk", dp.getGambar_produk());
                    goInput.putExtra("id_produk_fk", dp.getId_produk_penjualan_fk());
                    goInput.putExtra("jumlah_produk", dp.getJumlah_produk());
                    ctx.startActivity(goInput);

                }
            });
        }
    }


}
