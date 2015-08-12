package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

/**
 * Created by ak-buffalo on 10.08.15.
 */
public class DomainCheckerFragment extends BaseFragment {
    public static DomainCheckerFragment newInstance() {
        return new DomainCheckerFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_domain_checker;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAvail = (Button) view.findViewById(R.id.btnAvail_FDCH);
        Button btnWhoIS = (Button) view.findViewById(R.id.btnWhoIs_FDCH);
    }
}
