package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.FavoritesAdapter;
import com.uae.tra_smart_services.adapter.FavoritesAdapter.OnFavoriteClickListener;
import com.uae.tra_smart_services.customviews.DragFrameLayout;
import com.uae.tra_smart_services.customviews.DragFrameLayout.OnItemDeleteListener;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.entities.FavoriteItem;
import com.uae.tra_smart_services.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesFragment extends BaseFragment
        implements OnFavoriteClickListener, OnItemDeleteListener, OnClickListener {

    private DragFrameLayout dflContainer;
    private RecyclerView rvFavoritesList;
    private RelativeLayout rlEmptyContainer;
    private HexagonView hvAddService;

    private FavoritesAdapter mFavoritesAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private OnFavoritesEventListener mFavoritesEventListener;

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        if (_activity instanceof OnFavoritesEventListener) {
            mFavoritesEventListener = (OnFavoritesEventListener) _activity;
        }
    }

    @Override
    protected void initViews() {
        super.initViews();
        rlEmptyContainer = findView(R.id.rlEmptyContainer_FF);
        hvAddService = findView(R.id.hvPlusBtn);
        dflContainer = findView(R.id.dflContainer_FF);
        rvFavoritesList = findView(R.id.rvFavoritesList_FF);
        setEmptyPlaceholderVisibility(true);
    }

    @Override
    protected void initListeners() {
        super.initListeners();
        dflContainer.setItemDeleteListener(this);
        hvAddService.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initFavoritesList();
    }

    private void initFavoritesList() {
        mFavoritesAdapter = new FavoritesAdapter(getActivity());
        mFavoritesAdapter.setFavoriteClickListener(this);
        rvFavoritesList.setHasFixedSize(true);
        rvFavoritesList.setAdapter(mFavoritesAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvFavoritesList.setLayoutManager(mLinearLayoutManager);
    }

    public final void addServicesToFavorites(final List<FavoriteItem> _items){
        mFavoritesAdapter.addData(_items);
        setEmptyPlaceholderVisibility(mFavoritesAdapter.isEmpty());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hvPlusBtn && mFavoritesEventListener != null) {
            mFavoritesEventListener.onAddFavoritesClick();
        }
    }

    @Override
    public void onItemClick(int _position) {
        Log.d(getClass().getSimpleName(), "Item click: " + _position);
    }

    @Override
    public void onServiceInfoClick(int _position) {
        if (mFavoritesEventListener != null) {
            mFavoritesEventListener.onOpenServiceInfo(_position, mFavoritesAdapter.getItem(_position));
        }
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
        setEmptyPlaceholderVisibility(mFavoritesAdapter.isEmpty());
    }

    @Override
    public final void onDetach() {
        mFavoritesEventListener = null;
        super.onDetach();
    }

    private void setEmptyPlaceholderVisibility(final boolean _isEmpty) {
        if (_isEmpty) {
            rlEmptyContainer.setVisibility(View.VISIBLE);
            rvFavoritesList.setVisibility(View.INVISIBLE);
        } else {
            rlEmptyContainer.setVisibility(View.INVISIBLE);
            rvFavoritesList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getTitle() {
        return R.string.str_favorites;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favorites;
    }

    public interface OnFavoritesEventListener {
        void onAddFavoritesClick();

        void onOpenServiceInfo(int _position, FavoriteItem _item);
    }
}
