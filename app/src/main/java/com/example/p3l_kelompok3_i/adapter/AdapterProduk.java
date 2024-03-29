package com.example.p3l_kelompok3_i.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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

    public AdapterProduk(Context ctx, List<DataProduk> mList) {
        this.ctx = ctx;
        this.mList = mList;
        mListFull = new ArrayList<>(mList);
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutlist, parent, false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataProduk dp = mList.get(position);
        holder.namaProduk.setText(dp.getNama_produk());
        holder.hargaProduk.setText(String.valueOf(dp.getHarga_produk()));
        Integer panjang = String.valueOf(dp.getStok_produk()).length();
        String text2 = String.valueOf("(STOK PRODUK HABIS!)");
        Integer panjang2 = String.valueOf("(STOK PRODUK HABIS!)").length();
        Integer akhir = String.valueOf(dp.getStok_produk()+ "    (HAMPIR HABIS!)").length();
        String text = String.valueOf(dp.getStok_produk()+ "    (HAMPIR HABIS!)");
        SpannableString mSpan = new SpannableString(text);
        SpannableString mSpan2 = new SpannableString(text2);

        ForegroundColorSpan mRed = new ForegroundColorSpan(Color.RED);
        mSpan.setSpan(mRed,panjang,akhir, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSpan2.setSpan(mRed,0,panjang2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if(dp.getStok_produk() == 0) {
            holder.stokProduk.setText(mSpan2);
            holder.stokMinimalProduk.setText(String.valueOf(dp.getStok_minimal_produk()));
        } else if(dp.getStok_produk() < dp.getStok_minimal_produk()) {
             holder.stokProduk.setText(mSpan);
             holder.stokMinimalProduk.setText(String.valueOf(dp.getStok_minimal_produk()));
        }else {
             holder.stokProduk.setText(String.valueOf(dp.getStok_produk()));
             holder.stokMinimalProduk.setText(String.valueOf(dp.getStok_minimal_produk()));
         }


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

            if (constraint == null || constraint.length() == 0) {
                filteredListProduk.addAll(mListFull);
            } else {
                String filterPatternProduk = constraint.toString().toLowerCase().trim();
                for (DataProduk data : mListFull) {
                    if (data.getNama_produk().toLowerCase().contains(filterPatternProduk)) {
                        filteredListProduk.add(data);
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


    class HolderData extends RecyclerView.ViewHolder {
        TextView namaProduk, hargaProduk, stokProduk, stokMinimalProduk;
        ImageView gambarProduk;

        public HolderData(View v) {
            super(v);
            namaProduk = (TextView) v.findViewById(R.id.tvNamaProduk);
            hargaProduk = (TextView) v.findViewById(R.id.tvHargaProduk);
            stokProduk = (TextView) v.findViewById(R.id.tvStokProduk);
            stokMinimalProduk = (TextView) v.findViewById(R.id.tvStokMinimal);
            gambarProduk = (ImageView) v.findViewById(R.id.imgProduk);
        }
    }

}
