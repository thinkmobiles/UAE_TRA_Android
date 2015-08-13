package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.text.TextUtils;
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

    public DevicesListAdapter(Context context) {
        super(context);
    }

    public DevicesListAdapter(Context context, List<String> data) {
        super(context, data);
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

    private void setFilteredData(final List<String> _filteredData){
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
        return new ViewHolder(view);
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

    private static class ViewHolder extends BaseViewHolder<String> {

        private TextView tvServiceName;

        protected ViewHolder(View view) {
            super(view);
        }

        @Override
        protected void initViews(View view) {
            tvServiceName = (TextView) view.findViewById(R.id.tvServiceName_LISL);
        }

        @Override
        protected void setData(int position, String data) {
            tvServiceName.setText(data);
        }
    }
}