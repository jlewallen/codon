package com.page5of4.codon.useful.repositories;

import java.util.Collection;

public interface ListableRepository<K, V> extends Repository<K, V> {
   Collection<K> allKeys();
}
