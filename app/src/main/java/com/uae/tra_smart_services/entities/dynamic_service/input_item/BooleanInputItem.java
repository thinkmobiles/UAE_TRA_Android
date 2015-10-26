package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;

/**
 * Created by mobimaks on 20.10.2015.
 */

public class BooleanInputItem extends BaseInputItem implements OnClickListener {

    private RelativeLayout rlContainer;
    private TextView tvText;
    private SwitchCompat swSwitch;

    protected BooleanInputItem() {
    }

    @Override
    protected void initViews() {
        super.initViews();
        rlContainer = findView(R.id.rlContainer_IIB);
        tvText = findView(R.id.tvText_IIB);
        tvText.setText(getPlaceholder());

        swSwitch = findView(R.id.swSwitch_IIB);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        rlContainer.setOnClickListener(this);
    }

    @Override
    public final void onClick(final View _view) {
        swSwitch.toggle();
    }

    @Override
    public boolean isDataValid() {
        return true;
    }

    @NonNull
    @Override
    public final JsonElement getJsonData() {
        final JsonObject element = new JsonObject();
        element.addProperty(getQueryName(), swSwitch.isChecked());
        return element;
    }

    @NonNull
    @Override
    public final String getArgsData() {
        return String.valueOf(swSwitch.isChecked());
    }

    public static class Builder extends BaseBuilder<BooleanInputItem> {

        @Override
        protected BooleanInputItem getInstance() {
            return new BooleanInputItem();
        }

    }

    @Override
    protected final int getLayoutRes() {
        return R.layout.input_item_boolean;
    }

}
