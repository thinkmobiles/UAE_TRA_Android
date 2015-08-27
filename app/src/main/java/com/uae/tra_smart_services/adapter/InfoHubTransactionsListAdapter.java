package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.customviews.HexagonView;
import com.uae.tra_smart_services.interfaces.OnInfoHubItemClickListener;
import com.uae.tra_smart_services.rest.model.response.InfoHubTransActionsListItemModel;

import java.util.ArrayList;

/**
 * Created by ak-buffalo on 18.08.15.
 */
public class InfoHubTransactionsListAdapter extends RecyclerView.Adapter<InfoHubTransactionsListAdapter.ViewHolder> {

    private ArrayList<InfoHubTransActionsListItemModel> mDataSet;
    private Context mContext;
    private OnInfoHubItemClickListener onItemClickListener;
    private float mMarginOffset = 0;

    public InfoHubTransactionsListAdapter(Context _context, ArrayList<InfoHubTransActionsListItemModel> _dataSet){
        mContext = _context;
        mDataSet = _dataSet;
    }

    public void add(int position, InfoHubTransActionsListItemModel item) {
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
                        onItemClickListener.onItemSelected((InfoHubTransActionsListItemModel) container.getTag());
                    }
                }
            });

            hexagonView = (HexagonView) itemView.findViewById(R.id.hvIcon_LIHLI);
            title = (TextView) itemView.findViewById(R.id.hvTitle_LIHLI);
            description = (TextView) itemView.findViewById(R.id.hvDescr_LIHLI);
            date = (TextView) itemView.findViewById(R.id.hvDate_LIHLI);
        }

        public void setData(InfoHubTransActionsListItemModel _model){
            hexagonView.setHexagonSrcDrawable(R.drawable.layerlist_infohub_icon);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_hub_list_item, parent, false);
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
}