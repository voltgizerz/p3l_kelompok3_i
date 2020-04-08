package com.example.p3l_kelompok3_i.model_pengadaan;

public class ProdukDibeli
{
    private String kode_pengadaan_fk;
    private String satuan_pengadaan;
    private String id_produk_fk;
    private String jumlah_pengadaan;

    public String getKode_pengadaan_fk ()
    {
        return kode_pengadaan_fk;
    }

    public void setKode_pengadaan_fk (String kode_pengadaan_fk)
    {
        this.kode_pengadaan_fk = kode_pengadaan_fk;
    }

    public String getSatuan_pengadaan ()
    {
        return satuan_pengadaan;
    }

    public void setSatuan_pengadaan (String satuan_pengadaan)
    {
        this.satuan_pengadaan = satuan_pengadaan;
    }

    public String getId_produk_fk ()
    {
        return id_produk_fk;
    }

    public void setId_produk_fk (String id_produk_fk)
    {
        this.id_produk_fk = id_produk_fk;
    }

    public String getJumlah_pengadaan ()
    {
        return jumlah_pengadaan;
    }

    public void setJumlah_pengadaan (String jumlah_pengadaan)
    {
        this.jumlah_pengadaan = jumlah_pengadaan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [kode_pengadaan_fk = "+kode_pengadaan_fk+", satuan_pengadaan = "+satuan_pengadaan+", id_produk_fk = "+id_produk_fk+", jumlah_pengadaan = "+jumlah_pengadaan+"]";
    }
}