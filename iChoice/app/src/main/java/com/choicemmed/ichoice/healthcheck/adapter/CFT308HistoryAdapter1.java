package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.choicemmed.common.SharePreferenceUtil;
import com.choicemmed.common.StringUtils;
import com.choicemmed.ichoice.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import pro.choicemmed.datalib.CFT308Data;


public class CFT308HistoryAdapter1 extends RecyclerView.Adapter<CFT308HistoryAdapter1.Holder> {
    private Context context;
    List<CFT308Data> cft308DataList;

    private TextView tvStartTime;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;

    public CFT308HistoryAdapter1(Context context, List<CFT308Data> cft308Data) {
        this.context = context;
        this.cft308DataList = cft308Data;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cft308, viewGroup, false);
        Holder sleepHolder = new Holder(view);
        return sleepHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        if (holder != null) {
            CFT308Data w628Data = cft308DataList.get(i);
            String startTime = w628Data.getMeasureTime();
            textView4.setVisibility(View.GONE);
            tvStartTime.setText(startTime.substring(startTime.length() - 8));

            if ((int) SharePreferenceUtil.get(context, "temp_unit", 1) == 1) {
                textView1.setText(w628Data.getTemp().substring(0, w628Data.getTemp().length() - 2));
                textView2.setText(w628Data.getTemp().substring(w628Data.getTemp().length() - 2));
                textView3.setText(context.getString(R.string.temp_unit));
            } else {
                textView3.setText(context.getString(R.string.temp_unit1));
                String f = String.valueOf((Float.parseFloat(w628Data.getTemp()) * 9 / 5) + 32);
                DecimalFormat df = new DecimalFormat("#.0");
                df.setRoundingMode(RoundingMode.DOWN);
                f = df.format(Float.parseFloat(f));
                if (!StringUtils.isEmpty(f)) {
                    textView1.setText(f.substring(0, f.length() - 2));
                    textView2.setText(f.substring(f.length() - 2));
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return cft308DataList == null ? 0 : cft308DataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvStartTime = itemView.findViewById(R.id.time);
            textView1 = itemView.findViewById(R.id.tv1);
            textView2 = itemView.findViewById(R.id.tv2);
            textView3 = itemView.findViewById(R.id.temp_unit);
            textView4 = itemView.findViewById(R.id.tv4);

        }
    }


}
