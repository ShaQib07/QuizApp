package com.shakib.bdlabit.pmpquizprep.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.shakib.bdlabit.pmpquizprep.Common.common;
import com.shakib.bdlabit.pmpquizprep.Dashboard;
import com.shakib.bdlabit.pmpquizprep.Interface.itemClickListener;
import com.shakib.bdlabit.pmpquizprep.LocSub;
import com.shakib.bdlabit.pmpquizprep.MockTest;
import com.shakib.bdlabit.pmpquizprep.Model.MockListItem;
import com.shakib.bdlabit.pmpquizprep.Model.Subject;
import com.shakib.bdlabit.pmpquizprep.R;

import java.util.ArrayList;
import java.util.logging.Handler;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import static com.shakib.bdlabit.pmpquizprep.R.color.light_green;

public class MockAdapter extends RecyclerView.Adapter<MockAdapter.MockAdapterViewHolder> implements RewardedVideoAdListener {

    public Activity c;
    public ArrayList<MockListItem> arrayList;
    private RewardedVideoAd rewardedVideoAd;
    String mockNo1;

    public MockAdapter(Activity c, ArrayList<MockListItem> arrayList){
        this.c = c;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MockAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mock_list,parent,false);

        return new MockAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MockAdapterViewHolder holder, int position) {

        MobileAds.initialize(c.getApplicationContext(),"ca-app-pub-3940256099942544~3347511713");
        rewardedVideoAd = MobileAds.getRewardedVideoAdInstance(c);
        rewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();
        final String mockNo = arrayList.get(position).getMockName();

        holder.mockNo.setText(mockNo);

        if (arrayList.get(position).isCompleted()){
            holder.card.setCardBackgroundColor(c.getResources().getColor(R.color.redish));
            holder.mockLock.setVisibility(View.INVISIBLE);

        } else {
            holder.card.setCardBackgroundColor(c.getResources().getColor(light_green));
            holder.mockLock.setVisibility(View.VISIBLE);

        }

        holder.setItemClickListener((view, position1, isLongClick) -> {
            mockNo1 = "Mock "+(position1 +1);
            AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("UNLOCK!")
                    .setMessage("You have to watch a reward video to proceed.")
                    .setPositiveButton("Play", (dialog, which) -> {
                        if (rewardedVideoAd.isLoaded()){
                            rewardedVideoAd.show();
                        } else {
                            c.startActivity(new Intent(c.getApplicationContext(), MockTest.class).putExtra("mock", mockNo1));
                            c.finish();
                        }
                    })
                    .show();
        });


    }

    private void loadRewardedVideoAd(){
        rewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        rewardedVideoAd.show();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        //loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        c.startActivity(new Intent(c.getApplicationContext(), MockTest.class).putExtra("mock", mockNo1));
        c.finish();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    public class MockAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mockNo;
        ImageView mockLock;
        CardView card;

        private itemClickListener itemClickListener;

        public MockAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            mockNo = itemView.findViewById(R.id.mock_no);
            mockLock = itemView.findViewById(R.id.mock_lock);
            card = itemView.findViewById(R.id.card);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(itemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }
    }
}

