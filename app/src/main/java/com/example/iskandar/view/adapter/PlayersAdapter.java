package com.example.iskandar.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iskandar.R;
import com.example.iskandar.view.model.Players;
import com.example.iskandar.view.utils.AssetManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayerHolder> {
    private Context context;
    private List<Players> playersList;
    private OnPlayerClick OnPlayerClick;

    public PlayersAdapter(Context context, List<Players> playersList) {
        this.context = context;
        this.playersList = playersList;
    }

    public void setOnPlayerClick(OnPlayerClick onplayerclick) {
        if(onplayerclick != null){
            this.OnPlayerClick = onplayerclick;
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull PlayerHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.view.clearAnimation();
    }

    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PlayerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_player, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerHolder playerHolder, int position) {
        final Players player = playersList.get(position);
        String[] foto = player.getFoto().split(",");
        playerHolder.IVfotoplayer.setImageDrawable(AssetManager.drawableFromAsset(context, foto[0]));
        playerHolder.TVnamaplayer.setText(player.getNama());
        playerHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnPlayerClick.onClick(player);
            }
        });
        playerHolder.view.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));

    }

    @Override
    public int getItemCount() {
        return playersList != null ? playersList.size() : 0;
    }

    //interface
    public interface OnPlayerClick {
        void onClick(Players player);
    }

    //class Holder
    class PlayerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.foto_player)
        ImageView IVfotoplayer;
        @BindView(R.id.nama_player)
        TextView TVnamaplayer;
        View view;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }
    }
}
