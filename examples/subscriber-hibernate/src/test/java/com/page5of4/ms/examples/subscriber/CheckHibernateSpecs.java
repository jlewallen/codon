package com.page5of4.ms.examples.subscriber;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.page5of4.ms.examples.subscriber.model.User;

public class CheckHibernateSpecs {

   @Test
   public void test() {
      EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa.example");
      EntityManager em = entityManagerFactory.createEntityManager();
      EntityTransaction transaction = em.getTransaction();
      transaction.begin();
      User user = new User(UUID.randomUUID(), "Jacob", "Lewallen", new Date());
      em.persist(user);
      List l1 = em.createQuery("FROM User").getResultList();
      System.out.println(l1.size());
      List<User> l2 = em.createQuery("FROM User", User.class).getResultList();
      System.out.println(l2.size());
      transaction.commit();
   }

}
