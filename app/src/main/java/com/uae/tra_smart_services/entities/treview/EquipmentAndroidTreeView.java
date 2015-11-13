package com.uae.tra_smart_services.entities.treview;

import android.content.Context;

/**
 * Created by ak-buffalo on 13.11.15.
 */
public class EquipmentAndroidTreeView extends AndroidTreeView {
    public TreeViewBaseAdapter<?> mTreeViewBaseAdapter;
    public EquipmentAndroidTreeView(Context context) {
        super(context, null);
    }

    public void setTreeViewAdapter(TreeViewBaseAdapter<?> _adapter){
        mTreeViewBaseAdapter = _adapter;
        mRoot = mTreeViewBaseAdapter.getRoot();
    }
}