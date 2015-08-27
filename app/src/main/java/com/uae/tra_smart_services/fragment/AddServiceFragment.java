package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.AddFavoritesAdapter;
import com.uae.tra_smart_services.adapter.AddFavoritesAdapter.OnItemClickListener;
import com.uae.tra_smart_services.entities.FavoriteItem;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 22.08.2015.
 */
public class AddServiceFragment extends BaseFragment implements OnItemClickListener {

    public static AddServiceFragment newInstance() {
        return new AddServiceFragment();
    }

    private RecyclerView rvFavoritesList;
    private ImageView ivBackground;

    private AddFavoritesAdapter mFavoritesAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private List<FavoriteItem> mSelectedItems;
    private OnFavoriteServicesSelectedListener mServicesSelectedListener;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnFavoriteServicesSelectedListener) {
            mServicesSelectedListener = (OnFavoriteServicesSelectedListener) _activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mSelectedItems = new ArrayList<>();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_done, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done && mServicesSelectedListener != null) {
            Log.d("Favorites", "Size: " + mFavoritesAdapter.getItemCount() + ", selected: " + mSelectedItems.size());
            mServicesSelectedListener.onFavoriteServicesSelected(mSelectedItems);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivBackground = findView(R.id.ivBackground_FAS);
        ivBackground.setImageResource(ImageUtils.isBlackAndWhiteMode(getActivity()) ? R.drawable.res_bg_2_gray : R.drawable.res_bg_2);
        rvFavoritesList = findView(R.id.rvFavoritesList_FAS);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFavoritesList();
    }

    private void initFavoritesList() {
        final int size = 5;
        final List<FavoriteItem> data = new ArrayList<>();
        final String[] names = new String[]{
                "Addressing consumer disputes request with licensees on telecommunication services",
                "Broadband Speed Test",
                "Cancel spectrum authorization for aerial and marin radio services",
                "Complain about service provider",
                "Complain about service provider"
        };
        final int[] backgrounds = new int[]{
                R.drawable.background_dispute,
                R.drawable.background_speed,
                R.drawable.background_location,
                R.drawable.background_complain,
                R.drawable.background_complain
        };
        for (int i = 0; i < size; i++) {
            FavoriteItem item = new FavoriteItem();
            item.name = names[i];
            item.backgroundDrawableRes = backgrounds[i];
            data.add(item);
        }
        mFavoritesAdapter = new AddFavoritesAdapter(getActivity(), data);
        mFavoritesAdapter.setItemClickListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        rvFavoritesList.setHasFixedSize(true);
        rvFavoritesList.setAdapter(mFavoritesAdapter);
        rvFavoritesList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onItemClick(FavoriteItem _item, boolean isSelected) {
        if (isSelected) {
            mSelectedItems.add(_item);
        } else {
            mSelectedItems.remove(_item);
        }
    }


    @Override
    public void onDetach() {
        mServicesSelectedListener = null;
        super.onDetach();
    }

    @Override
    protected int getTitle() {
        return 0;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_add_service;
    }

    public interface OnFavoriteServicesSelectedListener {
        void onFavoriteServicesSelected(List<FavoriteItem> _items);
    }
}
