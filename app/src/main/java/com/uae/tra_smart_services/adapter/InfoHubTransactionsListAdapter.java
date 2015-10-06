package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.response.TransactionResponse;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubTransactionsListAdapter extends RecyclerView.Adapter<InfoHubTransactionsListAdapter.ViewHolder> {

    public static final int VIEW_TYPE_TRANSACTION = 0;
    public static final int VIEW_TYPE_LOADER = 1;

    private ArrayList<TransactionResponse> mDataSet;
    private Context mContext;
    private OnInfoHubItemClickListener onItemClickListener;
    private float mMarginOffset = 0;
    private boolean mLoadingFinished = false;
    private int mSize;

    public InfoHubTransactionsListAdapter(Context _context) {
        mContext = _context;
        mDataSet = new ArrayList<>();
        mSize = mDataSet.size() + 1;
    }

    public void addAll(final ArrayList<TransactionResponse> _transactionResponses) {
        mDataSet.addAll(_transactionResponses);
        if (!_transactionResponses.isEmpty()) {
            mSize = mDataSet.size() + 1;
            notifyItemRangeInserted(mDataSet.size() - _transactionResponses.size(), _transactionResponses.size());
        } else {
            mSize--;
            notifyItemRemoved(mDataSet.size());
        }
    }

    public void add(int position, TransactionResponse item) {
        mDataSet.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private HexagonView hexagonView;
        private TextView title;
        private TextView description;
        private TextView date;
        private RelativeLayout container;
        private ProgressBar progressBar;
        private boolean isProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelected((TransactionResponse) container.getTag());
                    }
                }
            });

            hexagonView = (HexagonView) itemView.findViewById(R.id.hvIcon_LIHLI);
            title = (TextView) itemView.findViewById(R.id.hvTitle_LIHLI);
            description = (TextView) itemView.findViewById(R.id.hvDescr_LIHLI);
            date = (TextView) itemView.findViewById(R.id.hvDate_LIHLI);
        }

        public ViewHolder(View view, boolean _isProgress) {
            super(view);
            isProgress = _isProgress;
            progressBar = (ProgressBar) view;
            progressBar.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        public void setData(TransactionResponse _model) {
//            Picasso.with(mContext).load(_model.getIconUrl()).into(hexagonView);
            title.setText(_model.title);
            description.setText(_model.description);
            date.setText(_model.modifiedDatetime);
            container.setTag(_model);
        }

        public void setLoaderVisibility() {
            progressBar.setVisibility(mLoadingFinished ? View.GONE : View.VISIBLE);
        }

        public View getContainer() {
            return container;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mDataSet.size()) {
            return VIEW_TYPE_TRANSACTION;
        } else {
            return VIEW_TYPE_LOADER;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default:
            case VIEW_TYPE_TRANSACTION:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info_hub, parent, false);
                return new ViewHolder(view);
            case VIEW_TYPE_LOADER:
                return new ViewHolder(new ProgressBar(parent.getContext()), true);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < mDataSet.size()) {
            holder.setData(mDataSet.get(position));

            if (position % 2 != 0) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getContainer().getLayoutParams();
                final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
                layoutParams.setMarginStart((int) mMarginOffset + margin);
                holder.getContainer().setLayoutParams(layoutParams);
            } else {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getContainer().getLayoutParams();
                layoutParams.setMarginStart((int) mMarginOffset);
                holder.getContainer().setLayoutParams(layoutParams);
            }
        } else {
            holder.setLoaderVisibility();
        }
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    public final void setOnItemClickListener(final OnInfoHubItemClickListener _onItemClickListener) {
        onItemClickListener = _onItemClickListener;
    }
}