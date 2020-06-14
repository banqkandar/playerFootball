package com.example.iskandar.view.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Players implements Parcelable {
    public static final Parcelable.Creator<Players> CREATOR = new Parcelable.Creator<Players>() {
        @Override
        public Players createFromParcel(Parcel source) {
            return new Players(source);
        }

        @Override
        public Players[] newArray(int size) {
            return new Players[size];
        }
    };
    private String nama;
    private String foto;
    private String posisi;

    public Players() {
    }

    protected Players(Parcel in) {
        this.nama = in.readString();
        this.foto = in.readString();
        this.posisi = in.readString();
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.foto);
        dest.writeString(this.posisi);
    }
}
