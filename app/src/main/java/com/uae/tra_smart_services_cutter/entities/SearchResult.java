package com.uae.tra_smart_services_cutter.entities;

import android.content.Context;
import android.text.SpannableStringBuilder;

import com.uae.tra_smart_services_cutter.global.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ak-buffalo on 09.09.15.
 */
public class SearchResult {

    private List<SearchResultItem> searchResultItems;

    public SearchResult(){
        searchResultItems = new ArrayList<>();
    }

    public void initFromServicesList(List<Service> _services, Context _context){
        for(Service service : _services){
            addItem(service, service.getTitle(_context));
        }
    }

    private void addItem(Service _service, String _text){
        searchResultItems.add(new SearchResultItem(_service, _text));
    }

    public List<SearchResultItem> getSearchResultItems(){
        return searchResultItems;
    }

    public class SearchResultItem {
        private Service service;
        private SpannableStringBuilder spannedText;
        private String originalText;

        SearchResultItem(Service _service, String _text){
            service = _service;
            originalText = _text;
        }

        public Service getBindedService(){
            return service;
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
