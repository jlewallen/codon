package com.page5of4.codon.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.page5of4.codon.useful.repositories.Repository;

public class JpaRepository<K, V> implements Repository<K, V> {
   @PersistenceContext
   private EntityManager entityManager;
   private final Class<V> entityClass;

   public JpaRepository(EntityManager entityManager, Class<V> entityClass) {
      super();
      this.entityManager = entityManager;
      this.entityClass = entityClass;
   }

   public JpaRepository(Class<V> entityClass) {
      super();
      this.entityClass = entityClass;
   }

   @Override
   public V get(K key) {
      return entityManager.find(entityClass, key);
   }

   @Override
   public void delete(K key) {
      entityManager.remove(entityManager.find(entityClass, key));
   }

   @Override
   public void add(K key, V project) {
      entityManager.persist(project);
   }
}
