package com.page5of4.ms.subscriptions.impl;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.page5of4.ms.subscriptions.Subscription;
import com.page5of4.ms.subscriptions.SubscriptionStorage;

public class XmlSubscriptionStorage implements SubscriptionStorage {
   private static final String FILENAME = "com.page5of4.ms.subscriptions.xml";

   public XmlSubscriptionStorage() {
      super();
   }

   @Override
   public List<Subscription> findAllSubscriptions() {
      return new ArrayList<Subscription>(read());
   }

   @Override
   public void addSubscriptions(Collection<Subscription> subscriptions) {
      Set<Subscription> all = new HashSet<Subscription>(read());
      all.addAll(subscriptions);
      write(all);
   }

   @Override
   public void removeSubscriptions(Collection<Subscription> subscriptions) {
      Set<Subscription> all = new HashSet<Subscription>(read());
      all.removeAll(subscriptions);
      write(all);
   }

   private Collection<Subscription> read() {
      try {
         File file = new File(getPath());
         if(!file.exists()) return new ArrayList<Subscription>();
         FileInputStream fileStream = new FileInputStream(getPath());
         XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(fileStream));
         Collection<Subscription> o = (Collection<Subscription>)decoder.readObject();
         decoder.close();
         return o;
      }
      catch(Exception e) {
         throw new RuntimeException("Unable to read subscriptions", e);
      }
   }

   private void write(Collection<Subscription> subscriptions) {
      try {
         File file = new File(getPath());
         XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
         encoder.writeObject(subscriptions);
         encoder.close();
      }
      catch(Exception e) {
         throw new RuntimeException("Unable to read subscriptions", e);
      }
   }

   private String getPath() {
      return new File(System.getProperty("user.home"), FILENAME).toString();
   }
}
