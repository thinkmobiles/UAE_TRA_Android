package com.uae.tra_smart_services.rest.model.response;

import java.util.ArrayList;
import java.util.List;

import static com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter.TreeEntity;

/**
 * Created by ak-buffalo on 13.11.15.
 */
public class EquipmentTreeModel {
    public String validateAs;
    public boolean required;
    public String inputType;
    public String name;
    public int order;
    public String _id;
    public ArrayList<? extends EquipmentTreeItem> dataSource;

    public class EquipmentTreeItem implements TreeEntity {
        public List<TreeEntity> items = new ArrayList<>();

        public String value;
        public String EN;
        public String AR;
        public boolean checked;

        @Override
        public boolean haveChild() {
            return items.size() > 0;
        }

        @Override
        public void add(TreeEntity _entity) {
            items.add(_entity);
        }

        @Override
        public List<TreeEntity> getChildren() {
            return items;
        }
    }
}