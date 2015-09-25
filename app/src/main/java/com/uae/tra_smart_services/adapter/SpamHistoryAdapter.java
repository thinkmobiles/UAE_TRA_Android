package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.SpamHistoryAdapter.ViewHolder;
import com.uae.tra_smart_services.customviews.HexagonView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mobimaks on 24.09.2015.
 */
public final class SpamHistoryAdapter extends RecyclerView.Adapter<ViewHolder> {

    private final LayoutInflater mInflater;
    private final List<String> mData;
    private boolean mEvenOffset;

    private OnDeleteClickListener mDeleteClickListener;

    public SpamHistoryAdapter(final @NonNull Context _context) {
        this(_context, new ArrayList<String>());
    }

    public SpamHistoryAdapter(final @NonNull Context _context, final @NonNull List<String> _data) {
        mInflater = LayoutInflater.from(_context);
        mData = _data;
    }

    public void addData(final @NonNull List<String> _data) {
        int oldDataSize = mData.size();
        mData.addAll(_data);
        notifyItemRangeInserted(oldDataSize, _data.size());
    }

    public void deleteItem(final int _position) {
        mData.remove(_position);
        mEvenOffset = !mEvenOffset;
        notifyItemRemoved(_position);
    }

    public void setDeleteClickListener(final OnDeleteClickListener _deleteClickListener) {
        mDeleteClickListener = _deleteClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_spam_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        protected static final int ODD_MARGIN = 20;
        protected static final int EVEN_MARGIN = 36;

        private HexagonView hvIcon;
        private TextView tvTitle, tvDescription;
        private ImageView ivDelete;

        public ViewHolder(final View _itemView) {
            super(_itemView);
            hvIcon = (HexagonView) _itemView.findViewById(R.id.hvIcon_LISH);
            tvTitle = (TextView) _itemView.findViewById(R.id.tvTitle_LISH);
            tvDescription = (TextView) _itemView.findViewById(R.id.tvDescription_LISH);
            ivDelete = (ImageView) _itemView.findViewById(R.id.ivDelete_LISH);
            ivDelete.setOnClickListener(this);
        }

        public void setData(final String _data, final int _position) {
            initMargins(_position);
            tvTitle.setText(_data);
        }

        private void initMargins(final int _position) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) hvIcon.getLayoutParams();
            if (layoutParams != null) {
                final float marginStart;
                if (mEvenOffset) {
                    marginStart = (_position % 2 == 0 ? EVEN_MARGIN : ODD_MARGIN) * hvIcon.getResources().getDisplayMetrics().density;
                } else {
                    marginStart = (_position % 2 == 0 ? ODD_MARGIN : EVEN_MARGIN) * hvIcon.getResources().getDisplayMetrics().density;
                }
                layoutParams.setMarginStart(Math.round(marginStart));
                hvIcon.setLayoutParams(layoutParams);
            }
        }

        @Override
        public final void onClick(final View _view) {
            if (mDeleteClickListener != null) {
                final int position = getAdapterPosition();
                mDeleteClickListener.onDeleteClick(mData.get(position), position);
            }
        }
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(final String _data, final int _position);
    }

}
