package com.sty.websocketpush.websocket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sty.websocketpush.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: tian
 * @UpdateDate: 2020/9/8 10:50 AM
 */
public class RcvLogAdapter extends RecyclerView.Adapter<RcvLogAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> datas;

    public RcvLogAdapter(Context mContext) {
        this.mContext = mContext;
        this.datas = new ArrayList<>();
        this.datas.add("ladkfj");
        this.datas.add("kkkkk");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.item_logger, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvLog.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void addData(String log) {
        datas.add(0, log);
        notifyItemInserted(0);
    }

    public void clearLog() {
        datas.clear();
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvLog;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLog = itemView.findViewById(R.id.tv_log);
        }
    }
}
