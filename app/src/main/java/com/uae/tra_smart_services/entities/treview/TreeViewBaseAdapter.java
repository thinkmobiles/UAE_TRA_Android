package com.uae.tra_smart_services.entities.treview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 13.11.15.
 */
public abstract class TreeViewBaseAdapter<M extends TreeViewBaseAdapter.TreeEntity>{
    private M mModel;
    TreeNode root = TreeNode.root();

    public TreeViewBaseAdapter(M _model){
        mModel = _model;
    }

    public TreeNode getRoot(){
        return root.addChildren(createChildNodes(mModel));
    }

    protected abstract boolean hasChild(M _entity);

    protected abstract int getChildCount(M _entity);

    protected abstract List<TreeEntity> getChildren(TreeEntity _entity);

    protected abstract TreeNode createTreeHeaderNode(M _entity);

    protected abstract TreeNode createTreeItemNode(M _entity);

    protected TreeNode createTreeNode(M _entity){
        if(_entity.canHaveChild()){
            return createTreeHeaderNode(_entity);
        } else {
            return createTreeItemNode(_entity);
        }
    }

    protected List<TreeNode> createChildNodes(TreeEntity _entity) {
        List<TreeNode> treeNodes = new ArrayList<>();
        for (int i = 0; i < getChildCount((M) _entity); i++){
            TreeEntity treeEntity = getChildren(_entity).get(i);
            TreeNode treeNode = createTreeNode((M)treeEntity);
            if(hasChild((M)treeEntity)){
                treeNode.addChildren(createChildNodes(treeEntity));
            }
            treeNodes.add(treeNode);
        }
        return treeNodes;
    }

    public interface TreeEntity {
        boolean canHaveChild();
        void add(TreeEntity _entity);
        List<TreeEntity> getChildren();
    }
}