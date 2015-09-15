package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.ServiceProvider;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mobimaks on 04.09.2015.
 */
public final class ServiceProviderAdapter extends android.widget.BaseAdapter {

    private final LayoutInflater mInflater;
    private List<ServiceProvider> mData;

    public ServiceProviderAdapter(final Context _context) {
        mInflater = LayoutInflater.from(_context);
        mData = Arrays.asList(ServiceProvider.values());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public ServiceProvider getItem(final int _position) {
        return mData.get(_position);
    }

    @Override
    public long getItemId(final int _position) {
        return _position;
    }

    @Override
    public View getView(final int _position, View _convertView, final ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.spinner_item_service_provider, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    @Override
    public View getDropDownView(int _position, View _convertView, ViewGroup _parent) {
        final ViewHolder holder;
        if (_convertView == null) {
            _convertView = mInflater.inflate(R.layout.spinner_dropdown_item_service_provider, _parent, false);
            holder = new ViewHolder(_convertView);
        } else {
            holder = (ViewHolder) _convertView.getTag();
        }
        holder.setData(getItem(_position));
        return _convertView;
    }

    private class ViewHolder {

        private final TextView tvTitle;

        public ViewHolder(final View _view) {
            tvTitle = (TextView) _view;
            _view.setTag(this);
        }

        public void setData(final ServiceProvider _provider) {
            tvTitle.setText(_provider.getTitleRes());
        }

    }
}
