package com.uae.tra_smart_services.fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.FavoritesAdapter;
import com.uae.tra_smart_services.adapter.FavoritesAdapter.OnFavoriteClickListener;
import com.uae.tra_smart_services.customviews.DragFrameLayout;
import com.uae.tra_smart_services.customviews.DragFrameLayout.OnItemDeleteListener;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesFragment extends BaseFragment implements OnFavoriteClickListener, OnItemDeleteListener {

    private DragFrameLayout dflContainer;
    private RecyclerView rvFavoritesList;

    private FavoritesAdapter mFavoritesAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    protected void initViews() {
        super.initViews();
        dflContainer = findView(R.id.dflContainer_FF);
        rvFavoritesList = findView(R.id.rvFavoritesList_FF);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        dflContainer.setItemDeleteListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFavoritesList();
    }

    private void initFavoritesList() {
        final int size = 20;
        final List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("Text #" + i);
        }
        mFavoritesAdapter = new FavoritesAdapter(getActivity(), data);
        mFavoritesAdapter.setFavoriteClickListener(this);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());

        rvFavoritesList.setHasFixedSize(true);
        rvFavoritesList.setAdapter(mFavoritesAdapter);
        rvFavoritesList.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onItemClick(int _position) {
        Log.d(getClass().getSimpleName(), "Item click: " + _position);
    }

    @Override
    public void onServiceInfoClick(int _position) {
        Log.d(getClass().getSimpleName(), "Service info click: " + _position);
    }

    @Override
    public void onRemoveLongClick(View _view, int _position) {
        ClipData data = ClipData.newPlainText("Item remove", "" + _position);
        View itemView = mLinearLayoutManager.findViewByPosition(_position);
        itemView.setTag(_view);
        dflContainer.starDragChild(itemView, data);
    }

    @Override
    public void onDeleteItem(int _position) {
        mFavoritesAdapter.removeItem(_position);
    }

    @Override
    protected int getTitle() {
        return R.string.str_favorites;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favorites;
    }
}
