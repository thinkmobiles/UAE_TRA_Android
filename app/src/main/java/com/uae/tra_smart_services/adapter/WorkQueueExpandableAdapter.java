package com.uae.tra_smart_services.adapter;

import android.content.Context;
import android.graphics.PathEffect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.uae.tra_smart_services.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by and on 19.11.15.
 */
public class WorkQueueExpandableAdapter extends ExpandableRecyclerAdapter<WorkQueueExpandableAdapter.CrimeParentViewHolder, WorkQueueExpandableAdapter.CrimeChildViewHolder> {






    public class DataModel {
        public String sectorField;
        List<Map<String,String>> dataModel;
    }


    public static class DataSource {
        public Map<String, String> dataSource;
        public static class List extends ArrayList<DataSource> {}
    }

    public static class NetModelToExpRecyclerViewModelAdapter implements Comparator<String> {
        public List<ParentObject> mParentObjects;
        public WorkQueueDataModel datamodel;

        public NetModelToExpRecyclerViewModelAdapter(WorkQueueDataModel _datamodel){
            datamodel = _datamodel;
        }

        public Map<String, List<Map<String, String>>> prepareData(){
            Map<String, List<Map<String, String>>> unique = new HashMap<>();
            header: for(final Map<String, String> content : datamodel.dataContent){
                Set<String> keys = unique.keySet();
                String valueToFind = content.get(datamodel.additional.get("sectorField"));
                if(unique.isEmpty()){
                    unique.put(valueToFind, new ArrayList<Map<String, String>>(){{add(content);}});
                    continue;
                }
                for (String key : keys){
                    if(compare(valueToFind, key) > 0){
                        unique.get(key).add(content);
                        continue header ;
                    }
                }
                unique.put(valueToFind, new ArrayList<Map<String, String>>(){{add(content);}});
            }
            return unique;
        }

        @Override
        public int compare(String lhs, String rhs) {
            return (lhs.hashCode() == rhs.hashCode()) ? 1 : -1;
        }

        public void sortData(String _key){

        }

        public List<ParentObject> getParentObjects(){
            return (mParentObjects = new ArrayList<ParentObject>(){
                {
                    for(final Map.Entry<String, List<Map<String, String>>> item : prepareData().entrySet()){
                        add(new Header() {
                            @Override
                            public List<Object> getChildObjectList() {
                                return new ArrayList<Object>(item.getValue());
                            }
                            @Override
                            public void setChildObjectList(List<Object> list) {

                            }
                            @Override
                            public String getTile(){
                                return item.getKey();
                            }
                        });
                    }
                }
            });
        }
        public List<ParentObject> getParentObjects(String _key){
            sortData(_key);
            return mParentObjects; // TODO Customize method to have ability to return grouped objects
        }
    }

    public interface Header extends ParentObject {
        String getTile();
    }

    private LayoutInflater mInflater;
    private NetModelToExpRecyclerViewModelAdapter netModelToExpRecyclerViewModelAdapter;

    public WorkQueueExpandableAdapter(Context context, NetModelToExpRecyclerViewModelAdapter netModelToExpRecyclerViewModelAdapter) {
        super(context, netModelToExpRecyclerViewModelAdapter.getParentObjects());
        mInflater = LayoutInflater.from(context);
    }

    public void groupBy(String _key){
        mParentItemList = netModelToExpRecyclerViewModelAdapter.getParentObjects(_key);
        notifyDataSetChanged();
    }

    @Override
    public CrimeParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.layout_tree_header, viewGroup, false);
        return new CrimeParentViewHolder(view);
    }

    @Override
    public CrimeChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.layout_work_queue_list_item_child, viewGroup, false);
        return new CrimeChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(WorkQueueExpandableAdapter.CrimeParentViewHolder crimeParentViewHolder, int i, Object parentListItem) {
//        Crime crime = (Crime) parentListItem;
//        crimeParentViewHolder.mCrimeTitleTextView.setText(crime.getTitle());
    }

    @Override
    public void onBindChildViewHolder(CrimeChildViewHolder crimeChildViewHolder, int i, Object childListItem) {
//        CrimeChild crimeChild = (CrimeChild) childListItem;
//        crimeChildViewHolder.mCrimeDateText.setText(crimeChild.getDate().toString());
//        crimeChildViewHolder.mCrimeSolvedCheckBox.setChecked(crimeChild.isSolved());
    }

    public class CrimeParentViewHolder extends ParentViewHolder {

//        public TextView mCrimeTitleTextView;
//        public ImageButton mParentDropDownArrow;

        public CrimeParentViewHolder(View itemView) {
            super(itemView);
//
//            mCrimeTitleTextView = (TextView) itemView.findViewById(R.id.parent_list_item_crime_title_text_view);
//            mParentDropDownArrow = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
        }
    }

    public class CrimeChildViewHolder extends ChildViewHolder {

//        public TextView mCrimeDateText;
//        public CheckBox mCrimeSolvedCheckBox;

        public CrimeChildViewHolder(View itemView) {
            super(itemView);
//
//            mCrimeDateText = (TextView) itemView.findViewById(R.id.child_list_item_crime_date_text_view);
//            mCrimeSolvedCheckBox = (CheckBox) itemView.findViewById(R.id.child_list_item_crime_solved_check_box);
        }
    }

    public class WorkQueueDataModel {
        public Integer order;
        public String name;
        public String inputType;
        public HashMap<String, String> additional;
        public boolean required;
        public String validateAs;
        public String _id;
        public ArrayList<HashMap<String,String>> dataSource;
        public HashMap<String, String> displayName;
        public HashMap<String, String> placeHolder;
        public ArrayList<HashMap<String, String>> dataContent;
    }
}