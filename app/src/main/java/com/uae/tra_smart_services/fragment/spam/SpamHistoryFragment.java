package com.uae.tra_smart_services.fragment.spam;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.SpamHistoryAdapter;
import com.uae.tra_smart_services.adapter.SpamHistoryAdapter.OnDeleteClickListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.util.SmsUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 24.09.2015.
 */
public class SpamHistoryFragment extends BaseFragment implements OnClickListener, OnDeleteClickListener, RadioGroup.OnCheckedChangeListener {

    //region Declare @SpamType
    @IntDef({SPAM_SMS, SPAM_WEB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SpamType {
    }

    public static final int SPAM_SMS = 0;
    public static final int SPAM_WEB = 1;
    //endregion

    private RadioGroup rgSpamHistoryTabs;
    private LinearLayout llAddToSpam;
    private RecyclerView rvSpamHistory;

    private SpamHistoryAdapter mHistoryAdapter;
    private LinearLayoutManager mHistoryLayoutManager;

    private OnAddToSpamClickListener mAddToSpamClickListener;

    public static SpamHistoryFragment newInstance() {
        return new SpamHistoryFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnAddToSpamClickListener) {
            mAddToSpamClickListener = (OnAddToSpamClickListener) _activity;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        rgSpamHistoryTabs = findView(R.id.rgSpamHistoryTabs_FRSH);
        llAddToSpam = findView(R.id.llAddToSpam_FRSH);
        rvSpamHistory = findView(R.id.rvSpamHistory_FRSH);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        llAddToSpam.setOnClickListener(this);
        rgSpamHistoryTabs.setOnCheckedChangeListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSpamHistoryList();
    }

    private void initSpamHistoryList() {
        List<String> stubData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            stubData.add("Promotion #" + (i + 1));
        }

        mHistoryAdapter = new SpamHistoryAdapter(getActivity(), stubData);
        mHistoryAdapter.setDeleteClickListener(this);
        rvSpamHistory.setAdapter(mHistoryAdapter);

        mHistoryLayoutManager = new LinearLayoutManager(getActivity());
        rvSpamHistory.setLayoutManager(mHistoryLayoutManager);
    }

    @Override
    public final void onClick(final View _view) {
        switch (_view.getId()) {
            case R.id.llAddToSpam_FRSH:
                if (mAddToSpamClickListener != null) {
                    handleAddToSpamClick();
                }
                break;
        }
    }

    private void handleAddToSpamClick() {
        switch (rgSpamHistoryTabs.getCheckedRadioButtonId()) {
            case R.id.rbSmsSpam_FRSH:
                mAddToSpamClickListener.onAddToSpamClick(SPAM_SMS);
                break;
            case R.id.rbWebSpam_FRSH:
                mAddToSpamClickListener.onAddToSpamClick(SPAM_WEB);
                break;
        }
    }

    @Override
    public final void onCheckedChanged(final RadioGroup _group, final int _checkedId) {

    }

    @Override
    public final void onDeleteClick(final String _data, final int _position) {
        mHistoryAdapter.deleteItem(_position);
        SmsUtils.sendUnblockSms(getActivity(), "no name");
    }

    @Override
    public void onDetach() {
        mAddToSpamClickListener = null;
        super.onDetach();
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_report_history_title;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_report_spam_history;
    }

    public interface OnAddToSpamClickListener {
        void onAddToSpamClick(@SpamType int _spamType);
    }

}
