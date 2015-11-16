package com.uae.tra_smart_services.rest.model.response;

import java.util.ArrayList;
import java.util.List;

import static com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter.TreeEntity;

/**
 * Created by ak-buffalo on 13.11.15.
 */
public class EquipmentModel {
    /*
    -    TreeNode root = TreeNode.root();
    -    TreeNode child0 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child0")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -
    -    child0.addChildren(
    -            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
    -            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
    -            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
    -            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()))
    -    );
    -    TreeNode child1 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child1")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child11 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child12 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child121 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    -    TreeNode child122 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    -    TreeNode child123 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    -    child12.addChildren(child121, child122, child123);
    -    TreeNode child13 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child14 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode undefinedChild1 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "undefinedChild1")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    -    TreeNode undefinedChild2 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "undefinedChild2")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    -    TreeNode child15 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child16 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    child1.addChild( child11);
    -    child1.addChild( child12);
    -    child1.addChild( child13);
    -    child1.addChild( child14);
    -    child1.addChild(undefinedChild1);
    -    child1.addChild(undefinedChild2);
    -    child1.addChild(child15);
    -    child1.addChild(child16);
    -
    -    TreeNode child2 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child2")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child3 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child3")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child4 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child4")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -    TreeNode child5 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child5")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    -
    -    root.addChildren(child0, child1, child2, child3, child4, child5);
    -    */
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
                TreeEntity header_4 = new EquipmentTreeHeader("EQUIPMENT 4");
                TreeEntity header_4_1 = new EquipmentTreeHeader("EQUIPMENT 4-1");
                TreeEntity header_4_2 = new EquipmentTreeHeader("EQUIPMENT 4-2");
                TreeEntity header_4_2_1 = new EquipmentTreeSelectableItem(true, "EQUIPMENT 4-22-1");
                TreeEntity header_4_2_2 = new EquipmentTreeSelectableItem(false, "EQUIPMENT 4-2-2");
                TreeEntity header_4_2_3 = new EquipmentTreeSelectableItem(true, "EQUIPMENT 4-2-3");
                header_4_2.add(header_4_2_1);
                header_4_2.add(header_4_2_2);
                header_4_2.add(header_4_2_3);
                TreeEntity header_4_3 = new EquipmentTreeHeader("EQUIPMENT 4-3");
                header_4.add(header_4_1);
                header_4.add(header_4_2);
                header_4.add(header_4_3);
                add(header_4);
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