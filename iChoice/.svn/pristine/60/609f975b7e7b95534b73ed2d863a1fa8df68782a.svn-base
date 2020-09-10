package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicemmed.ichoice.R;

import java.util.List;

import pro.choicemmed.datalib.W628Data;

/**
 * @anthor by jiangnan
 * @Date on 2020/3/3.
 */
public class W628HistorySleepAdapter extends RecyclerView.Adapter<W628HistorySleepAdapter.SleepHolder>{
    private Context context;
    List<W628Data> w628DataList;

    private TextView tvStartTime;
    private TextView tvEndTime;

    private OnItemClickListener mOnItemClickListener;

    public W628HistorySleepAdapter(Context context, List<W628Data> w628DataList) {
        this.context = context;
        this.w628DataList = w628DataList;
    }


    @NonNull
    @Override
    public SleepHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sleep_time, null);
        SleepHolder sleepHolder = new SleepHolder(view);
        return sleepHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SleepHolder sleepHolder, int i) {
        if (sleepHolder != null){
            W628Data w628Data = w628DataList.get(i);
            String startTime = w628Data.getStartDate();
            String endTime = w628Data.getEndDate();
            tvStartTime.setText(startTime);
            tvEndTime.setText(endTime);
            sleepHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = sleepHolder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return w628DataList == null? 0:w628DataList.size();
    }


    public class SleepHolder extends RecyclerView.ViewHolder {

        public SleepHolder(@NonNull View itemView) {
            super(itemView);
            tvStartTime = itemView.findViewById(R.id.tv_sleep_startTime);
            tvEndTime = itemView.findViewById(R.id.tv_sleep_endTime);
        }
    }


    public interface OnItemClickListener
    {
        //子条目单机事件
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(W628HistorySleepAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = (OnItemClickListener) onItemClickListener;
    }
}
