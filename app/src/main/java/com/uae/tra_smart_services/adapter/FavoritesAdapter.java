package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.FavoritesAdapter.ViewHolder;
import com.uae.tra_smart_services.customviews.HexagonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 18.08.2015.
 */
public class FavoritesAdapter extends Adapter<ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<String> mData;
    private OnFavoriteClickListener mFavoriteClickListener;
    private boolean mIsOddOpaque;

    public FavoritesAdapter(final Context _context) {
        this(_context, new ArrayList<String>());
    }

    public FavoritesAdapter(final Context _context, final @NonNull List<String> _data) {
        mInflater = LayoutInflater.from(_context);
        mData = _data;
    }

    public final void removeItem(int _position) {
        mData.remove(_position);
        mIsOddOpaque = !mIsOddOpaque;
        notifyItemRemoved(_position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setFavoriteClickListener(OnFavoriteClickListener _favoriteClickListener) {
        mFavoriteClickListener = _favoriteClickListener;
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

        public final void setData(final String _title, final int _position) {
            rootView.setVisibility(View.VISIBLE);
            if (mIsOddOpaque) {
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? Color.TRANSPARENT : Color.parseColor("#fff5ea"));
            } else {
                rlItemContainer.setBackgroundColor(_position % 2 == 0 ? Color.parseColor("#fff5ea") : Color.TRANSPARENT);
            }
            tvTitle.setText(_title);
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
