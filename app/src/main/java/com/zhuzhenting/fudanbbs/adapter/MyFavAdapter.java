package com.zhuzhenting.fudanbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.activity.PostActivity;
import com.zhuzhenting.fudanbbs.beans.js.FavSec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zzt on 15-12-25.
 */
public class MyFavAdapter extends RecyclerView.Adapter<MyFavAdapter.ViewHolder> {
    private List<FavSec> favSecs;
    private Context context;

    public MyFavAdapter(Context context) {
        this.context = context;
        favSecs = new ArrayList<>();
    }

    public void add(FavSec data) {
        favSecs.add(data);
    }

    public void addAll(Collection<FavSec> dataSet) {
        favSecs.addAll(dataSet);
    }

    public void clear() {
        favSecs.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_fav,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FavSec favSec = favSecs.get(position);
        holder.tvTitle.setText(favSec.getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PostActivity.class);
                i.putExtra("title",favSec.getDesc());
                i.putExtra("input",favSec.getName());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favSecs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }
}
