package com.page5of4.codon.persistence.voldemort.extender;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import voldemort.client.ClientConfig;
import voldemort.client.SocketStoreClientFactory;

import com.page5of4.codon.persistence.voldemort.VoldemortRepositoryFactory;
import com.page5of4.codon.useful.repositories.Repositories;
import com.page5of4.codon.useful.repositories.RepositoryFactory;
import com.page5of4.nagini.SerializerFactories;

public class StoreClientFactory implements ManagedServiceFactory {
   private static final Logger logger = LoggerFactory.getLogger(StoreClientFactory.class);
   private final Map<String, ServiceRegistration> registrations = new ConcurrentHashMap<String, ServiceRegistration>();
   private final BundleContext bundleContext;

   public Map<String, ServiceRegistration> getRegistrations() {
      return Collections.unmodifiableMap(registrations);
   }

   public StoreClientFactory(BundleContext bundleContext) {
      super();
      this.bundleContext = bundleContext;
   }

   @Override
   public String getName() {
      return "com.page5of4.codon.persistence.voldemort.Config";
   }

   @Override
   public void updated(String pid, @SuppressWarnings("rawtypes") Dictionary p) throws ConfigurationException {
      deleted(pid);

      String bootstrapUrl = (String)p.get("bootstrap.url");
      logger.info("Configuring '{}' at '{}'", pid, bootstrapUrl);

      SocketStoreClientFactory storeClientFactory = new SocketStoreClientFactory(new ClientConfig().setSerializerFactory(SerializerFactories.defaultChain()).setBootstrapUrls(bootstrapUrl));
      RepositoryFactory factory = new VoldemortRepositoryFactory(storeClientFactory);
      Dictionary<String, String> properties = new Hashtable<String, String>();
      properties.put(Repositories.PERSISTENCE_PROVIDER_PROPERTY, factory.getClass().getName());
      registrations.put(pid, bundleContext.registerService(RepositoryFactory.class.getName(), factory, properties));
   }

   @Override
   public void deleted(String pid) {
      ServiceRegistration registration = registrations.remove(pid);
      if(registration != null) {
         logger.info("Unregistering {}", pid);
         registration.unregister();
      }
   }
}
