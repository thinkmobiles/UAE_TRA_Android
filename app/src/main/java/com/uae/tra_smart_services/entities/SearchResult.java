package com.uae.tra_smart_services.entities;

import java.util.ArrayList;

/**
 * Created by and on 09.09.15.
 */
public class SearchResult {

    private ArrayList<SearchResultItem> searchResultItems;

    public SearchResult(){
        searchResultItems = new ArrayList<>();
    }

    public void addItem(String _text){
        searchResultItems.add(new SearchResultItem(_text));
    }

    public ArrayList<SearchResultItem> getSearchResultItems(){
        return searchResultItems;
    }

    public class SearchResultItem{
        private String text;

        SearchResultItem(String _text){
            text = _text;
        }

        public String getText() {
            return text;
        }
    }

    public int getCount(){
        return searchResultItems.size();
    }

    public void clearAll(){
        searchResultItems.clear();
    }

    public void addAllItems(ArrayList<SearchResult.SearchResultItem> results){
        searchResultItems.addAll(results);
    }
}
