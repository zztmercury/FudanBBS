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
import com.zhuzhenting.fudanbbs.beans.fjjyy.PostInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zzt on 15-12-24.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<PostInfo> postInfos;
    private Context context;
    private String bid;

    public PostAdapter(Context context) {
        this.context = context;
        postInfos = new ArrayList<>();
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public void add(PostInfo data) {
        postInfos.add(data);
    }

    public void addAll(Collection<PostInfo> dataSet) {
        postInfos.addAll(dataSet);
    }

    public void clear() {
        postInfos.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final PostInfo postInfo = postInfos.get(position);
        holder.tvTitle.setText(postInfo.getTitle());
        holder.tvOwner.setText(postInfo.getOwner());
        holder.tvTime.setText(postInfo.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RConActivity.class);
                i.putExtra("gid", postInfo.getId());
                i.putExtra("bid", bid);
                i.putExtra("title",postInfo.getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOwner, tvTitle, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOwner = (TextView) itemView.findViewById(R.id.text_owner);
            tvTitle = (TextView) itemView.findViewById(R.id.text_title);
            tvTime = (TextView) itemView.findViewById(R.id.text_count);
        }
    }
}
