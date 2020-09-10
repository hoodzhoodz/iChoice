package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.framework.application.IchoiceApplication;

import java.util.Calendar;
import java.util.List;
import pro.choicemmed.datalib.Cbp1k1Data;

/**
 * @author Created by Jiang nan on 2019/11/27 14:29.
 * @description
 **/
public class HistoryBpAdapter extends RecyclerView.Adapter<HistoryBpAdapter.HistoryHolder>{
    private Context mContext;
    List<Cbp1k1Data> cbp1k1DataList;
    TextView time;
    TextView sys;
    TextView dia;
    TextView pr;
    TextView sunit;
    TextView dunit;

    public HistoryBpAdapter(Context mContext, List<Cbp1k1Data> cbp1k1DataList) {
        this.mContext = mContext;
        this.cbp1k1DataList = cbp1k1DataList;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.historical_item,null);
        HistoryHolder historyHolder = new HistoryHolder(view);
        return historyHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder historyHolder, int i) {
        if (historyHolder != null){
            Cbp1k1Data cbp1k1Data = cbp1k1DataList.get(i);
            int systolicPressure = cbp1k1Data.getSystolic();
            int diastolicPressure = cbp1k1Data.getDiastolic();
            if (IchoiceApplication.getAppData().userProfileInfo.getIsUnit()) {
                sys.setText(systolicPressure + "");
                dia.setText(diastolicPressure + "");
                sunit.setText("(mmHg)");
                dunit.setText("(mmHg)");
            }else {
                systolicPressure = (int)(systolicPressure*0.133);
                diastolicPressure = (int)(diastolicPressure*0.133);
                sys.setText(systolicPressure + "");
                dia.setText(diastolicPressure + "");
                sunit.setText("(kPa)");
                dunit.setText("(kPa)");
            }
            pr.setText(cbp1k1Data.getPulseRate().toString());
            Calendar calendar = DateUtils.strToCalendar(cbp1k1Data.getMeasureDateTime());
            time.setText(calendar.get(Calendar.HOUR_OF_DAY)+ ":" + calendar.get(Calendar.MINUTE));
        }
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return cbp1k1DataList == null? 0:cbp1k1DataList.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
             time = (TextView)itemView.findViewById(R.id.time);
             sys = (TextView)itemView.findViewById(R.id.sys);
             dia = (TextView)itemView.findViewById(R.id.dia);
             pr = (TextView)itemView.findViewById(R.id.pr);
             sunit = (TextView) itemView.findViewById(R.id.sys_unit);
             dunit = (TextView) itemView.findViewById(R.id.dia_unit);
        }
    }
}
