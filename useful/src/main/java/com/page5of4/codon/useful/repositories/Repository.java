package com.page5of4.codon.useful.repositories;

public interface Repository<K, V> {
   V get(K key);

   void delete(K key);

   void add(K key, V value);
}
