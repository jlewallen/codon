package com.page5of4.codon.persistence.voldemort;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
      String storeName = getStoreNameFor(repositoryClass);
      return new VoldemortRepository<Object, Object>(storeClientFactory.getStoreClient(storeName));
   }

   private String getStoreNameFor(Class<? extends Repository<?, ?>> repositoryClass) {
      Matcher matcher = Pattern.compile("([^\\.]+)Repository").matcher(repositoryClass.getSimpleName());
      if(matcher.matches()) {
         return matcher.group(1).toLowerCase();
      }
      throw new RuntimeException("Unable to infer StoreName for " + repositoryClass);
   }
}
