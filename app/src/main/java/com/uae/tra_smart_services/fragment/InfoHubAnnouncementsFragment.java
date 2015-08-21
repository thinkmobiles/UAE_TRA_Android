package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubAnnFullListAdapter;
import com.uae.tra_smart_services.entities.C;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnouncementsFragment extends BaseFragment
                                        implements OnInfoHubItemClickListener<InfoHubAnnouncementsListItemModel> {

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
    }

    public static InfoHubAnnouncementsFragment newInstance() {
        return new InfoHubAnnouncementsFragment();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private ArrayList<InfoHubAnnouncementsListItemModel> DUMMY_ANNOUNCEMENTS_LIST;
    @Override
    protected void initData() {
        super.initData();
        DUMMY_ANNOUNCEMENTS_LIST = new ArrayList<InfoHubAnnouncementsListItemModel>(){
            {
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setHeaderImageUrl("http://www.tra.gov.ae/images/tra-logo.png")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setFullDescription(getString(R.string.str_dummy_info_hub_list_item_full_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
            }
        };
    }

    private RecyclerView mList;
    private RecyclerView.LayoutManager mLayoutManager;
    private InfoHubAnnFullListAdapter mAdapter;
    @Override
    protected void initViews() {
        super.initViews();
        mList = findView(R.id.rvInfoHubAnnList_FIHA);
        mList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(mLayoutManager);
        mAdapter = new InfoHubAnnFullListAdapter(getActivity(), DUMMY_ANNOUNCEMENTS_LIST);
        mAdapter.setOnItemClickListener(this);
        mList.setAdapter(mAdapter);
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_announcements;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub_announcements;
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
}