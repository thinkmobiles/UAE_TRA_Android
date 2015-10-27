package com.uae.tra_smart_services_cutter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uae.tra_smart_services_cutter.R;
import com.uae.tra_smart_services_cutter.customviews.HexagonView;
import com.uae.tra_smart_services_cutter.global.ListItemFilter;
import com.uae.tra_smart_services_cutter.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services_cutter.rest.model.response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubAnnFullListAdapter extends RecyclerView.Adapter<InfoHubAnnFullListAdapter.ViewHolder>
                                    implements Filterable {

    private ArrayList<InfoHubAnnouncementsListItemModel> mDataSet;
    private Context mContext;
    private OnInfoHubItemClickListener onItemClickListener;
    private float mMarginOffset = 0;

    public InfoHubAnnFullListAdapter(Context _context, ArrayList<InfoHubAnnouncementsListItemModel> _dataSet){
        mContext = _context;
        mDataSet = _dataSet;
    }

    public void add(int position, InfoHubAnnouncementsListItemModel item) {
        mDataSet.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final HexagonView hexagonView;
        private final TextView title;
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
            hexagonView = (HexagonView) itemView.findViewById(R.id.hvIcon_LIIHA);
            title = (TextView) itemView.findViewById(R.id.hvTitle_LIIHA);
            description = (TextView) itemView.findViewById(R.id.hvDescr_LIIHA);
            date = (TextView) itemView.findViewById(R.id.hvDate_LIIHA);
        }

        public void setData(InfoHubAnnouncementsListItemModel _model){
            Picasso.with(mContext).load(_model.getIconUrl()).into(hexagonView);
            title.setText(_model.getTitle());
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info_hub_announsments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public final void setOnItemClickListener(final OnInfoHubItemClickListener _onItemClickListener) {
        onItemClickListener = _onItemClickListener;
    }

    @Override
    public ListItemFilter getFilter() {
        return AnnouncementsFilter.getInstance(mDataSet);
    }

    public static class AnnouncementsFilter extends ListItemFilter<InfoHubAnnFullListAdapter, InfoHubAnnouncementsListItemModel> {
        private AnnouncementsFilter(List<InfoHubAnnouncementsListItemModel> originalList) {
            super(originalList);
        }
        private static ListItemFilter mInstance;
        public static ListItemFilter getInstance(List<InfoHubAnnouncementsListItemModel> originalList){
            if(mInstance == null){
                mInstance = new AnnouncementsFilter(originalList);
            }
            return mInstance;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mAdapter.mDataSet.clear();
            mAdapter.mDataSet.addAll((ArrayList<InfoHubAnnouncementsListItemModel>) results.values);
            mAdapter.notifyDataSetChanged();
        }
    }
}