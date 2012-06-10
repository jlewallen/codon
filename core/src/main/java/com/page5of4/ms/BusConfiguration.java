package com.page5of4.ms;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class BusConfiguration {
   private static final String BUS_OWNER = "bus.owner.";
   private final Map<String, String> properties = new HashMap<String, String>();
   private final String applicationName;

   public String getApplicationName() {
      return applicationName;
   }

   @Value("classpath:/META-INF/spring/application.properties")
   public void setPropertiesSource(Resource resource) {
      try {
         Properties loaded = new Properties();
         loaded.load(resource.getInputStream());
         properties.putAll((Map)loaded);
      }
      catch(IOException e) {
         throw new BusException("Error loading: " + resource, e);
      }
   }

   public BusConfiguration(String applicationName) {
      super();
      this.applicationName = applicationName;
      if(applicationName == null || applicationName.length() == 0) throw new BusException("Application name is required.");
   }

   public String getOwnerAddress(String messageType) {
      String selected = null;
      for(Map.Entry<String, String> entry : properties.entrySet()) {
         if(entry.getKey().startsWith(BUS_OWNER)) {
            String keysPackage = entry.getKey().replace(BUS_OWNER, "");
            if(messageType.startsWith(keysPackage) && (selected == null || selected.length() < keysPackage.length())) {
               selected = entry.getKey();
            }
         }
      }
      if(selected != null) {
         return properties.get(selected);
      }
      throw new BusException(String.format("Unable to locate Owner for '%s'", messageType));
   }

   public String getLocalAddress(String messageType) {
      return String.format("%s.{messageType}", applicationName);
   }
}
