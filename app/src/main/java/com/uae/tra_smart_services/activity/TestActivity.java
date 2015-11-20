package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.entities.treview.EquipmentTreeView;
import com.uae.tra_smart_services.entities.treview.TreeHeaderHolder;
import com.uae.tra_smart_services.entities.treview.TreeNode;
import com.uae.tra_smart_services.entities.treview.TreeSelectableItemHolder;
import com.uae.tra_smart_services.adapter.TreeViewBaseAdapter;
import com.uae.tra_smart_services.rest.model.response.EquipmentTreeModel;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by and on 13.11.15.
 */
public class TestActivity extends Activity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activitiy);
//        createTreeView();

        RecyclerView mCrimeRecyclerView = (RecyclerView) findViewById(R.id.expandibleRecycleView);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        WorkQueueExpandableAdapter crimeExpandableAdapter = new WorkQueueExpandableAdapter(getActivity(), generateCrimes());
    }

    private final void createTreeView(){
        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        String strModel = "{\n" +
                        "          \"validateAs\": \"none\",\n" +
                        "          \"required\": false,\n" +
                        "          \"inputType\": \"tree\",\n" +
                        "          \"name\": \"checkTree\",\n" +
                        "          \"order\": 1,\n" +
                        "          \"_id\": \"564c8d7964ad6f431d6a1498\",\n" +
                        "          \"dataSource\": [\n" +
                        "            {\n" +
                        "              \"value\": \"Node 1s\",\n" +
                        "              \"EN\": \"sss\",\n" +
                        "              \"AR\": \"sss\",\n" +
                        "              \"items\": [\n" +
                        "                {\n" +
                        "                  \"value\": \"Node 1_1d\",\n" +
                        "                  \"EN\": \"ddd\",\n" +
                        "                  \"AR\": \"ddd\",\n" +
                        "                  \"items\": [\n" +
                        "                    \n" +
                        "                  ]\n" +
                        "                }\n" +
                        "              ]\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"value\": \"Node 2f\",\n" +
                        "              \"EN\": \"ff\",\n" +
                        "              \"AR\": \"ff\",\n" +
                        "              \"items\": [\n" +
                        "                \n" +
                        "              ]\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"value\": \"Node 3g\",\n" +
                        "              \"EN\": \"gg\",\n" +
                        "              \"AR\": \"al gg\",\n" +
                        "              \"items\": [\n" +
                        "                {\n" +
                        "                  \"value\": \"Node 3_3asa\",\n" +
                        "                  \"EN\": \"asa\",\n" +
                        "                  \"AR\": \"asa\",\n" +
                        "                  \"items\": [\n" +
                        "                    {\n" +
                        "                      \"value\": \"Node 3_3_1s\",\n" +
                        "                      \"EN\": \"asdasd\",\n" +
                        "                      \"AR\": \"asdas\",\n" +
                        "                      \"items\": [\n" +
                        "                        \n" +
                        "                      ]\n" +
                        "                    },\n" +
                        "                    {\n" +
                        "                      \"value\": \"Node 3_3_2d\",\n" +
                        "                      \"EN\": \"sasd\",\n" +
                        "                      \"AR\": \"asdasd\",\n" +
                        "                      \"items\": [\n" +
                        "                        {\n" +
                        "                          \"value\": \"Node 3_3_2_1e\",\n" +
                        "                          \"EN\": \"weqw\",\n" +
                        "                          \"AR\": \"eqweqwe\",\n" +
                        "                          \"items\": [\n" +
                        "                            {\n" +
                        "                              \"value\": \"Node 3_3_2_1_0a\",\n" +
                        "                              \"EN\": \"adasd\",\n" +
                        "                              \"AR\": \"asdasdas\",\n" +
                        "                              \"items\": [\n" +
                        "                                \n" +
                        "                              ]\n" +
                        "                            }\n" +
                        "                          ]\n" +
                        "                        }\n" +
                        "                      ]\n" +
                        "                    }\n" +
                        "                  ]\n" +
                        "                }\n" +
                        "              ]\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"value\": \"Node 5as\",\n" +
                        "              \"EN\": \"asas\",\n" +
                        "              \"AR\": \"asas\",\n" +
                        "              \"items\": [\n" +
                        "                {\n" +
                        "                  \"value\": \"Node 5_5f\",\n" +
                        "                  \"EN\": \"ff\",\n" +
                        "                  \"AR\": \"ff\",\n" +
                        "                  \"items\": [\n" +
                        "                    \n" +
                        "                  ]\n" +
                        "                }\n" +
                        "              ]\n" +
                        "            }\n" +
                        "          ],\n" +
                        "          \"displayName\": {\n" +
                        "            \"AR\": \"AR Check Some\",\n" +
                        "            \"EN\": \"Check Some\"\n" +
                        "          },\n" +
                        "          \"placeHolder\": {\n" +
                        "            \"AR\": \"AR Check Some\",\n" +
                        "            \"EN\": \"Check Some\"\n" +
                        "          }\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }";

        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(strModel));
        reader.setLenient(true);
        final EquipmentTreeModel equipmentTreeModel = gson.fromJson(reader, EquipmentTreeModel.class);

        EquipmentTreeView equipmentTreeView = new EquipmentTreeView(this);
        equipmentTreeView.setTreeViewAdapter(new TreeViewBaseAdapter<TreeViewBaseAdapter.TreeEntity>(new ArrayList<TreeViewBaseAdapter.TreeEntity>(){{
            addAll(equipmentTreeModel.dataSource);
        }}) {
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
        equipmentTreeView.setDefaultAnimation(true);
        container.addView(equipmentTreeView.getView());
    }
}
