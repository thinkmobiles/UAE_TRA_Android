package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.rest.model.new_response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnListAdapter extends RecyclerView.Adapter<InfoHubAnnListAdapter.ViewHolder>
                                    implements Filterable {

    private ArrayList<InfoHubAnnouncementsListItemModel> mDataSet;
    private Context mContext;
    private InfoHubAnnListAdapter.OnInfoHubItemClickListener onItemClickListener;
    private float mMarginOffset = 0;

    public InfoHubAnnListAdapter(Context _context, ArrayList<InfoHubAnnouncementsListItemModel> _dataSet){
        mContext = _context;
        mDataSet = _dataSet;
    }

    public void add(int position, InfoHubAnnouncementsListItemModel item) {
        mDataSet.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final HexagonView hexagonView;
        private final TextView description;
        private final TextView date;
        private final RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = (RelativeLayout) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemSelected((InfoHubAnnouncementsListItemModel) container.getTag());
                    }
                }
            });
            hexagonView = (HexagonView) itemView.findViewById(R.id.hvIcon_LIHLI);
            description = (TextView) itemView.findViewById(R.id.hvDescr_LIHLI);
            date = (TextView) itemView.findViewById(R.id.hvDate_LIHLI);
        }

        public void setData(InfoHubAnnouncementsListItemModel _model){
//            Picasso.with(InfoHubAnnListAdapter.this.mContext).load(_model.getIconUrl()).into(hexagonView);
            description.setText(_model.getDescription());
            date.setText(_model.getDate());
            container.setTag(_model);
        }

        public View getContainer(){
            return container;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_hub_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataSet.get(position));

        if (position % 2 == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getContainer().getLayoutParams();
            final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
            layoutParams.setMargins((int) mMarginOffset + margin, layoutParams.topMargin, layoutParams.getMarginEnd(), layoutParams.bottomMargin);
            holder.getContainer().setLayoutParams(layoutParams);
        } else {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.getContainer().getLayoutParams();
            layoutParams.setMargins((int) mMarginOffset,  layoutParams.topMargin, layoutParams.getMarginEnd(), layoutParams.bottomMargin);
            holder.getContainer().setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public final void setOnItemClickListener(final InfoHubAnnListAdapter.OnInfoHubItemClickListener _onItemClickListener) {
        onItemClickListener = _onItemClickListener;
    }

    public interface OnInfoHubItemClickListener {
        void onItemSelected(InfoHubAnnouncementsListItemModel item);
    }

    @Override
    public Filter getFilter() {
        return ListItemFilter.getInstance(this, mDataSet);
    }

    private static class ListItemFilter extends Filter {
        private static ListItemFilter mInstance;

        public static ListItemFilter getInstance(InfoHubAnnListAdapter adapter, List<InfoHubAnnouncementsListItemModel> originalList){
            if(mInstance == null){
                mInstance = new ListItemFilter(adapter, originalList);
            }
            return mInstance;
        }

        private final InfoHubAnnListAdapter adapter;

        private final List<InfoHubAnnouncementsListItemModel> originalList;

        private final List<InfoHubAnnouncementsListItemModel> filteredList;

        private ListItemFilter(InfoHubAnnListAdapter adapter, List<InfoHubAnnouncementsListItemModel> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final InfoHubAnnouncementsListItemModel listItem : originalList) {
                    if (listItem.getDescription().toLowerCase().trim().contains(filterPattern)) {
                        filteredList.add(listItem);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.mDataSet.clear();
            adapter.mDataSet.addAll((ArrayList<InfoHubAnnouncementsListItemModel>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}