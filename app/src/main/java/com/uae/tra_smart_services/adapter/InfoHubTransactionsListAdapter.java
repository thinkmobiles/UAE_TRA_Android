package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.InfoHubTransactionsListAdapter.ViewHolder;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.rest.model.response.GetTransactionResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubTransactionsListAdapter extends Adapter<ViewHolder> implements Filterable {

    private static final int VIEW_TYPE_TRANSACTION = 0;
    private static final int VIEW_TYPE_LOADER = 1;

    private final Context mContext;

    private boolean mIsShowingFilteredData;
    private boolean mIsShowingLoader;
    private List<GetTransactionResponseModel> mDataSet, mShowingData;
    private float mMarginOffset = 0;
    private boolean mLoadingFinished = false;
    //    private int mSize;
    private Filter mFilter;

    public InfoHubTransactionsListAdapter(final Context _context) {
        mContext = _context;
        mIsShowingLoader = true;
        mDataSet = new ArrayList<>();
        mShowingData = new ArrayList<>();
//        mSize = mDataSet.size() + 1;
    }

    public boolean isInLoadingState() {
        return mIsShowingLoader;
    }

    public void startLoading() {
        if (!isInLoadingState()) {
            mIsShowingLoader = true;
            notifyItemInserted(mShowingData.size());
        }
    }

    public void stopLoading() {
        if (isInLoadingState()) {
            mIsShowingLoader = false;
            notifyItemRemoved(mShowingData.size());
        }
    }

    private void invalidateFilter() {
        mFilter = null;
    }

    public boolean isEmpty() {
        return mDataSet.isEmpty();
    }

    public void addAll(final List<GetTransactionResponseModel> _transactionResponses) {
        mDataSet.addAll(_transactionResponses);
        invalidateFilter();
        if (!mIsShowingFilteredData) {
            mShowingData.addAll(_transactionResponses);
            if (_transactionResponses.isEmpty()) {
                mIsShowingLoader = false;
                notifyItemRemoved(mShowingData.size());
            } else {
                mIsShowingLoader = true;
                notifyItemRangeInserted(mDataSet.size() - _transactionResponses.size(), _transactionResponses.size());
            }
        }
    }

//    public void add(int position, GetTransactionResponseModel item) {
//        invalidateFilter();
//        mDataSet.add(position, item);
//        if (mIsShowingFilteredData) {
//            mShowingData.clear();
//            mShowingData.addAll(mDataSet);
//            notifyDataSetChanged();
//        } else {
//            notifyItemInserted(position);
//        }
//    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new TransactionFilter(mDataSet);
        }
        return mFilter;
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

        public void setData(GetTransactionResponseModel _model) {
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
        if (position < mShowingData.size()) {
            holder.setData(mShowingData.get(position));

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
        final int progressBarCount = (mIsShowingLoader && !mIsShowingFilteredData) ? 1 : 0;
        return mShowingData.size() + progressBarCount;
    }

    private final class TransactionFilter extends Filter {

        private final List<GetTransactionResponseModel> mAllData;

        private TransactionFilter(final List<GetTransactionResponseModel> _allData) {
            mAllData = _allData;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final List<GetTransactionResponseModel> filteredList;
            if (TextUtils.isEmpty(constraint)) {
                filteredList = mAllData;
            } else {
                filteredList = getFilteredList(constraint);
            }
            final FilterResults filterResults = new FilterResults();
            filterResults.count = filteredList.size();
            filterResults.values = filteredList;
            return filterResults;
        }

        @NonNull
        private List<GetTransactionResponseModel> getFilteredList(final CharSequence _constraint) {
            List<GetTransactionResponseModel> filteredData = new ArrayList<>();
            for (int i = 0; i < mAllData.size(); i++) {
                final GetTransactionResponseModel transactionResponse = mAllData.get(i);
                if (transactionResponse.title.toLowerCase().contains(_constraint.toString().toLowerCase().trim())) {
                    filteredData.add(transactionResponse);
                }
            }
            return filteredData;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mIsShowingFilteredData = results.count < mAllData.size();
            showFilteredData((List<GetTransactionResponseModel>) results.values);
        }

    }

    protected void showFilteredData(final List<GetTransactionResponseModel> _data) {
        mShowingData.clear();
        mShowingData.addAll(_data);
        notifyDataSetChanged();
    }
}