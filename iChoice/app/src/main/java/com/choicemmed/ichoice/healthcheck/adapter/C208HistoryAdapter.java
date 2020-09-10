package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;

import java.util.Calendar;
import java.util.List;

import pro.choicemmed.datalib.OxSpotData;

import static com.choicemmed.common.FormatUtils.sleep_Time;
import static com.choicemmed.common.FormatUtils.template_DbDateTime;

public class C208HistoryAdapter extends RecyclerView.Adapter<C208HistoryAdapter.OXHistoryHolder> {

    private Context mContext;
    List<OxSpotData> oxSpotDataList;
    TextView tv_time;
    TextView tv_spo2;
    TextView tv_PR;
    TextView tv_PI;
    TextView tv_RR;

    public C208HistoryAdapter(Context context, List<OxSpotData> list) {
        this.mContext = context;
        this.oxSpotDataList = list;
    }

    @NonNull
    @Override
    public OXHistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ox_spot_historical, null);
        OXHistoryHolder oxHistoryHolder = new OXHistoryHolder(view);
        return oxHistoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OXHistoryHolder oxHistoryHolder, int i) {
        if (oxHistoryHolder != null) {
            OxSpotData oxSpotData = oxSpotDataList.get(i);
            int spo2 = oxSpotData.getBloodOxygen();
            int PR = oxSpotData.getPulseRate();
            float PI = oxSpotData.getPi();
            int RR = oxSpotData.getRR();
            tv_spo2.setText(spo2 + "");
            tv_PR.setText(PR + "");
            if (PI == 0) {
                tv_PI.setText("--");
            } else {
                tv_PI.setText(PI + "");
            }
            //显示RR值
            if (RR > 0) {
                tv_RR.setText(String.valueOf(RR));
            } else {
                tv_RR.setText(mContext.getResources().getString(R.string.ox_no_data));
            }
            String time = FormatUtils.getDateTimeString(FormatUtils.parseDate(oxSpotData.getMeasureDateTime(), template_DbDateTime), sleep_Time);
            tv_time.setText(time.substring(0, time.length() - 3));
        }
    }


    @Override
    public int getItemCount() {
        return oxSpotDataList == null ? 0 : oxSpotDataList.size();
    }

    public class OXHistoryHolder extends RecyclerView.ViewHolder {
        public OXHistoryHolder(@NonNull View viewItem) {
            super(viewItem);
            tv_time = (TextView) viewItem.findViewById(R.id.item_time_tv);
            tv_spo2 = (TextView) viewItem.findViewById(R.id.item_spo2_tv);
            tv_PR = (TextView) viewItem.findViewById(R.id.item_PR_tv);
            tv_PI = (TextView) viewItem.findViewById(R.id.item_PI_tv);
            tv_RR = (TextView) viewItem.findViewById(R.id.item_RR_tv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
