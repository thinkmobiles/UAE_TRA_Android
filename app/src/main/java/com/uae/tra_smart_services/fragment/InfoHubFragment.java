package com.uae.tra_smart_services.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubAnnListAdapter;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.rest.model.new_response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubFragment extends BaseFragment
        implements InfoHubAnnListAdapter.OnInfoHubItemClickListener{
    private static final ArrayList<InfoHubAnnouncementsListItemModel> DUMMY_LIST = new ArrayList<InfoHubAnnouncementsListItemModel>(){
        {
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("One Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Two Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Three Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Four Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Five Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Six Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Seven Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Dummy description")
                    .setDate("12.08.2015, 20:12"));
            add(new InfoHubAnnouncementsListItemModel()
                    .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                    .setDescription("Dummy description")
                    .setDate("12.08.2015, 20:12"));
        }
    };

    public static InfoHubFragment newInstance() {
        return new InfoHubFragment();
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

    private RecyclerView infoHubList;
    private RecyclerView.LayoutManager mLayoutManager;
    private InfoHubAnnListAdapter mAdapter;
    @Override
    protected void initViews() {
        super.initViews();
        infoHubList = findView(R.id.rvInfoHubAnnList_FIHA);
        infoHubList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        infoHubList.setLayoutManager(mLayoutManager);
        mAdapter = new InfoHubAnnListAdapter(getActivity(), DUMMY_LIST);
        mAdapter.setOnItemClickListener(this);
        infoHubList.setAdapter(mAdapter);
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub_announcements;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }

    @Override
    public void onItemSelected(InfoHubAnnouncementsListItemModel item) {
        // TODO need to be implemented
        Toast.makeText(getActivity(), item.getDescription(), Toast.LENGTH_LONG).show();
    }

}
