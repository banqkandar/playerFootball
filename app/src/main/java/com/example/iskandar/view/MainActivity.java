package com.example.iskandar.view;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.example.iskandar.R;
import com.example.iskandar.view.adapter.FotoDetailAdapter;
import com.example.iskandar.view.adapter.PlayersAdapter;
import com.example.iskandar.view.model.Players;
import com.example.iskandar.view.utils.AssetManager;
import com.ramotion.cardslider.CardSliderLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_player)
    TextView TVplayer;
    @BindView(R.id.tv_posisi)
    TextView TVposisi;
    @BindView(R.id.tv_nama)
    TextView TVnama;
    @BindView(R.id.img_profil)
    ImageView IVprofil;

    //RecyclerView
    @BindView(R.id.rv_list_player)
    RecyclerView RVlistplayer;
    @BindView(R.id.rv_foto_detail)
    RecyclerView RVfotodetail;

    //Layout
    @BindView(R.id.bottom_sheet_main)
    LinearLayout Lbottomsheetmain;
    @BindView(R.id.layout_detail_list)
    LinearLayout Ldetaillist;
    @BindView(R.id.layout_detail_wisata)
    LinearLayout linearLayoutDetailWisata;
    @BindView(R.id.layout_about)
    LinearLayout Labout;
    @BindView(R.id.layout_bio)
    LinearLayout Lbio;

    private PlayersAdapter playersAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    /*
    OnClick close Bottom sheet
     */
    @OnClick(R.id.button_back)
    public void onCloseBottomSheet() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    /*
    OnClick close Bottom sheet
     */
    @OnClick(R.id.button_back2)
    public void onClose() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    showBottomSheet("about", null, null, null);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //Init Views
    private void initViews() {
        //android Toolbar
        initToolbar();
        initBottomSheet();
        initRecyclerViewWisata();

    }

    //init Toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    //Init RecyclerView Players
    private void initRecyclerViewWisata() {
        List<Players> playersList = getDataPlayers();
        Log.d(TAG, String.valueOf(playersList.size()));
        playersAdapter = new PlayersAdapter(this, playersList);
        RVlistplayer.setLayoutManager(new LinearLayoutManager(this));
        RVlistplayer.setAdapter(playersAdapter);
        playersAdapter.setOnPlayerClick(new PlayersAdapter.OnPlayerClick() {
            @Override
            public void onClick(Players player) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    showBottomSheet("detail", player.getFoto().split(","), player.getNama(), player.getPosisi());
                }
            }
        });
    }

    //Init RecyclerView foto
    private void initRecyclerViewFoto(String[] files) {
        FotoDetailAdapter fotoWisataAdapter = new FotoDetailAdapter(this);
        fotoWisataAdapter.setFotos(files);
        RVfotodetail.setAdapter(fotoWisataAdapter);
        RVfotodetail.setLayoutManager(new CardSliderLayoutManager(this));
        //new CardSnapHelper().attachToRecyclerView(recyclerViewFoto);
    }

    //Init BottomSheet
    private void initBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(Lbottomsheetmain);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    //get data from assets
    private List<Players> getDataPlayers() {
        List<Players> playerList = new ArrayList<>();
        Players player;
        try {
            JSONObject jsonObject = new JSONObject(AssetManager.loadJson(this));
            JSONArray jsonArray = jsonObject.getJSONArray("players");
            //Log.d(TAG, String.valueOf(jsonArray.length()));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject values = jsonArray.getJSONObject(i);
                player = new Players();
                player.setNama(values.getString("nama"));
                player.setFoto(values.getString("foto"));
                player.setPosisi(values.getString("posisi"));
                playerList.add(player);
            }

            //handle exception
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playerList;
    }

    //show BottomSheet
    private void showBottomSheet(String type, String[] files, String nama_player, String posisi_player) {
        if (type.equalsIgnoreCase("detail")) {
            Ldetaillist.setVisibility(View.VISIBLE);
            Labout.setVisibility(View.GONE);

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            TVplayer.setText(nama_player);
            TVposisi.setText(posisi_player);
            TVplayer.setTypeface(AssetManager.loadTypeface(this, "open-sans-extrabold.ttf"));
            RVfotodetail.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
            linearLayoutDetailWisata.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
            initRecyclerViewFoto(files);

        } else {
            Ldetaillist.setVisibility(View.GONE);
            Labout.setVisibility(View.VISIBLE);

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            Lbio.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up));
            IVprofil.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in));
            TVnama.setTypeface(AssetManager.loadTypeface(this, "open-sans-extrabold.ttf"));
        }
    }
}
