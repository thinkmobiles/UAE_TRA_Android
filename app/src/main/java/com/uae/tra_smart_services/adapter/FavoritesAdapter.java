package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
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
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.global.Service;
import com.uae.tra_smart_services.util.ImageUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesAdapter extends Adapter<ViewHolder> implements Filterable {

    @IntDef({HEADER_ITEM, SIMPLE_ITEM})
    @Retention(RetentionPolicy.SOURCE)
    private @interface ItemType {
    }

    private static final int HEADER_ITEM = 0;
    private static final int SIMPLE_ITEM = 1;

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
        notifyItemRemoved(_position + 1);
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
        if (viewType == SIMPLE_ITEM) {
            return new ItemViewHolder(mInflater.inflate(R.layout.list_item_favorite, parent, false));
        } else {
            return new HeaderViewHolder(mInflater.inflate(R.layout.layout_add_service, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == SIMPLE_ITEM) {
            ((ItemViewHolder) holder).setData(mShowingData.get(position - 1), position - 1);
        }
    }

    @ItemType
    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER_ITEM : SIMPLE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mShowingData.size() + 1;
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

    protected class ItemViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {

        private final View rootView;
        private final View rlItemContainer;
        private final HexagonView hvIcon;
        private final TextView tvTitle, tvServiceInfo, tvRemove;

        public ItemViewHolder(final View _itemView) {
            super(_itemView);
            rootView = _itemView;
            rlItemContainer = _itemView.findViewById(R.id.rlItemContainer_LIF);
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
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? Color.TRANSPARENT : mBackgroundColor);
            } else {
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? mBackgroundColor : Color.TRANSPARENT);
            }
            tvTitle.setText(_item.getTitleRes());
            hvIcon.setHexagonSrcDrawable(_item.getDrawableRes());
        }

        @Override
        public void onClick(View v) {
            if (mFavoriteClickListener != null) {
                switch (v.getId()) {
                    case R.id.rlItemContainer_LIF:
                        mFavoriteClickListener.onItemClick(getAdapterPosition() - 1);
                        break;
                    case R.id.tvServiceInfo_LIF:
                        mFavoriteClickListener.onServiceInfoClick(getAdapterPosition() - 1);
                        break;
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mFavoriteClickListener != null) {
                mFavoriteClickListener.onRemoveLongClick(tvRemove, getAdapterPosition() - 1);
            }
            return true;
        }
    }

    protected class HeaderViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private HexagonView hvIcon;

        public HeaderViewHolder(final View _itemView) {
            super(_itemView);
            hvIcon = (HexagonView) _itemView.findViewById(R.id.hvIcon_LAS);
            hvIcon.setHexagonSrcDrawable(ImageUtils.getFilteredDrawableByTheme(hvIcon.getContext(), R.drawable.ic_add_service_plus, R.attr.themeMainColor));
            _itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View _view) {
            if (mFavoriteClickListener != null) {
                mFavoriteClickListener.onAddServiceClick();
            }
        }
    }

    public interface OnFavoriteClickListener {
        void onAddServiceClick();

        void onItemClick(final int _position);

        void onServiceInfoClick(final int _position);

        void onRemoveLongClick(final View _view, final int _position);
    }

}
