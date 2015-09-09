package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.SearchResult;
import com.uae.tra_smart_services.global.ListItemFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by and on 09.09.15.
 */
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>
                                                                            implements Filterable{
    /** FIELDS */
    private SearchResult mDataSet;
    private Context mContext;
    private OnSearchResultItemClickListener listener;

    /** CONSTRUCTOR */
    public SearchRecyclerViewAdapter(final Context _context, final SearchResult _dataSet) {
        mDataSet = _dataSet;
        mContext = _context;
    }

    /** OVERRIDEN METHODS */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_search_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataSet.getSearchResultItems().get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.getCount();
    }

    @Override
    public Filter getFilter() {
        return SearchResultFilter.getInstance(this, mDataSet.getSearchResultItems());
    }

    /** METHODS */
    public void setOnSearchResultItemClickListener(OnSearchResultItemClickListener _listener){
        listener = _listener;
    }

    public void clear(){
        if(getItemCount() != 0){
            mDataSet.clearAll();
        }
    }

    public void addAllFiltered(ArrayList<SearchResult.SearchResultItem> filteredValues){
            mDataSet.addAllItems(filteredValues);
    }

    /** ENTITIES */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LinearLayout container;
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvSearchResultItem_RSI);
            textView.setOnClickListener(this);
            container = (LinearLayout) itemView.findViewById(R.id.llContainer_RSI);
        }

        public View getContainer() {
            return container;
        }

        public void setData(final SearchResult.SearchResultItem searchResultItem) {
            textView.setText(searchResultItem.getText());
            container.setTag(searchResultItem);
        }

        @Override
        public void onClick(View _view) {
            if(listener != null){
                listener.onSearchResultItemClicked((SearchResult.SearchResultItem) _view.getTag());
            }
        }
    }

    public static class SearchResultFilter extends ListItemFilter<SearchRecyclerViewAdapter, SearchResult.SearchResultItem> {
        private SearchResultFilter(SearchRecyclerViewAdapter adapter, List<SearchResult.SearchResultItem> originalList) {
            super(adapter, originalList);
        }
        public static ListItemFilter getInstance(SearchRecyclerViewAdapter adapter, List<SearchResult.SearchResultItem> originalList){
            if(mInstance == null){
                mInstance = new SearchResultFilter(adapter, originalList);
            }
            return mInstance;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAdapter.clear();
            mAdapter.addAllFiltered((ArrayList<SearchResult.SearchResultItem>) results.values);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface OnSearchResultItemClickListener{
        void onSearchResultItemClicked(SearchResult.SearchResultItem _item);
    }
}