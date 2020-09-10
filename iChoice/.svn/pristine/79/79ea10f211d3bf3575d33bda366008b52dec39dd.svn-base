package com.choicemmed.ichoice.healthcheck.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.choicemmed.common.DateUtils;
import com.choicemmed.common.FormatUtils;
import com.choicemmed.ichoice.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pro.choicemmed.datalib.OxRealTimeData;
import pro.choicemmed.datalib.OxSpotData;

import static com.choicemmed.common.FormatUtils.sleep_Time;
import static com.choicemmed.common.FormatUtils.template_DbDateTime;

public class C208sHistoryAdapter extends RecyclerView.Adapter<C208sHistoryAdapter.OXHistoryHolder> {

    private Context mContext;
    List<OxRealTimeData> oxSpotDataList;
    TextView tv_time;
    TextView tv_time_count;
    LinearLayout item;

    public ItemClickListener getListener() {
        return listener;
    }

    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }

    private ItemClickListener listener;

    public C208sHistoryAdapter(Context context, List<OxRealTimeData> list) {
        this.mContext = context;
        this.oxSpotDataList = list;
    }

    @NonNull
    @Override
    public OXHistoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ox_real_time, viewGroup, false);
        OXHistoryHolder oxHistoryHolder = new OXHistoryHolder(view);
        return oxHistoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OXHistoryHolder oxHistoryHolder, int i) {
        if (oxHistoryHolder != null) {
            OxRealTimeData oxRealTimeData = oxSpotDataList.get(i);
            String time = FormatUtils.getDateTimeString(FormatUtils.parseDate(oxRealTimeData.getMeasureDateStartTime(), template_DbDateTime), sleep_Time);
            tv_time.setText(time);

            Date start = FormatUtils.parseDate(oxRealTimeData.getMeasureDateStartTime(), template_DbDateTime);
            Date end = FormatUtils.parseDate(oxRealTimeData.getMeasureDateEndTime(), template_DbDateTime);
//            tv_time_count.setText(formatDuring(end.getTime() - start.getTime()));
            tv_time_count.setText((int) (end.getTime() - start.getTime()) / 1000 + "");

        }
    }

    private static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;

        if (days != 0) {
            return days + " d " + hours + " h " + minutes + " min " + seconds + "";
        }

        if (hours != 0) {
            return hours + " h " + minutes + " min " + seconds + "";
        }

        if (minutes != 0) {
            return minutes + " min " + seconds + "";
        }
        return seconds + "";

    }

    @Override
    public int getItemCount() {
        return oxSpotDataList == null ? 0 : oxSpotDataList.size();
    }

    public class OXHistoryHolder extends RecyclerView.ViewHolder {
        public OXHistoryHolder(@NonNull final View viewItem) {
            super(viewItem);
            tv_time = (TextView) viewItem.findViewById(R.id.tv_time);
            tv_time_count = (TextView) viewItem.findViewById(R.id.tv_time_count);
            item = (LinearLayout) itemView.findViewById(R.id.ll_ox_real);
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
