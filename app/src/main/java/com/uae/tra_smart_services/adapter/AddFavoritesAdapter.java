package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.AddFavoritesAdapter.ViewHolder;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.entities.FavoriteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class AddFavoritesAdapter extends Adapter<ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<FavoriteItem> mData;
    private final SparseBooleanArray mSelectedItems;
    private final int mBackgroundColor;

    private OnItemClickListener mItemClickListener;

    public AddFavoritesAdapter(final Context _context) {
        this(_context, new ArrayList<FavoriteItem>());
    }

    public AddFavoritesAdapter(final Context _context, final @NonNull List<FavoriteItem> _data) {
        mInflater = LayoutInflater.from(_context);
        mData = _data;
        mSelectedItems = new SparseBooleanArray();

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = _context.getTheme();
        theme.resolveAttribute(R.attr.shadowBackgroundColor, typedValue, true);
        mBackgroundColor = typedValue.data;
    }

    public final SparseBooleanArray getSelectedItems() {
        return mSelectedItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_add_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public final void setItemClickListener(OnItemClickListener _itemClickListener) {
        mItemClickListener = _itemClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private final View vContainer;
        private final HexagonView hvIcon;
        private final CheckBox cbSelection;
        private final TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            vContainer = itemView;
            hvIcon = (HexagonView) itemView.findViewById(R.id.hvIcon_LIAF);
            cbSelection = (CheckBox) itemView.findViewById(R.id.cbSelection_LIAF);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle_LIAF);
            itemView.setOnClickListener(this);
        }

        public final void setData(final FavoriteItem _item, final int _position) {
            vContainer.setBackgroundColor(_position % 2 == 0 ? Color.TRANSPARENT : mBackgroundColor);
            cbSelection.setChecked(mSelectedItems.get(_position));
            tvTitle.setText(_item.name);
            hvIcon.setHexagonBackgroundDrawable(_item.backgroundDrawableRes);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            final boolean isAlreadySelected = mSelectedItems.get(position);
            mSelectedItems.put(position, !isAlreadySelected);
            notifyItemChanged(position);
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(mData.get(position), !isAlreadySelected);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FavoriteItem _item, boolean isSelected);
    }

}
