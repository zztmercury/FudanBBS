package com.zhuzhenting.fudanbbs.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuzhenting.fudanbbs.R;
import com.zhuzhenting.fudanbbs.beans.ServerReport;
import com.zhuzhenting.fudanbbs.beans.fjjyy.RConInfo;
import com.zhuzhenting.fudanbbs.servlet.Servlet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by zzt on 15-12-23.
 */
public class RConAdapter extends RecyclerView.Adapter<RConAdapter.ViewHolder> {
    private List<RConInfo> rConInfos;
    private OnReplySuccessListener onReplySuccessListener;
    private Context context;
    private String bid;

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
        final RConInfo rConInfo = rConInfos.get(position);
        holder.tvOwner.setText(rConInfo.getOwner());
        holder.tvDate.setText(rConInfo.getDate());
        holder.tvQuote.setText(rConInfo.getQuote());
        //String content = rConInfo.getContent();
        //content.s
        holder.tvContent.setText(rConInfo.getContent());
        holder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText etContent = new EditText(context);
                new AlertDialog.Builder(context)
                        .setView(etContent)
                        .setPositiveButton(R.string.reply, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = etContent.getText().toString();
                                if (TextUtils.isEmpty(content)) {
                                    etContent.setError("回复内容不能为空");
                                    return;
                                }
                                reply(rConInfo, content);
                            }
                        }).show();
            }
        });
    }

    private void reply(RConInfo rCon, String content) {
        final RConInfo rConInfo = new RConInfo();
        rConInfo.setBid(bid);
        rConInfo.setReid(rCon.getFid());
        rConInfo.setContent(content);
        new AsyncTask<Void, Void, ServerReport>() {
            @Override
            protected ServerReport doInBackground(Void... params) {
                return Servlet.sendReply(rConInfo);
            }

            @Override
            protected void onPostExecute(ServerReport serverReport) {
                if (serverReport.isSuccess()) {
                    Toast.makeText(context, R.string.reply_success, Toast.LENGTH_SHORT).show();
                    onReplySuccessListener.onReplySuccess();
                } else {
                    Toast.makeText(context, serverReport.getInfo(), Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    @Override
    public int getItemCount() {
        return rConInfos.size();
    }

    public void setOnReplySuccessListener(OnReplySuccessListener onReplySuccessListener) {
        this.onReplySuccessListener = onReplySuccessListener;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOwner, tvQuote, tvContent, tvDate;
        private Button btnReply;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOwner = (TextView) itemView.findViewById(R.id.text_owner);
            tvQuote = (TextView) itemView.findViewById(R.id.text_quote);
            tvContent = (TextView) itemView.findViewById(R.id.text_content);
            tvDate = (TextView) itemView.findViewById(R.id.text_date);
            btnReply = (Button) itemView.findViewById(R.id.btn_reply);
        }
    }

    public interface OnReplySuccessListener {
        void onReplySuccess();
    }
}
