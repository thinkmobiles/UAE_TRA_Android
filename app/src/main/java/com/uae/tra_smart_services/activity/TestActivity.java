package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.treview.EquipmentAndroidTreeView;
import com.uae.tra_smart_services.entities.treview.TreeHeaderHolder;
import com.uae.tra_smart_services.entities.treview.TreeNode;
import com.uae.tra_smart_services.entities.treview.TreeSelectableItemHolder;
import com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter;
import com.uae.tra_smart_services.rest.model.response.EquipmentModel;

import java.util.List;

/**
 * Created by and on 13.11.15.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        FrameLayout container = (FrameLayout) findViewById(R.id.container);


        /*
    TreeNode root = TreeNode.root();
    TreeNode child0 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child0")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

    child0.addChildren(
            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
            new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()))
    );
    TreeNode child1 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child1")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child11 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child12 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child121 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    TreeNode child122 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    TreeNode child123 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    child12.addChildren(child121, child122, child123);
    TreeNode child13 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child14 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode undefinedChild1 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "undefinedChild1")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    TreeNode undefinedChild2 = new TreeNode(new EquipmentModel.EquipmentTreeSelectableItem(false, "undefinedChild2")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
    TreeNode child15 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child16 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    child1.addChild( child11);
    child1.addChild( child12);
    child1.addChild( child13);
    child1.addChild( child14);
    child1.addChild(undefinedChild1);
    child1.addChild(undefinedChild2);
    child1.addChild(child15);
    child1.addChild(child16);

    TreeNode child2 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child2")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child3 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child3")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child4 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child4")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
    TreeNode child5 = new TreeNode(new EquipmentModel.EquipmentTreeHeader("Child5")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

    root.addChildren(child0, child1, child2, child3, child4, child5);
    */



        EquipmentAndroidTreeView tView = new EquipmentAndroidTreeView(this);
        tView.setTreeViewAdapter(new TreeViewBaseAdapter<TreeViewBaseAdapter.TreeEntity>(EquipmentModel.getRootEntity()){

            @Override
            protected boolean hasChild(TreeViewBaseAdapter.TreeEntity _entity) {
                return getChildCount(_entity) > 0;
            }

            @Override
            protected int getChildCount(TreeViewBaseAdapter.TreeEntity _entity) {
                return _entity.getChildren().size();
            }

            @Override
            protected List<TreeEntity> getChildren(TreeViewBaseAdapter.TreeEntity _entity) {
                return _entity.getChildren();
            }

            @Override
            protected TreeNode createTreeHeaderNode(TreeViewBaseAdapter.TreeEntity _entity) {
                return new TreeNode(_entity).setViewHolder(new TreeHeaderHolder(getBaseContext()));
            }

            @Override
            protected TreeNode createTreeItemNode(TreeViewBaseAdapter.TreeEntity _entity) {
                return new TreeNode(_entity).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
            }
        });
        tView.setDefaultAnimation(true);
        container.addView(tView.getView());
    }
}
