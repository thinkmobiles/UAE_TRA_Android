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
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

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
        TreeNode child0 = new TreeNode(new TreeHeader("Child0")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

        child0.addChildren(
                new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext())),
                new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()))
        );
        TreeNode child1 = new TreeNode(new TreeHeader("Child1")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child11 = new TreeNode(new TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child12 = new TreeNode(new TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child121 = new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode child122 = new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        TreeNode child123 = new TreeNode(new TreeSelectableItem(false, "ChildChild")).setViewHolder(new TreeSelectableItemHolder(getBaseContext()));
        child12.addChildren(child121, child122, child123);
        TreeNode child13 = new TreeNode(new TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child14 = new TreeNode(new TreeHeader("ChildChild")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        child1.addChildren(
                child11,child12,child13,child14

        );
        TreeNode child2 = new TreeNode(new TreeHeader("Child2")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child3 = new TreeNode(new TreeHeader("Child3")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child4 = new TreeNode(new TreeHeader("Child4")).setViewHolder(new TreeHeaderHolder(getBaseContext()));
        TreeNode child5 = new TreeNode(new TreeHeader("Child5")).setViewHolder(new TreeHeaderHolder(getBaseContext()));

        root.addChildren(child0, child1, child2, child3, child4, child5);

        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        container.addView(tView.getView());
    }

    public class TreeHeaderHolder extends TreeNode.BaseNodeViewHolder<TreeHeader> {
        private TextView tvValue;
        private PrintView arrowView;

        public TreeHeaderHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, TreeHeader value) {
            final View view = LayoutInflater.from(context).inflate(R.layout.layout_tree_header, null, false);

            tvValue = (TextView) view.findViewById(R.id.node_value);
            tvValue.setText(value.text);

            arrowView = (PrintView) view.findViewById(R.id.arrow_icon);
            if (node.isLeaf()) {
                arrowView.setVisibility(View.INVISIBLE);
            }

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(LEVEL_PADDING * mNode.getLevel(), 0, 0, 0);
            view.setLayoutParams(params);
            return view;
        }

        @Override
        public void toggle(boolean active) {
            arrowView.setIconText(context.getResources().getString(active ? R.string.ic_keyboard_arrow_up : R.string.ic_keyboard_arrow_down));
        }
    }

    public class TreeSelectableItemHolder extends TreeNode.BaseNodeViewHolder<TreeSelectableItem> {
        private TextView tvValue;
        private CheckBox nodeSelector;

        public TreeSelectableItemHolder(Context context) {
            super(context);
        }

        @Override
        public View createNodeView(TreeNode node, TreeSelectableItem value) {
            final View view = LayoutInflater.from(context).inflate(R.layout.layout_selectable_item, null, false);

            tvValue = (TextView) view.findViewById(R.id.node_value);
            tvValue.setText(value.title);

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
    }

    public static class TreeHeader {
        TreeHeader(String _title) {
            text = _title;
        }
        public String text;
    }

    public static class TreeSelectableItem {
        TreeSelectableItem(boolean _checked, String _title) {
            checked = _checked;
            title = _title;
        }
        public boolean checked;
        public String title;
    }
}
