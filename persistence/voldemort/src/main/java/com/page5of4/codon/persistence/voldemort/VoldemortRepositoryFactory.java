package com.page5of4.codon.persistence.voldemort;

import voldemort.client.StoreClientFactory;

import com.page5of4.codon.useful.repositories.Repository;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

public class VoldemortRepositoryFactory implements RepositoryFactory {
   private final StoreClientFactory storeClientFactory;

   public VoldemortRepositoryFactory(StoreClientFactory storeClientFactory) {
      super();
      this.storeClientFactory = storeClientFactory;
   }

   @Override
   public Repository<?, ?> createRepository(Class<? extends Repository<?, ?>> repositoryClass) {
      String storeName = "";
      return new VoldemortRepository<Object, Object>(storeClientFactory.getStoreClient(storeName));
   }
}
