package com.uae.tra_smart_services.entities.dynamic_service.input_item;

import android.widget.EditText;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.dynamic_service.BaseInputItem;

/**
 * Created by mobimaks on 22.10.2015.
 */
public class StringInputItem extends BaseInputItem {

    private EditText etEditText;

    protected StringInputItem() {
        super();
    }

    @Override
    protected final void initViews() {
        super.initViews();
        etEditText = findView(R.id.etEdit_IIT);
        etEditText.setHint(getPlaceholder());
    }

    public static final class Builder extends BaseBuilder {

        @Override
        protected BaseInputItem getInstance() {
            return new StringInputItem();
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.input_item_string;
    }

}
