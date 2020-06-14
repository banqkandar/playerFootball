package com.example.iskandar.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.iskandar.R;
import com.example.iskandar.view.utils.AssetManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FotoDetailAdapter extends RecyclerView.Adapter<FotoDetailAdapter.FotoHolder> {

    private Context context;
    private String[] fotos;

    public FotoDetailAdapter(Context context) {
        this.context = context;
    }

    public void setFotos(String[] fotos){
        this.fotos = fotos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FotoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foto_detail, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FotoHolder fotoHolder, int position) {
        fotoHolder.IVfotodetail.setImageDrawable(AssetManager.drawableFromAsset(context, fotos[position]));
    }

    @Override
    public int getItemCount() {
        return fotos != null ? fotos.length : 0;
    }

    //class Holder
    class FotoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.foto_detail)
        ImageView IVfotodetail;

        public FotoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
