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

import pro.choicemmed.datalib.W314B4Data;

/**
 * @author Created by Jiang nan on 2020/1/19 12:15.
 * @description
 **/
public class W314HistorySleepAdapter extends RecyclerView.Adapter<W314HistorySleepAdapter.SleepHolder>{
    private Context context;
    List<W314B4Data> w314B4DataList;

    private TextView tvStartTime;
    private TextView tvEndTime;
    private OnItemClickListener mOnItemClickListener;

    public W314HistorySleepAdapter(Context context, List<W314B4Data> w314B4DataList) {
        this.context = context;
        this.w314B4DataList = w314B4DataList;
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
            W314B4Data w314B4Data = w314B4DataList.get(i);
            String startTime = w314B4Data.getStartDate();
            String endTime = w314B4Data.getEndDate();
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
        return w314B4DataList == null? 0:w314B4DataList.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
