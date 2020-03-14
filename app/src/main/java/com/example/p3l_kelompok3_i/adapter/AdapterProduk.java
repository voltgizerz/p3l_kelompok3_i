package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
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
import com.example.p3l_kelompok3_i.DaftarProduk;
import com.example.p3l_kelompok3_i.R;
import com.example.p3l_kelompok3_i.model_produk.DataProduk;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduk extends RecyclerView.Adapter<AdapterProduk.HolderData> implements Filterable {

    private List<DataProduk> mList;
    private List<DataProduk> mListFull;
    private Context ctx;

    public AdapterProduk(Context ctx, List<DataProduk> mList)
    {
        this.ctx = ctx;
        this.mList= mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataProduk dp = mList.get(position);
        holder.namaProduk.setText(dp.getNama_produk());
        holder.hargaProduk.setText(String.valueOf(dp.getHarga_produk()));
        holder.stokProduk.setText(String.valueOf(dp.getStok_produk()));
        holder.stokMinimalProduk.setText(String.valueOf(dp.getStok_minimal_produk()));

        Glide.with(ctx).load(dp.getGambar_produk()).thumbnail(0.5f).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.gambarProduk);
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
            List<DataProduk> filteredListProduk = new ArrayList<>();

            if(constraint == null || constraint.length()== 0){
                filteredListProduk.addAll(mListFull);
            }else{
                String filterPatternProduk = constraint.toString().toLowerCase().trim();
                for(DataProduk item : mListFull){
                    if(item.getNama_produk().toLowerCase().contains(filterPatternProduk)){
                        filteredListProduk.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredListProduk;

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
        TextView namaProduk, hargaProduk, stokProduk,stokMinimalProduk;
        ImageView gambarProduk;

        public HolderData(View v)
        {
            super(v);

            namaProduk =(TextView) v.findViewById(R.id.tvNamaProduk);
            hargaProduk =(TextView) v.findViewById(R.id.tvHargaProduk);
            stokProduk =(TextView) v.findViewById(R.id.tvStokProduk);
            stokMinimalProduk =(TextView) v.findViewById(R.id.tvStokMinimal);
            gambarProduk = (ImageView) v.findViewById(R.id.imgProduk);
        }

    }


}
