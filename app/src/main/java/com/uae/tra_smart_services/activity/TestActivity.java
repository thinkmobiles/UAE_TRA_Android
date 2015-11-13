package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.TreeNode;
import com.uae.tra_smart_services.entities.AndroidTreeView;

import java.util.ArrayList;

/**
 * Created by and on 10.11.15.
 */
public class TestActivity extends Activity {
    private FrameLayout container;
    private static final int LEVEL_PADDING = 30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        container = (FrameLayout) findViewById(R.id.container);

        initTreeView();
    }

    private void initTreeView() {
        TreeNode root = TreeNode.root();
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

        CustomAndroidTreeView tView = new CustomAndroidTreeView(this);
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

    public class TreeHeaderHolder extends TreeNode.BaseNodeViewHolder<EquipmentModel.TreeHeader> {
        private TextView tvValue;
        private PrintView arrowView;

        public TreeHeaderHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, EquipmentModel.TreeHeader value) {
            final View view = LayoutInflater.from(context).inflate(R.layout.layout_tree_header, null, false);

            tvValue = (TextView) view.findViewById(R.id.node_value);
            tvValue.setText(value.title);

            arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
            if (node.isLeaf()) {
                arrowView.setVisibility(View.INVISIBLE);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(LEVEL_PADDING * (mNode.getLevel() - 1), 0, 0, 0);
            view.setLayoutParams(params);
            return view;
        }

        @Override
        public void toggle(boolean active) {
            arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_up : R.string.ic_keyboard_arrow_down));
        }
    }

    public class TreeSelectableItemHolder extends TreeNode.BaseNodeViewHolder<EquipmentModel.TreeSelectableItem> implements TreeNode.TreeNodeClickListener{
        private TextView tvValue;
        private CheckBox nodeSelector;

        public TreeSelectableItemHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, EquipmentModel.TreeSelectableItem value) {
            final View view = LayoutInflater.from(context).inflate(R.layout.layout_selectable_item, null, false);

            tvValue = (TextView) view.findViewById(R.id.node_value);
            tvValue.setText(value.text);

            nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
            nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mNode.setSelected(isChecked);
                }
            });
            nodeSelector.setChecked(node.isSelected());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(LEVEL_PADDING * (mNode.getLevel() - 1), 0, 0, 0);
            view.setLayoutParams(params);

            return view;
        }

        @Override
        public void toggleSelectionMode(boolean editModeEnabled) {
            nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
            nodeSelector.setChecked(mNode.isSelected());
        }

        @Override
        public void toggle(boolean active) {
            super.toggle(active);
            mNode.setSelected(active);
            nodeSelector.setChecked(active);
        }

        @Override
        public void onClick(TreeNode node, Object value) {
        }
    }

    public class CustomAndroidTreeView extends AndroidTreeView {
        public TreeViewBaseAdapter<?> mTreeViewBaseAdapter;
        public CustomAndroidTreeView(Context context) {
            super(context, null);
        }

        public void setTreeViewAdapter(TreeViewBaseAdapter<?> _adapter){
            mTreeViewBaseAdapter = _adapter;
            mRoot = mTreeViewBaseAdapter.getRoot();
        }

    }
        public abstract class TreeViewBaseAdapter<M extends TreeEntity>{
            private M mModel;
            TreeNode root = TreeNode.root();

            TreeViewBaseAdapter(M _model){
                mModel = _model;
            }

            public TreeNode getRoot(){
                return root.addChildren(createChildNodes(mModel));
            }

            protected abstract boolean hasChild(M _entity);

            protected abstract int getChildCount(M _entity);

            protected abstract TreeNode createTreeHeaderNode(M _entity);

            protected abstract TreeNode createTreeItemNode(M _entity);

            protected TreeNode createTreeNode(M _entity){
                if(_entity.canHaveChild()){
                    return createTreeHeaderNode(_entity);
                } else {
                    return createTreeItemNode(_entity);
                }
            }

            protected ArrayList<TreeNode> createChildNodes(TreeEntity _entity) {
                ArrayList<TreeNode> treeNodes = new ArrayList<>();
                for (int i = 0; i < _entity.getChildren().size(); i++){
                    TreeEntity treeEntity = _entity.getChildren().get(i);
                    TreeNode treeNode = createTreeNode((M)treeEntity);
                    if(hasChild((M)treeEntity)){
                        treeNode.addChildren(createChildNodes(treeEntity));
                    }
                    treeNodes.add(treeNode);
                }
                return treeNodes;
            }
        }

    public static class EquipmentModel{

        public static TreeEntity getRootEntity(){
            return new TreeHeader(null){
                {
                    add(new TreeHeader("EQUIPMENT 1"));
                    TreeEntity header_2 = new TreeHeader("EQUIPMENT 2");
                    TreeEntity header_2_1 = new TreeHeader("EQUIPMENT 2-1");
                    TreeEntity header_2_2 = new TreeHeader("EQUIPMENT 2-2");
                    TreeEntity header_2_3 = new TreeHeader("EQUIPMENT 2-3");
                    TreeEntity header_2_4 = new TreeSelectableItem(true, "EQUIPMENT 2-4");
                    TreeEntity header_2_5 = new TreeSelectableItem(false, "EQUIPMENT 2-5");
                    TreeEntity header_2_3_1 = new TreeHeader("EQUIPMENT 2_3_1");
                    TreeEntity header_2_3_2 = new TreeHeader("EQUIPMENT 2_3_2");
                    TreeEntity header_2_3_3 = new TreeHeader("EQUIPMENT 2_3_3");
                    TreeEntity header_2_3_3_1 = new TreeHeader("EQUIPMENT 2_3_3_1");
                    TreeEntity header_2_3_3_2 = new TreeHeader("EQUIPMENT 2_3_3_2");
                    TreeEntity header_2_3_3_3 = new TreeHeader("EQUIPMENT 2_3_3_3");
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
                    add(new TreeHeader("EQUIPMENT 3"));
                    add(new TreeHeader("EQUIPMENT 4"));
                    add(new TreeHeader("EQUIPMENT 5"));
                    add(new TreeHeader("EQUIPMENT 6"));
                }
            };
        }

        public ArrayList<TreeEntity> mHeaders = new ArrayList<>();

        public static class TreeHeader implements TreeEntity {
            public ArrayList<TreeEntity> children = new ArrayList<>();

            public String title;
            TreeHeader(String _title) {
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
            public ArrayList<TreeEntity> getChildren() {
                return children;
            }
        }

        public static class TreeSelectableItem implements TreeEntity{
            public String text;
            public boolean checked;
            TreeSelectableItem(boolean _checked, String _title) {
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
            public ArrayList<TreeEntity> getChildren() {
                return new ArrayList<>();
            }
        }
    }

    public interface TreeEntity {
        boolean canHaveChild();
        void add(TreeEntity _entity);
        ArrayList<TreeEntity> getChildren();
    }
}
