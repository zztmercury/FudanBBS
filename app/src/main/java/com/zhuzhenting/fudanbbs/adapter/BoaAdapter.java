package com.zhuzhenting.fudanbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.activity.BoaActivity;
import com.zhuzhenting.fudanbbs.activity.PostActivity;
import com.zhuzhenting.fudanbbs.beans.fjjyy.BoaInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zzt on 15-12-23.
 */
public class BoaAdapter extends RecyclerView.Adapter<BoaAdapter.ViewHolder> {
    private List<BoaInfo> boaInfos;
    private Context context;
    private Comparator<BoaInfo> comparator;

    public BoaAdapter(Context context) {
        this.context = context;
        boaInfos = new ArrayList<>();
        comparator = new Comparator<BoaInfo>() {
            @Override
            public int compare(BoaInfo lhs, BoaInfo rhs) {
                if (lhs.getDir().equals(rhs.getDir())) {
                    return lhs.getTitle().compareTo(rhs.getTitle());
                } else
                    return lhs.getDir().compareTo(rhs.getDir());
            }
        };
    }

    public void add(BoaInfo data) {
        boaInfos.add(data);
    }

    public void addAll(Collection<BoaInfo> dataSet) {
        boaInfos.addAll(dataSet);
    }

    public void clear() {
        boaInfos.clear();
    }

    public void sort() {
        Collections.sort(boaInfos, comparator);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_boa, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BoaInfo boaInfo = boaInfos.get(position);
        holder.tvCount.setText(boaInfo.getCount());
        holder.tvTitle.setText(boaInfo.getDesc());
        holder.tvCate.setText(boaInfo.getCate());
        holder.tvBm.setText(boaInfo.getBm());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                if (boaInfo.getDir().equals("1")) {
                    i.setClass(context, BoaActivity.class);
                    i.putExtra("dir", true);
                } else {
                    i.setClass(context, PostActivity.class);
                }
                i.putExtra("title", boaInfo.getDesc());
                i.putExtra("input", boaInfo.getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boaInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCate, tvBm, tvTitle, tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCate = (TextView) itemView.findViewById(R.id.tv_cate);
            tvBm = (TextView) itemView.findViewById(R.id.tv_bm);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        }
    }
}
