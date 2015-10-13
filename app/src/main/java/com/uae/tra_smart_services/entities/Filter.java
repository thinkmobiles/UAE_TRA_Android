package com.uae.tra_smart_services.entities;

/**
 * Created by ak-buffalo on 14.08.15.
 *
 * Filter define rule to check of compliance of the domainStrValue name
 * */
public interface Filter<T>{
    boolean check(T _data);
}