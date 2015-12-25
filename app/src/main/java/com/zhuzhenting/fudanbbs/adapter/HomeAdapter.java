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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzt on 15-12-23.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final Map<String, String> boaMap = new HashMap<>();
    private Context context;

    public HomeAdapter(Context context) {
        this.context = context;
        initBoardMap();
    }

    private void initBoardMap() {
        boaMap.put("0", "BBS 系统");
        boaMap.put("1", "复旦大学");
        boaMap.put("2", "院系风采");
        boaMap.put("3", "五湖四海");
        boaMap.put("4", "休闲娱乐");
        boaMap.put("5", "文学艺术");
        boaMap.put("6", "体育健身");
        boaMap.put("7", "感性空间");
        boaMap.put("8", "新闻信息");
        boaMap.put("9", "学术学科");
        boaMap.put("A", "音乐影视");
        boaMap.put("B", "交易专区");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_class, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String title = boaMap.get(Integer.toHexString(position).toUpperCase());
        holder.tvBoaTitle.setText(title);
        holder.tvBoaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BoaActivity.class);
                i.putExtra("title",title);
                i.putExtra("dir", false);
                i.putExtra("input", Integer.toHexString(position).toUpperCase());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boaMap.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBoaTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBoaTitle = (TextView) itemView.findViewById(R.id.tv_boa_title);
        }
    }
}
