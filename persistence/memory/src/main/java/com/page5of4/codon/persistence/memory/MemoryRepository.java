package com.page5of4.codon.persistence.memory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.page5of4.codon.useful.repositories.ListableRepository;

public class MemoryRepository<K, V> implements ListableRepository<K, V> {
   private final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();

   @Override
   public V get(K key) {
      return map.get(key);
   }

   @Override
   public void delete(K key) {
      map.remove(key);
   }

   @Override
   public void add(K key, V value) {
      map.put(key, value);
   }

   @Override
   public Collection<K> allKeys() {
      return map.keySet();
   }
}
