package com.uae.tra_smart_services.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubAnnPreviewListAdapter;
import com.uae.tra_smart_services.adapter.InfoHubTransactionsListAdapter;
import com.uae.tra_smart_services.entities.C;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.response.InfoHubAnnouncementsListItemModel;
import com.uae.tra_smart_services.rest.model.response.InfoHubTransActionsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public class InfoHubFragment extends BaseFragment
                            implements TextView.OnClickListener {
    public static InfoHubFragment newInstance() {
        return new InfoHubFragment();
    }

    @Override
    protected int getTitle() {
        return R.string.str_info_hub;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_info_hub;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private ArrayList<InfoHubAnnouncementsListItemModel> DUMMY_ANNOUNCEMENTS_LIST;
    private ArrayList<InfoHubTransActionsListItemModel> DUMMY_TRANSACTIONS_LIST;
    @Override
    protected void initData() {
        super.initData();
        DUMMY_ANNOUNCEMENTS_LIST = new ArrayList<InfoHubAnnouncementsListItemModel>(){
            {
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubAnnouncementsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
            }
        };
        DUMMY_TRANSACTIONS_LIST = new ArrayList<InfoHubTransActionsListItemModel>(){
            {
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
                add(new InfoHubTransActionsListItemModel()
                        .setIconUrl("http://www.socialsciencespace.com/wp-content/uploads/Speak-Dummy.jpg")
                        .setTitle(getString(R.string.str_dummy_info_hub_list_item_title, ""))
                        .setDescription(getString(R.string.str_dummy_info_hub_list_item_descr, ""))
                        .setDate(getString(R.string.str_dummy_info_hub_list_item_date, "")));
            }
        };
    }
    private TextView seeMoreAnnouncements;
    @Override
    protected void initViews() {
        super.initViews();
        seeMoreAnnouncements = findView(R.id.tvSeeMorebAnn_FIH);
        initAnnouncementsListPreview();
        initTransactionsList();
    }

    private RecyclerView mAnnouncementsListPreview;
    private RecyclerView.LayoutManager mAnnouncementsLayoutManager;
    private InfoHubAnnPreviewListAdapter mAnnouncementsListPreviewAdapter;
    private void initAnnouncementsListPreview(){
        mAnnouncementsListPreview = findView(R.id.rvInfoHubListPrev_FIH);
        mAnnouncementsListPreview.setHasFixedSize(true);
        mAnnouncementsLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mAnnouncementsListPreview.setLayoutManager(mAnnouncementsLayoutManager);
        mAnnouncementsListPreviewAdapter = new InfoHubAnnPreviewListAdapter(getActivity(), DUMMY_ANNOUNCEMENTS_LIST);
        mAnnouncementsListPreview.setAdapter(mAnnouncementsListPreviewAdapter);
    }

    private RecyclerView mTransactionsList;
    private RecyclerView.LayoutManager mTransactionsLayoutManager;
    private InfoHubTransactionsListAdapter mTransactionsListAdapter;
    private void initTransactionsList(){
        mTransactionsList = findView(R.id.rvTransactionsList_FIH);
        mTransactionsList.setHasFixedSize(true);
        mTransactionsLayoutManager = new LinearLayoutManager(getActivity());
        mTransactionsList.setLayoutManager(mTransactionsLayoutManager);
        mTransactionsListAdapter = new InfoHubTransactionsListAdapter(getActivity(), DUMMY_TRANSACTIONS_LIST);
        mTransactionsList.setAdapter(mTransactionsListAdapter);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        seeMoreAnnouncements.setOnClickListener(this);
        mAnnouncementsListPreviewAdapter.setOnItemClickListener(new OnInfoHubItemClickListener<InfoHubAnnouncementsListItemModel>() {
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
        });
        mTransactionsListAdapter.setOnItemClickListener(new OnInfoHubItemClickListener<InfoHubTransActionsListItemModel>() {
            @Override
            public void onItemSelected(InfoHubTransActionsListItemModel item) {
                Bundle args = new Bundle();
                args.putParcelable(C.INFO_HUB_ANN_DATA, item);
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.flContainer_AH, InfoHubDetailsFragment.newInstance(args))
                        .commit();
            }
        });
    }

    @Override
    public void onClick(View _view) {
        switch (_view.getId()){
            case R.id.tvSeeMorebAnn_FIH:
                getFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.flContainer_AH, InfoHubAnnouncementsFragment.newInstance())
                        .commit();
                break;
        }
    }
}