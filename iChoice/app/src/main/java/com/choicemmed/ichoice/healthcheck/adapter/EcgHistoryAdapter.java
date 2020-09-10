package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;
import com.choicemmed.ichoice.healthcheck.view.EcgView;

import java.util.List;

import pro.choicemmed.datalib.EcgData;

import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.uncompressA12bEcgData;
import static com.choicemmed.ichoice.healthcheck.fragment.ecg.EcgMeasureFragment.uncompressP10bEcgData;

public class EcgHistoryAdapter extends RecyclerView.Adapter<EcgHistoryAdapter.Holder> {

    private Context mContext;
    List<EcgData> ecgDataList;
    CardView item;
    TextView tv_time;
    TextView tv_heart_rate;
    EcgView ecgView;
    TextView ecg_result;

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    private ItemClickListener listener;

    public EcgHistoryAdapter(Context context, List<EcgData> list) {
        this.mContext = context;
        this.ecgDataList = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ecg_data, viewGroup, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int j) {
        if (holder != null) {
            EcgData ecgData = ecgDataList.get(j);
            if (ecgData.getEcgResult() == 1) {
                ecg_result.setTextColor(mContext.getResources().getColor(R.color.color_000000));
                ecg_result.setText(mContext.getString(R.string.Normal));
            } else if (ecgData.getEcgResult() == 2) {
                ecg_result.setTextColor(mContext.getResources().getColor(R.color.ecg_fa5d5c));
                ecg_result.setText(mContext.getString(R.string.ecg_error));
            } else {
                ecg_result.setText("");
            }
            tv_time.setText(ecgData.getMeasureTime());
            tv_heart_rate.setText(ecgData.getDecodeBpm() + "");
                String [] data = ecgData.getEcgData().split(",");
                int[]  ecg = new int[data.length];
                for (int i = 0; i < data.length; i++) {
                    ecg[i]= Integer.parseInt(data[i]);
                }
            ecgView.redrawEcg(ecg);
        }
    }

    @Override
    public int getItemCount() {
        return ecgDataList == null ? 0 : ecgDataList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull final View viewItem) {
            super(viewItem);
            ecg_result = viewItem.findViewById(R.id.ecg_result);
            tv_time = viewItem.findViewById(R.id.tv_time);
            tv_heart_rate = viewItem.findViewById(R.id.tv_avg_hr1);
            ecgView = viewItem.findViewById(R.id.ecg_chart);
            item = itemView.findViewById(R.id.ll_ecg);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemViewClick(getPosition());
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface ItemClickListener {
        void onItemViewClick(int position);
    }
}
