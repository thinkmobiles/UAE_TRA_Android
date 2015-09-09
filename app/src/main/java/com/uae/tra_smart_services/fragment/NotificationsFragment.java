package com.uae.tra_smart_services.fragment;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by PC on 9/9/2015.
 */
public class NotificationsFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivCloseNotificationList;
    private TextView tvNotificationListStatus;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivCloseNotificationList = findView(R.id.ivNotificationListClose_FN);
        tvNotificationListStatus = findView(R.id.tvNotificationListStatus_FN);
        tvNotificationListStatus.setText(
            getResources().getString(
                    R.string.str_you_have_notification,
                    getResources().getString(R.string.str_no)
            )
        );
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivCloseNotificationList.setOnClickListener(this);
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_notification;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivNotificationListClose_FN:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
