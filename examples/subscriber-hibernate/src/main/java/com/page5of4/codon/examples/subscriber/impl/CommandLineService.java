package com.page5of4.codon.examples.subscriber.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jpa.EntityManagerFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.examples.subscriber.model.Person;

public class CommandLineService {
   private static final Logger logger = LoggerFactory.getLogger(CommandLineService.class);
   private static final Object PERSISTENCE_UNIT = "jpa.example";
   private final BundleContext bundleContext;

   public CommandLineService(BundleContext bundleContext) {
      super();
      this.bundleContext = bundleContext;
   }

   public void listDudes() {
      try {
         ServiceReference reference = getEntityManagerFactoryServiceReference();
         try {
            EntityManagerFactory emf = (EntityManagerFactory)bundleContext.getService(reference);
            EntityManager em = emf.createEntityManager();
            {
               Person user = new Person(UUID.randomUUID(), "Jacob", "Lewallen", new Date());
               EntityTransaction transaction = em.getTransaction();
               transaction.begin();
               em.persist(user);
               transaction.commit();
            }
            {
               Query query = em.createQuery("FROM Person");
               List<?> list = query.getResultList();
               logger.info("{} {}", list, list.getClass());
               for(Object r : list) {
                  logger.info("{}", r);
               }
            }
            {
               TypedQuery<Person> query = em.createQuery("FROM Person", Person.class);
               List<Person> found = query.getResultList();
               logger.info(String.format("People: %d", found.size()));
               for(Object dude : found) {
                  logger.info(String.format("%s %s", dude, dude.getClass()));
               }
            }
            em.close();
         }
         finally {
            bundleContext.ungetService(reference);
         }
      }
      catch(Exception e) {
         logger.error("Error", e);
      }
   }

   private ServiceReference getEntityManagerFactoryServiceReference() throws Exception {
      ServiceReference[] serviceReferences = bundleContext.getServiceReferences(EntityManagerFactory.class.getName(), String.format("(%s=%s)", EntityManagerFactoryBuilder.JPA_UNIT_NAME, PERSISTENCE_UNIT));
      if(serviceReferences != null && serviceReferences.length > 0) {
         return serviceReferences[0];
      }
      else {
         throw new Exception("EntityManagerFactory is not available");
      }
   }
}
