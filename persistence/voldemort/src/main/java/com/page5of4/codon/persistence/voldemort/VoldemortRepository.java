package com.page5of4.codon.persistence.voldemort;

import voldemort.client.StoreClient;

import com.page5of4.codon.useful.repositories.Repository;

public class VoldemortRepository<K, V> implements Repository<K, V> {
   private final StoreClient<K, V> storeClient;

   public VoldemortRepository(StoreClient<K, V> storeClient) {
      super();
      this.storeClient = storeClient;
   }

   @Override
   public V get(K key) {
      return storeClient.getValue(key);
   }

   @Override
   public void delete(K key) {
      storeClient.delete(key);
   }

   @Override
   public void add(K key, V value) {
      storeClient.put(key, value);
   }
}
