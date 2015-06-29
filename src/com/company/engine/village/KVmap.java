package com.company.engine.village;

import java.util.HashMap;
import java.util.Map;

public class KVmap<K,V> {
    private Map<K,V> map = new HashMap<K,V>();

    public void put(K key, V value){
        map.put( key, value );
    }
    public V getV( K key ){
        return map.get( key );
    }
    public K getK( V value ){
        for ( Map.Entry<K,V> entry : map.entrySet() ){
            if (entry.getValue().equals( value ))
                return entry.getKey();
        }
        return null;
    }
}
