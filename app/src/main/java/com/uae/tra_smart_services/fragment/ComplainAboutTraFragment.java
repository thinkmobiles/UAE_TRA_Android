package com.uae.tra_smart_services.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.fragment.base.BaseComplainFragment;

/**
 * Created by mobimaks on 11.08.2015.
 */
public final class ComplainAboutTraFragment extends BaseComplainFragment implements OnClickListener {

    private ImageView ivAddAttachment;
    private EditText etComplainTitle, etDescription;

    public static ComplainAboutTraFragment newInstance() {
        return new ComplainAboutTraFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivAddAttachment = findView(R.id.ivAddAttachment_FCAT);

        etComplainTitle = findView(R.id.etComplainTitle_FCAT);
        etDescription = findView(R.id.etDescription_FCAT);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        ivAddAttachment.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getToolbarTitleManager().setTitle(R.string.complain);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(v);
        switch (v.getId()) {
            case R.id.ivAddAttachment_FCAT:
                openImagePicker();
                break;
        }
    }

    @Override
    protected void sendComplain() {
        Toast.makeText(getActivity(), "Send", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_complain_about_tra;
    }

}
