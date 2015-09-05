/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.uae.tra_smart_services.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.HexagonHomeFragment.OnServiceSelectListener;
import com.uae.tra_smart_services.global.Service;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class ServicesRecyclerViewAdapter extends RecyclerView.Adapter<ServicesRecyclerViewAdapter.ViewHolder> {

    private List<Service> mDataSet;
    private Context mContext;
    private float mMarginOffset = 0;
    private OnServiceSelectListener mServiceSelectListener;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final HexagonView hvHexagonView;
        private final LinearLayout container;
        private final View rootView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mServiceSelectListener != null) {
                        mServiceSelectListener.onServiceSelect((Service) rootView.getTag(), null);
                    }
                }
            });

            rootView = v;
            textView = (TextView) v.findViewById(R.id.textView);
            hvHexagonView = (HexagonView) v.findViewById(R.id.hvHexagonView);
            container = (LinearLayout) v.findViewById(R.id.llContainer);
        }

        public View getContainer() {
            return container;
        }

        public void setData(final Service _service) {
            hvHexagonView.setHexagonSrcDrawable(_service.getDrawableRes());
            textView.setText(_service.getTitleRes());
            rootView.setTag(_service);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param _dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public ServicesRecyclerViewAdapter(final Context _context, final List<Service> _dataSet,
                                       final float _marginOffset) {
        mDataSet = _dataSet;
        mContext = _context;
        mMarginOffset = _marginOffset;
    }

    public final void setServiceSelectListener(final OnServiceSelectListener _serviceSelectListener) {
        mServiceSelectListener = _serviceSelectListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_service_item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.setData(mDataSet.get(position));

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewHolder.getContainer().getLayoutParams();
        if (position == 1 || position == 3) {
            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
            layoutParams.setMargins(layoutParams.getMarginStart(), (int) mMarginOffset + margin, layoutParams.getMarginEnd(), layoutParams.bottomMargin);
            viewHolder.getContainer().setLayoutParams(layoutParams);
        } else if (position == 0 || position == 2){
            layoutParams.setMargins(layoutParams.getMarginStart(), (int) mMarginOffset, layoutParams.getMarginEnd(), layoutParams.bottomMargin);
            viewHolder.getContainer().setLayoutParams(layoutParams);
        } else {
            layoutParams.setMargins(layoutParams.getMarginStart(), 0, layoutParams.getMarginEnd(), layoutParams.bottomMargin);
            viewHolder.getContainer().setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
