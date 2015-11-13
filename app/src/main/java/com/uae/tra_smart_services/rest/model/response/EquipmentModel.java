package com.uae.tra_smart_services.rest.model.response;

import java.util.ArrayList;
import java.util.List;

import static com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter.TreeEntity;

/**
 * Created by ak-buffalo on 13.11.15.
 */
public class EquipmentModel {

    public static TreeEntity getRootEntity(){
        return new EquipmentTreeHeader(null){
            {
                add(new EquipmentTreeHeader("EQUIPMENT 1"));
                TreeEntity header_2 = new EquipmentTreeHeader("EQUIPMENT 2");
                TreeEntity header_2_1 = new EquipmentTreeHeader("EQUIPMENT 2-1");
                TreeEntity header_2_2 = new EquipmentTreeHeader("EQUIPMENT 2-2");
                TreeEntity header_2_3 = new EquipmentTreeHeader("EQUIPMENT 2-3");
                TreeEntity header_2_4 = new EquipmentTreeSelectableItem(true, "EQUIPMENT 2-4");
                TreeEntity header_2_5 = new EquipmentTreeSelectableItem(false, "EQUIPMENT 2-5");
                TreeEntity header_2_3_1 = new EquipmentTreeHeader("EQUIPMENT 2_3_1");
                TreeEntity header_2_3_2 = new EquipmentTreeHeader("EQUIPMENT 2_3_2");
                TreeEntity header_2_3_3 = new EquipmentTreeHeader("EQUIPMENT 2_3_3");
                TreeEntity header_2_3_3_1 = new EquipmentTreeHeader("EQUIPMENT 2_3_3_1");
                TreeEntity header_2_3_3_2 = new EquipmentTreeHeader("EQUIPMENT 2_3_3_2");
                TreeEntity header_2_3_3_3 = new EquipmentTreeHeader("EQUIPMENT 2_3_3_3");
                header_2_3_3.add(header_2_3_3_1);
                header_2_3_3.add(header_2_3_3_2);
                header_2_3_3.add(header_2_3_3_3);
                header_2_3.add(header_2_3_1);
                header_2_3.add(header_2_3_2);
                header_2_3.add(header_2_3_3);
                header_2.add(header_2_1);
                header_2.add(header_2_2);
                header_2.add(header_2_3);
                header_2.add(header_2_4);
                header_2.add(header_2_5);
                add(header_2);
                add(new EquipmentTreeHeader("EQUIPMENT 3"));
                add(new EquipmentTreeHeader("EQUIPMENT 4"));
                add(new EquipmentTreeHeader("EQUIPMENT 5"));
                add(new EquipmentTreeHeader("EQUIPMENT 6"));
            }
        };
    }

    public static class EquipmentTreeHeader implements TreeEntity {
        public List<TreeEntity> children = new ArrayList<>();

        public String title;
        EquipmentTreeHeader(String _title) {
            title = _title;
        }

        @Override
        public boolean canHaveChild() {
            return true;
        }

        @Override
        public void add(TreeEntity _entity) {
            children.add(_entity);
        }

        @Override
        public List<TreeEntity> getChildren() {
            return children;
        }
    }

    public static class EquipmentTreeSelectableItem implements TreeEntity{
        public String text;
        public boolean checked;
        EquipmentTreeSelectableItem(boolean _checked, String _title) {
            checked = _checked;
            text = _title;
        }

        @Override
        public boolean canHaveChild() {
            return false;
        }

        @Override
        public void add(TreeEntity _entity) { /* Never have to be implemented*/ }

        @Override
        public List<TreeEntity> getChildren() {
            return new ArrayList<>();
        }
    }
}