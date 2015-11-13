package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.treview.EquipmentAndroidTreeView;
import com.uae.tra_smart_services.entities.treview.EquipmentModel;
import com.uae.tra_smart_services.entities.treview.TreeHeaderHolder;
import com.uae.tra_smart_services.entities.treview.TreeNode;
import com.uae.tra_smart_services.entities.treview.TreeSelectableItemHolder;
import com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter;

import java.util.List;

import static com.uae.tra_smart_services.entities.treview.TreeViewBaseAdapter.TreeEntity;

/**
 * Created by and on 10.11.15.
 */
public class TestActivity extends Activity {
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        container = (FrameLayout) findViewById(R.id.container);

        initTreeView();
    }

    private void initTreeView() {
        /*TreeNode root = TreeNode.root();
        TreeNode child0 = new TreeNode(new EquipmentModel.TreeHeader("Child0")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

        child0.addChildren(
                new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()))
        );
        TreeNode child1 = new TreeNode(new EquipmentModel.TreeHeader("Child1")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child11 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child12 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child121 = new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode child122 = new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode child123 = new TreeNode(new EquipmentModel.TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        child12.addChildren(child121, child122, child123);
        TreeNode child13 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child14 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode undefinedChild1 = new TreeNode(new EquipmentModel.TreeSelectableItem(false, "undefinedChild1")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode undefinedChild2 = new TreeNode(new EquipmentModel.TreeSelectableItem(false, "undefinedChild2")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode child15 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child16 = new TreeNode(new EquipmentModel.TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        child1.addChild( child11);
        child1.addChild( child12);
        child1.addChild( child13);
        child1.addChild( child14);
        child1.addChild(undefinedChild1);
        child1.addChild(undefinedChild2);
        child1.addChild(child15);
        child1.addChild(child16);

        TreeNode child2 = new TreeNode(new EquipmentModel.TreeHeader("Child2")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child3 = new TreeNode(new EquipmentModel.TreeHeader("Child3")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child4 = new TreeNode(new EquipmentModel.TreeHeader("Child4")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child5 = new TreeNode(new EquipmentModel.TreeHeader("Child5")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

        root.addChildren(child0, child1, child2, child3, child4, child5);
        */

        EquipmentAndroidTreeView tView = new EquipmentAndroidTreeView(this);
        tView.setTreeViewAdapter(new TreeViewBaseAdapter<TreeEntity>(EquipmentModel.getRootEntity()){

            @Override
            protected boolean hasChild(TreeEntity _entity) {
                return getChildCount(_entity) > 0;
            }

            @Override
            protected int getChildCount(TreeEntity _entity) {
                return _entity.getChildren().size();
            }

            @Override
            protected List<TreeEntity> getChildren(TreeEntity _entity) {
                return _entity.getChildren();
            }

            @Override
            protected TreeNode createTreeHeaderNode(TreeEntity _entity) {
                return new TreeNode(_entity).setViewHolder(new TreeHeaderHolder(getBaseContext()));
            }

            @Override
            protected TreeNode createTreeItemNode(TreeEntity _entity) {
                return new TreeNode(_entity).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
            }
        });
        tView.setDefaultAnimation(true);
        container.addView(tView.getView());
    }
}
