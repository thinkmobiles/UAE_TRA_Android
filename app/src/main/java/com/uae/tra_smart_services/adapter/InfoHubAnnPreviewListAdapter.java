package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.new_response.InfoHubAnnouncementsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 19.08.15.
 */
public class InfoHubAnnPreviewListAdapter extends RecyclerView.Adapter<InfoHubAnnPreviewListAdapter.ViewHolder> {

    private ArrayList<InfoHubAnnouncementsListItemModel> mDataSet;
    private Context mContext;
    private OnInfoHubItemClickListener onItemClickListener;

    public InfoHubAnnPreviewListAdapter(Context _context, ArrayList<InfoHubAnnouncementsListItemModel> _dataSet){
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
            hexagonView = (HexagonView) itemView.findViewById(R.id.hvIcon_LIHLI);
            title = (TextView) itemView.findViewById(R.id.hvTitle_LIHLI);
            description = (TextView) itemView.findViewById(R.id.hvDescr_LIHLI);
            date = (TextView) itemView.findViewById(R.id.hvDate_LIHLI);
        }

        public void setData(InfoHubAnnouncementsListItemModel _model){
//            Picasso.with(InfoHubAnnFullListAdapter.this.mContext).load(_model.getIconUrl()).into(hexagonView);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_hub_list_item, parent, false);
        view.findViewById(R.id.vListViewItemSeparator).setVisibility(View.INVISIBLE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataSet.get(position));
        holder.getContainer().setLayoutParams(holder.getContainer().getLayoutParams());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public final void setOnItemClickListener(final OnInfoHubItemClickListener _onItemClickListener) {
        onItemClickListener = _onItemClickListener;
    }
}