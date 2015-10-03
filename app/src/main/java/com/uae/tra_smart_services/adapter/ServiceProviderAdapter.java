package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.global.ServiceProvider;

import java.util.Arrays;

/**
 * Created by mobimaks on 04.09.2015.
 */
public class ServiceProviderAdapter extends BaseSpinnerAdapter<ServiceProvider> {

    public ServiceProviderAdapter(final Context _context) {
        super(_context, Arrays.asList(ServiceProvider.values()));
    }

    protected ViewHolder<ServiceProvider> getViewHolder(View _view) {
        return new ServiceViewHolder(_view);
    }

    protected ViewHolder<ServiceProvider> getDropDownViewHolder(View _view) {
        return new ServiceViewHolder(_view);
    }

    @LayoutRes
    protected int getItemLayoutRes() {
        return R.layout.spinner_item_service_provider;
    }

    @Override
    protected int getDropdownLayoutRes() {
        return R.layout.spinner_dropdown_item_service_provider;
    }

    private class ServiceViewHolder extends ViewHolder<ServiceProvider> {

        private final TextView tvTitle;

        public ServiceViewHolder(final View _view) {
            super(_view);
            tvTitle = (TextView) _view;
        }

        public void setData(final ServiceProvider _provider) {
            tvTitle.setText(_provider.getTitleRes());
        }

    }
}
