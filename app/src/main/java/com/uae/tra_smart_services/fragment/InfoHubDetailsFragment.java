package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.ImageViewTarget;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.rest.model.response.InfoHubListItemModel;


/**
 * Created by ak-buffalo on 20.08.15.
 */
public class InfoHubDetailsFragment extends BaseFragment {

    private ImageView headerImage;
    private TextView headerDate;
    private TextView headerTitle;
    private TextView bodyFullDescription;

    private InfoHubListItemModel infoHubAnnModel;

    public static InfoHubDetailsFragment newInstance(Bundle _bundle) {
        InfoHubDetailsFragment fragment = new InfoHubDetailsFragment();
        fragment.setArguments(_bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        infoHubAnnModel = getArguments().getParcelable(C.INFO_HUB_ANN_DATA);
    }

    @Override
    protected void initViews() {
        headerImage = findView(R.id.tvHeaderImage_FIHD);
        Picasso.with(getActivity()).load(infoHubAnnModel.getHeaderImageUrl()).placeholder(R.drawable.logo).into(new ImageViewTarget(headerImage));
        headerDate = findView(R.id.tvHeaderDate_FIHD);
        headerDate.setText(infoHubAnnModel.getDate());
        headerTitle = findView(R.id.tvHeaderTitle_FIHD);
        headerTitle.setText(infoHubAnnModel.getDescription());
        bodyFullDescription = findView(R.id.tvBodyDescr_FIHD);
        bodyFullDescription.setText(infoHubAnnModel.getFullDescription());
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_announcement_details;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub_announcement_details;
    }
}