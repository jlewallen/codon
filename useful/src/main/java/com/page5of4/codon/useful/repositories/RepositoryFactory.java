package com.page5of4.codon.useful.repositories;

public interface RepositoryFactory {

   Repository<?, ?> createRepository(Class<? extends Repository<?, ?>> repositoryClass);

}
