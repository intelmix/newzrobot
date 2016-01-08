package com.intelmix.newzrobot.crawler;

import java.util.*;

/**
 * This class represents an in-memory cache which stores some data items
 * each with its own timestamp. The feature of this class is that it only
 * holds last "n" newest data items
 */
public class CappedCache {
    private int size = 0;

    TreeMap<Long, Object> map = new TreeMap<Long, Object>();

    public CappedCache(int size) {
         this.size = size;
    }

    /**
     * Add a new item to the cache while preserving its limited size.
     * In case will delete old items
     */
    public void addItem(long epoch, Object value) {
        map.put(epoch, value);

        //check size of map
        while ( map.size() > this.size ) map.remove(map.firstKey());
    }

    /**
     * Returns items in the cache, sorted by their time.
     */
    public Collection<Object> getItems() {
        return map.values();
   }
}


