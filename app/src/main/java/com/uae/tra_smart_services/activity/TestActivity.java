package com.uae.tra_smart_services.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.uae.tra_smart_services.R;
import com.uae.tra_smart_services.adapter.WorkQueueExpandableAdapter;
import com.uae.tra_smart_services.entities.treview.EquipmentTreeView;
import com.uae.tra_smart_services.entities.treview.TreeHeaderHolder;
import com.uae.tra_smart_services.entities.treview.TreeNode;
import com.uae.tra_smart_services.entities.treview.TreeSelectableItemHolder;
import com.uae.tra_smart_services.adapter.TreeViewBaseAdapter;
import com.uae.tra_smart_services.rest.model.response.EquipmentTreeModel;
import com.uae.tra_smart_services.rest.model.response.WorkQueueDataModel;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by and on 13.11.15.
 */
public class TestActivity extends Activity{
    private class Student implements  Comparable<Student>{
        public int num;
        public String name;

        public Student(int num, String name, int age) {
            this.num = num;
            this.name = name;
            this.age = age;
        }

        public int age;

        @Override
        public int compareTo(Student another) {
            return (num > another.num) ? 1 : -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activitiy);
//        createTreeView();






        ArrayList<Student> arraylist = new ArrayList<Student>();
        arraylist.add(new Student(223, "Chaitanya", 26));
        arraylist.add(new Student(245, "Rahul", 24));
        arraylist.add(new Student(209, "Ajeet", 32));

        Collections.sort(arraylist);

        for(Student str: arraylist){
            System.out.println(str.name);
        }











        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new StringReader(dataModelStr));
        final WorkQueueDataModel workQueueDataModel = gson.fromJson(reader, WorkQueueDataModel.class);

        List<String> sortBy = new ArrayList<>();
        for (Map<String,String> localized : workQueueDataModel.dataSource){
                sortBy.add(localized.get("EN"));
        }
        Spinner sortSelector = (Spinner) findViewById(R.id.sSortSelector);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, sortBy);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSelector.setAdapter(dataAdapter);

        RecyclerView mCrimeRecyclerView = (RecyclerView) findViewById(R.id.expandibleRecycleView);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WorkQueueExpandableAdapter adapter = new WorkQueueExpandableAdapter(this, workQueueDataModel);
        mCrimeRecyclerView.setAdapter(adapter);
        sortSelector.setOnItemSelectedListener(adapter);
    }

    private static final String dataModelStr = "{\n" +
            "          \"order\": 0,\n" +
            "          \"name\": \"tableContent\",\n" +
            "          \"inputType\": \"table\",\n" +
            "          \"additional\": {\n" +
            "            \"sectorField\": \"dealerType\"\n" +
            "          },\n" +
            "          \"required\": false,\n" +
            "          \"validateAs\": \"none\",\n" +
            "          \"_id\": \"564ed3f65491f82223bedcac\",\n" +
            "          \"dataSource\": [\n" +
            "            {\n" +
            "              \"EN\": \"Reference Number\",\n" +
            "              \"AR\": \"al Reference Number\",\n" +
            "              \"value\": \"referenceNumber\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Status\",\n" +
            "              \"AR\": \"al Status\",\n" +
            "              \"value\": \"status\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Workitem Name\",\n" +
            "              \"AR\": \"al Workitem Name\",\n" +
            "              \"value\": \"workitemName\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Task Type\",\n" +
            "              \"AR\": \"al Task Type\",\n" +
            "              \"value\": \"taskType\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Organization Name\",\n" +
            "              \"AR\": \"al Organization Name\",\n" +
            "              \"value\": \"organizationName\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Created By\",\n" +
            "              \"AR\": \"al Created By\",\n" +
            "              \"value\": \"createdBy\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Created Date\",\n" +
            "              \"AR\": \"al Created Date\",\n" +
            "              \"value\": \"createdDate\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"EN\": \"Dealer Type\",\n" +
            "              \"AR\": \"Dealer Type\",\n" +
            "              \"value\": \"dealerType\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"displayName\": {\n" +
            "            \"EN\": \"Some Table\",\n" +
            "            \"AR\": \"AR Some Table\"\n" +
            "          },\n" +
            "          \"placeHolder\": {\n" +
            "            \"EN\": \"\",\n" +
            "            \"AR\": \"\"\n" +
            "          },\n" +
            "          \"dataContent\": [\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2020\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"2\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2019\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"3\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2018\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"4\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2017\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"5\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2016\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"6\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2015\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"7\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"8\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2013\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"9\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2012\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"10\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2011\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"11\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2010\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"12\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2009\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"13\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"1\",\n" +
            "              \"status\": \"enable\",\n" +
            "              \"workitemName\": \"workitem\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"2\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"3\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2003\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"4\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"5\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"6\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"7\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2002\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"8\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type1\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"9\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2001\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type2\"\n" +
            "            },\n" +
            "            {\n" +
            "              \"referenceNumber\": \"10\",\n" +
            "              \"status\": \"disable\",\n" +
            "              \"workitemName\": \"test\",\n" +
            "              \"taskType\": \"task Type\",\n" +
            "              \"organizationName\": \"super\",\n" +
            "              \"createdBy\": \"user\",\n" +
            "              \"createdDate\": \"2014\\/02\\/03\",\n" +
            "              \"dealerType\": \"dealer type\"\n" +
            "            }\n" +
            "          ]\n" +
            "        }";


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
                return _entity.haveChild();
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
