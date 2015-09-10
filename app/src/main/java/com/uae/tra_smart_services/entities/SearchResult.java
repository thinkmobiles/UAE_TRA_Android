package com.uae.tra_smart_services.entities;

import android.text.SpannableStringBuilder;

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
        private SpannableStringBuilder spannedText;
        private String originalText;

        SearchResultItem(String _text){
            originalText = _text;
        }

        public String getOriginalText() {
            return originalText;
        }

        public SpannableStringBuilder getSpannedText() {
            return spannedText != null ? spannedText : new SpannableStringBuilder(originalText);
        }
        public void setSpannedText(SpannableStringBuilder _spannedText){
            spannedText = _spannedText;
        }

        @Override
        public String toString() {
            return spannedText != null ? spannedText.toString() : originalText.toString();
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
