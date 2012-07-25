package com.page5of4.codon.persistence.memory;

import com.page5of4.codon.useful.repositories.Repository;
import com.page5of4.codon.useful.repositories.RepositoryFactory;

public class MemoryRepositoryFactory implements RepositoryFactory {
   @Override
   public Repository<?, ?> createRepository(Class<? extends Repository<?, ?>> repositoryClass) {
      return new MemoryRepository<Object, Object>();
   }
}
