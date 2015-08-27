package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.uae.tra_smart_services.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 11.08.2015.
 */
public class DevicesListAdapter extends BaseAdapter<String> implements Filterable {


    private Filter mServiceFilter;
    private boolean mIsDataChanged = true;
    private final int mDrawableColor;

    public DevicesListAdapter(final Context _context) {
        super(_context);
        mDrawableColor = getPrimaryColor(_context);
    }

    public DevicesListAdapter(final Context _context, final List<String> _data) {
        super(_context, _data);
        mDrawableColor = getPrimaryColor(_context);
    }

    private int getPrimaryColor(Context _context) {
        final TypedValue typedValue = new TypedValue();
        final Resources.Theme theme = _context.getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void addData(List<String> data) {
        super.addData(data);
        mIsDataChanged = true;
    }

    @Override
    public void clearData() {
        super.clearData();
        mIsDataChanged = true;
    }

    private void setFilteredData(final List<String> _filteredData) {
        getData().clear();
        getData().addAll(_filteredData);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (mServiceFilter == null) {
            mServiceFilter = new ServiceFilter(getData());
            mIsDataChanged = false;
        }
        return mServiceFilter;
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.list_item_service_list;
    }

    @Override
    protected BaseViewHolder<String> getViewHolder(View view) {
        return new ViewHolder(view, mDrawableColor);
    }

    private class ServiceFilter extends Filter {

        private final List<String> listData;

        public ServiceFilter(List<String> _listData) {
            listData = new ArrayList<>(_listData);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.count = listData.size();
                results.values = listData;
            } else {
                List<String> filteredList = getFilteredList(constraint);
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        private List<String> getFilteredList(CharSequence _constraint) {
            List<String> serviceList = new ArrayList<>();
            for (int i = 0; i < listData.size(); i++) {
                String name = listData.get(i);
                if (name.toLowerCase().contains(_constraint.toString().toLowerCase())) {
                    serviceList.add(name);
                }
            }
            return serviceList;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<String> filteredData = (List<String>) results.values;
            setFilteredData(filteredData);
        }
    }

    private static final class ViewHolder extends BaseViewHolder<String> {

        private final int mDrawableColor;
        private TextView tvServiceName;

        protected ViewHolder(View view, int _drawableColor) {
            super(view);
            mDrawableColor = _drawableColor;
            Drawable drawableStart = tvServiceName.getCompoundDrawablesRelative()[0];
            drawableStart.mutate();
            DrawableCompat.setTint(drawableStart, mDrawableColor);
        }

        @Override
        protected final void initViews(View view) {
            tvServiceName = (TextView) view.findViewById(R.id.tvServiceName_LISL);
        }

        @Override
        protected final void setData(int position, String data) {
            tvServiceName.setText(data);
        }
    }
}