package com.choicemmed.ichoice.profile.adapter;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.choicemmed.ichoice.R;
import com.choicemmed.ichoice.healthcheck.entity.DevicesEntity;
import java.util.List;

import butterknife.BindView;


/**
 * 项目名称：iChoice
 * 类描述：
 * 创建人：114100
 * 创建时间：2019/4/9 10:19
 * 修改人：114100
 * 修改时间：2019/4/9 10:19
 * 修改备注：
 */
public class DevicesAdapter extends BaseQuickAdapter<DevicesEntity, BaseViewHolder> {


    public DevicesAdapter(@Nullable List<DevicesEntity> data) {
        super(R.layout.cell_item_device_view, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DevicesEntity item) {
        helper.setText(R.id.tv_devices,item.getName());
        helper.setImageResource(R.id.img_devices,item.getImageResId());
    }
}
