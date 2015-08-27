package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.FavoritesAdapter.ViewHolder;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.global.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesAdapter extends Adapter<ViewHolder> implements Filterable {

    private final LayoutInflater mInflater;
    private final List<Service> mAllData, mShowingData;
    private final int mBackgroundColor;
    private final Context mContext;
    private OnFavoriteClickListener mFavoriteClickListener;
    private Filter mServiceFilter;
    private boolean mIsOddOpaque;

    public FavoritesAdapter(final Context _context) {
        this(_context, new ArrayList<Service>());
    }

    public FavoritesAdapter(final Context _context, final @NonNull List<Service> _data) {
        mContext = _context;
        mInflater = LayoutInflater.from(_context);
        mAllData = _data;
        mShowingData = new ArrayList<>(_data);

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = _context.getTheme();
        theme.resolveAttribute(R.attr.shadowBackgroundColor, typedValue, true);
        mBackgroundColor = typedValue.data;
    }

    public final List<Service> getAllData() {
        return mAllData;
    }

    public final void setData(List<Service> _data) {
        mAllData.clear();
        mAllData.addAll(_data);
        invalidateFilter();
        showData(mAllData);
    }

    private void showData(final List<Service> _data) {
        mShowingData.clear();
        mShowingData.addAll(_data);
        notifyDataSetChanged();
    }

    public final boolean isNoVisibleItems() {
        return mShowingData.isEmpty();
    }

    public final boolean isEmpty() {
        return mAllData.isEmpty();
    }

    public final void addData(final List<Service> _items) {
        mAllData.addAll(_items);
        invalidateFilter();
        showData(mAllData);
    }

    public final void removeItem(final int _position) {
        mAllData.remove(mShowingData.get(_position));
        mShowingData.remove(_position);
        mIsOddOpaque = !mIsOddOpaque;
        invalidateFilter();
        notifyItemRemoved(_position);
    }

    private void invalidateFilter() {
        mServiceFilter = null;
    }

    @Override
    public Filter getFilter() {
        if (mServiceFilter == null) {
            mServiceFilter = new SearchFilter(mAllData);
        }
        return mServiceFilter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mShowingData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mShowingData.size();
    }

    public void setFavoriteClickListener(OnFavoriteClickListener _favoriteClickListener) {
        mFavoriteClickListener = _favoriteClickListener;
    }

    public Service getItem(int _position) {
        return mShowingData.get(_position);
    }

    private class SearchFilter extends Filter {

        private final List<Service> listData;

        public SearchFilter(List<Service> _listData) {
            listData = new ArrayList<>(_listData);
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();
            if (TextUtils.isEmpty(constraint)) {
                results.count = listData.size();
                results.values = listData;
            } else {
                List<Service> filteredList = getFilteredList(constraint);
                results.count = filteredList.size();
                results.values = filteredList;
            }
            return results;
        }

        private List<Service> getFilteredList(CharSequence _constraint) {
            List<Service> serviceList = new ArrayList<>();
            for (int i = 0; i < listData.size(); i++) {
                Service favoriteItem = listData.get(i);
                if (favoriteItem.getTitle(mContext).toLowerCase().contains(_constraint.toString().toLowerCase())) {
                    serviceList.add(favoriteItem);
                }
            }
            return serviceList;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Service> filteredData = (List<Service>) results.values;
            showData(filteredData);
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {

        private final View rootView;
        private final View rlItemContainer;
        private final HexagonView hvIcon;
        private final TextView tvTitle, tvServiceInfo, tvRemove;

        public ViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            rlItemContainer = itemView.findViewById(R.id.rlItemContainer_LIF);
            hvIcon = (HexagonView) rlItemContainer.findViewById(R.id.hvIcon_LIF);
            tvTitle = (TextView) rlItemContainer.findViewById(R.id.tvTitle_LIF);
            tvServiceInfo = (TextView) rlItemContainer.findViewById(R.id.tvServiceInfo_LIF);
            tvRemove = (TextView) rlItemContainer.findViewById(R.id.tvRemove_LIF);

            rlItemContainer.setOnClickListener(this);
            tvServiceInfo.setOnClickListener(this);
            tvRemove.setOnLongClickListener(this);
        }

        public final void setData(final Service _item, final int _position) {
            rootView.setVisibility(View.VISIBLE);
            if (mIsOddOpaque) {
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? mBackgroundColor : Color.TRANSPARENT);
            } else {
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? Color.TRANSPARENT : mBackgroundColor);
            }
            tvTitle.setText(_item.getTitleRes());
            hvIcon.setHexagonSrcDrawable(_item.getDrawableRes());
        }

        @Override
        public void onClick(View v) {
            if (mFavoriteClickListener != null) {
                switch (v.getId()) {
                    case R.id.rlItemContainer_LIF:
                        mFavoriteClickListener.onItemClick(getAdapterPosition());
                        break;
                    case R.id.tvServiceInfo_LIF:
                        mFavoriteClickListener.onServiceInfoClick(getAdapterPosition());
                        break;
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mFavoriteClickListener != null) {
                mFavoriteClickListener.onRemoveLongClick(tvRemove, getAdapterPosition());
            }
            return true;
        }
    }

    public interface OnFavoriteClickListener {
        void onItemClick(final int _position);

        void onServiceInfoClick(final int _position);

        void onRemoveLongClick(final View _view, final int _position);
    }

}
