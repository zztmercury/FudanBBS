package com.zhuzhenting.fudanbbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.beans.fjjyy.RConInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zzt on 15-12-23.
 */
public class RConAdapter extends RecyclerView.Adapter<RConAdapter.ViewHolder> {
    private List<RConInfo> rConInfos;
    private Context context;

    public RConAdapter(Context context) {
        this.context = context;
        rConInfos = new ArrayList<>();
    }

    public void add(RConInfo data) {
        rConInfos.add(data);
    }

    public void addAll(Collection<RConInfo> dataSet) {
        rConInfos.addAll(dataSet);
    }

    public void clear() {
        rConInfos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tcon_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RConInfo rConInfo = rConInfos.get(position);
        holder.tvOwner.setText(rConInfo.getOwner());
        holder.tvDate.setText(rConInfo.getDate());
        holder.tvQuote.setText(rConInfo.getQuote());
        //String content = rConInfo.getContent();
        //content.s
        holder.tvContent.setText(rConInfo.getContent());
    }

    @Override
    public int getItemCount() {
        return rConInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOwner, tvQuote, tvContent, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOwner = (TextView) itemView.findViewById(R.id.text_owner);
            tvQuote = (TextView) itemView.findViewById(R.id.text_quote);
            tvContent = (TextView) itemView.findViewById(R.id.text_content);
            tvDate = (TextView) itemView.findViewById(R.id.text_date);
        }
    }
}
