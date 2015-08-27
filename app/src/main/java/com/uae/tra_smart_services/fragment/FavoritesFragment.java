package com.uae.tra_smart_services.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.FavoritesAdapter;
import com.uae.tra_smart_services.adapter.FavoritesAdapter.OnFavoriteClickListener;
import com.uae.tra_smart_services.customviews.DragFrameLayout;
import com.uae.tra_smart_services.customviews.DragFrameLayout.OnItemDeleteListener;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.fragment.base.BaseFragment;
import com.uae.tra_smart_services.global.Constants;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.util.ImageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesFragment extends BaseFragment
        implements OnFavoriteClickListener, OnItemDeleteListener, OnClickListener, OnQueryTextListener {

    private DragFrameLayout dflContainer;
    private RecyclerView rvFavoritesList;
    private RelativeLayout rlEmptyContainer;
    private HexagonView hvAddService;
    private SearchView svSearchFavorites;
    private ImageView ivBackground;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void initViews() {
        super.initViews();
        ivBackground = findView(R.id.ivBackground_FF);
        ivBackground.setImageDrawable(ImageUtils.getFilteredDrawable(getActivity(), R.drawable.res_bg_2));
        ivBackground.setImageResource(ImageUtils.isBlackAndWhiteMode(getActivity()) ? R.drawable.res_bg_2_gray : R.drawable.res_bg_2);
        rlEmptyContainer = findView(R.id.rlEmptyContainer_FF);
        hvAddService = findView(R.id.hvPlusBtn);
        hvAddService.setHexagonBackgroundDrawable(ImageUtils.getFilteredDrawable(getActivity(), R.drawable.ic_plus));
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
        final List<Service> favoriteServices = getFavoriteServices();
        mFavoritesAdapter = new FavoritesAdapter(getActivity(), favoriteServices);
        mFavoritesAdapter.setFavoriteClickListener(this);
        rvFavoritesList.setHasFixedSize(true);
        rvFavoritesList.setAdapter(mFavoritesAdapter);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvFavoritesList.setLayoutManager(mLinearLayoutManager);
        setEmptyPlaceholderVisibility(mFavoritesAdapter.isEmpty());
    }

    private List<Service> getFavoriteServices() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String favoriteServicesStr = prefs.getString(Constants.KEY_FAVORITE_SERVICES, "");
        final List<Service> services = new ArrayList<>();

        if (!favoriteServicesStr.isEmpty()) {
            final String[] servicesStrArray = favoriteServicesStr.split(Constants.FAVORITE_SERVICES_DELIMITER);
            for (String serviceStr : servicesStrArray) {
                try {
                    final Service service = Service.valueOf(serviceStr);
                    services.add(service);
                } catch (IllegalArgumentException e) {
                    //continue cycle
                }
            }
        }
        return services;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_favorites, menu);
        svSearchFavorites = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        svSearchFavorites.setOnQueryTextListener(this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        final boolean isItemsVisible = !mFavoritesAdapter.isEmpty();
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(isItemsVisible);

        final boolean isAllServicesAlreadyFavorite = mFavoritesAdapter.getAllData().size() == Service.getAllServicesCount();
        final MenuItem addItem = menu.findItem(R.id.action_add);
        addItem.setVisible(isItemsVisible && !isAllServicesAlreadyFavorite);
    }

    public final void addServicesToFavorites(final List<Service> _items) {
        Log.d("Favorites", "Items before new added: " + mFavoritesAdapter.getItemCount());
        mFavoritesAdapter.addData(_items);
        Log.d("Favorites", "Items after new added: " + mFavoritesAdapter.getItemCount());
        getActivity().invalidateOptionsMenu();
        saveFavoriteServices();
        setEmptyPlaceholderVisibility(mFavoritesAdapter.isEmpty());
    }

    private void saveFavoriteServices() {
        final List<Service> favoriteServices = mFavoritesAdapter.getAllData();
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        if (favoriteServices.isEmpty()) {
            editor.remove(Constants.KEY_FAVORITE_SERVICES).apply();
        } else {
            final int favoriteServicesCount = favoriteServices.size();
            final StringBuilder builder = new StringBuilder();
            for (int i = 0; i < favoriteServicesCount; i++) {
                builder.append(favoriteServices.get(i).name()).append(Constants.FAVORITE_SERVICES_DELIMITER);
            }
            final String servicesStr = builder.toString();
            editor.putString(Constants.KEY_FAVORITE_SERVICES, servicesStr).apply();
        }
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add && mFavoritesEventListener != null) {
            mFavoritesEventListener.onAddFavoritesClick();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mFavoritesAdapter.getFilter().filter(newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mFavoritesAdapter.getFilter().filter(query);
        return true;
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
            hideKeyboard(getView());
            mFavoritesEventListener.onOpenServiceInfo(_position, mFavoritesAdapter.getItem(_position));
        }
        Log.d(getClass().getSimpleName(), "Service info click: " + _position);
    }

    @Override
    public void onRemoveLongClick(View _view, int _position) {
        hideKeyboard(_view);
        ClipData data = ClipData.newPlainText("Item remove", "" + _position);
        View itemView = mLinearLayoutManager.findViewByPosition(_position);
        itemView.setTag(_view);
        dflContainer.starDragChild(itemView, data);
    }

    @Override
    public void onDeleteItem(int _position) {
        mFavoritesAdapter.removeItem(_position);
        Log.d("Favorites", "Items after delete operation: " + mFavoritesAdapter.getItemCount());
        getActivity().invalidateOptionsMenu();
        saveFavoriteServices();
        if (mFavoritesAdapter.isEmpty()) {
            setEmptyPlaceholderVisibility(true);
        }
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

        void onOpenServiceInfo(int _position, Service _item);
    }
}
