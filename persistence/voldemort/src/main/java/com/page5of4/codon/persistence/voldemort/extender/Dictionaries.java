package com.page5of4.codon.persistence.voldemort.extender;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.Maps;

public class Dictionaries {
   public static <K, V> Map<K, V> asMap(Dictionary d) {
      return new Dictionaries.DictionaryAsMap<K, V>(d);
   }

   public static Collection<ExtractedMap> extractPrefixedMaps(Dictionary d, Pattern pattern) {
      Map<String, ExtractedMap> maps = Maps.newHashMap();
      Map<String, Object> map = Maps.newHashMap();
      for(Entry<String, Object> entry : Dictionaries.<String, Object> asMap(d).entrySet()) {
         Matcher matcher = pattern.matcher(entry.getKey());
         if(matcher.find()) {
            String mapKey = matcher.group(1);
            String propertyKey = matcher.group(2);
            if(!maps.containsKey(mapKey)) {
               maps.put(mapKey, new ExtractedMap(mapKey, new HashMap<String, Object>()));
            }
            maps.get(mapKey).getMap().put(propertyKey, entry.getValue());
         }
      }
      return maps.values();
   }

   public static class ExtractedMap {
      private final String key;
      private final Map<String, Object> map;

      public String getKey() {
         return key;
      }

      public Map<String, Object> getMap() {
         return map;
      }

      public ExtractedMap(String key, Map<String, Object> map) {
         super();
         this.key = key;
         this.map = map;
      }
   }

   public static class DictionaryAsMap<U, V> extends AbstractMap<U, V> {
      private final Dictionary<U, V> dict;

      public DictionaryAsMap(Dictionary<U, V> dict) {
         this.dict = dict;
      }

      @Override
      public Set<Entry<U, V>> entrySet() {
         return new AbstractSet<Entry<U, V>>() {
            @Override
            public Iterator<Entry<U, V>> iterator() {
               final Enumeration<U> e = dict.keys();
               return new Iterator<Entry<U, V>>() {
                  private U key;

                  @Override
                  public boolean hasNext() {
                     return e.hasMoreElements();
                  }

                  @Override
                  public Entry<U, V> next() {
                     key = e.nextElement();
                     return new KeyEntry(key);
                  }

                  @Override
                  public void remove() {
                     if(key == null) {
                        throw new IllegalStateException();
                     }
                     dict.remove(key);
                  }
               };
            }

            @Override
            public int size() {
               return dict.size();
            }
         };
      }

      @Override
      public V put(U key, V value) {
         return dict.put(key, value);
      }

      class KeyEntry implements Map.Entry<U, V> {

         private final U key;

         KeyEntry(U key) {
            this.key = key;
         }

         @Override
         public U getKey() {
            return key;
         }

         @Override
         public V getValue() {
            return dict.get(key);
         }

         @Override
         public V setValue(V value) {
            return dict.put(key, value);
         }
      }
   }
}
