package com.zhuzhenting.fudanbbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.activity.RConActivity;
import com.zhuzhenting.fudanbbs.beans.js.TopInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zzt on 15-12-11.
 */
public class TopTopicAdapter extends RecyclerView.Adapter<TopTopicAdapter.ViewHolder> {
    private List<TopInfo> topInfos;
    private Context context;

    public TopTopicAdapter(Context context) {
        topInfos = new ArrayList<>();
        this.context = context;
    }

    public void add(TopInfo data) {
        topInfos.add(data);
    }

    public void addAll(Collection<TopInfo> dataSet) {
        topInfos.addAll(dataSet);
    }

    public void clear() {
        topInfos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TopInfo topInfo = topInfos.get(position);
        holder.textBoard.setText(topInfo.getBoard());
        holder.textOwner.setText(topInfo.getOwner());
        holder.textTitle.setText(topInfo.getTitle());
        holder.textCount.setText(topInfo.getCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RConActivity.class);
                i.putExtra("gid", topInfo.getGid());
                i.putExtra("bid", topInfo.getBoard());
                i.putExtra("title",topInfo.getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textBoard, textOwner, textTitle, textCount;

        public ViewHolder(View itemView) {
            super(itemView);
            textBoard = (TextView) itemView.findViewById(R.id.text_board);
            textOwner = (TextView) itemView.findViewById(R.id.text_owner);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            textCount = (TextView) itemView.findViewById(R.id.text_count);
        }
    }
}
