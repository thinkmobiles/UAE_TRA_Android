package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubAnnFullListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.C;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnouncementsFragment extends BaseFragment
        implements OnInfoHubItemClickListener<InfoHubAnnouncementsListItemModel> {

    //    private ImageView ivBackground;
    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutManager;

    private InfoHubAnnFullListAdapter mAdapter;

    public static InfoHubAnnouncementsFragment newInstance() {
        return new InfoHubAnnouncementsFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        mAdapter.getFilter().initFromAdapter(mAdapter);
    }

    private ArrayList<InfoHubAnnouncementsListItemModel> DUMMY_ANNOUNCEMENTS_LIST;
    public static transient final String ICON_URL = "https://pbs.twimg.com/profile_images/552335001539207168/ToYKIO0y_bigger.jpeg";

    @Override
    protected void initData() {
        super.initData();
        final Resources res = getResources();
        final TypedArray icons = res.obtainTypedArray(R.array.news_image_url);
        final TypedArray dates = res.obtainTypedArray(R.array.news_date);
        final TypedArray titles = res.obtainTypedArray(R.array.news_titles);
        final TypedArray details = res.obtainTypedArray(R.array.news_details);

        DUMMY_ANNOUNCEMENTS_LIST = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            InfoHubAnnouncementsListItemModel model = new InfoHubAnnouncementsListItemModel();
            model.setIconUrl(icons.getString(i));
            model.setHeaderImageUrl(model.getIconUrl());
            model.setDescription(titles.getString(i));
            model.setFullDescription(details.getString(i));
            model.setDate(dates.getString(i));
            DUMMY_ANNOUNCEMENTS_LIST.add(model);
        }

        icons.recycle();
        dates.recycle();
        titles.recycle();
        details.recycle();
//        DUMMY_ANNOUNCEMENTS_LIST = new ArrayList<InfoHubAnnouncementsListItemModel>() {
//            {
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl("fail" + ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl("fail" + ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//                add(new InfoHubAnnouncementsListItemModel()
//                        .setIconUrl(ICON_URL)
//                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
//                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
//                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
//                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
//            }
//        };
    }

    @Override
    protected void initViews() {
        super.initViews();
//        ivBackground = findView(R.id.ivBackground_FIHA);
//        ivBackground.setImageResource(ImageUtils.isBlackAndWhiteMode(getActivity()) ? R.drawable.res_bg_2_gray : R.drawable.res_bg_2);
        mList = findView(R.id.rvInfoHubAnnList_FIHA);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(mLayoutManager);
        mAdapter = new InfoHubAnnFullListAdapter(getActivity(), DUMMY_ANNOUNCEMENTS_LIST);
        mAdapter.setOnItemClickListener(this);
        mList.setAdapter(mAdapter);
    }

    @Override
    public void onItemSelected(InfoHubAnnouncementsListItemModel item) {
        Bundle args = new Bundle();
        args.putParcelable(C.INFO_HUB_ANN_DATA, item);
        getFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.flContainer_AH, InfoHubDetailsFragment.newInstance(args))
                .commit();
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_announcements;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub_announcements;
    }
}