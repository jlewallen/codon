package com.page5of4.codon.examples.subscriber.impl;

import java.io.PrintWriter;
import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {
   private static final Logger logger = LoggerFactory.getLogger(Activator.class);
   private ServiceRegistration serviceRegistration;

   @Override
   public void start(BundleContext context) throws Exception {
      Dictionary<String, Object> props = new Hashtable<String, Object>();
      props.put("com.page5of4.codon.examples.subscriber.shell.group.id", "jpa");
      props.put("com.page5of4.codon.examples.subscriber.shell.group.name", "Example");
      props.put("com.page5of4.codon.examples.subscriber.shell.commands", new String[][] {
      { "lsdudes", "lsdude - show dudes" },
            // { "deldude", "deldude <id> - delete dude" },
            // { "adddude", "adddude <first_name> <last_name> - add dude" }
      });
      CommandLineService service = new CommandLineService(context);
      serviceRegistration = context.registerService(CommandLineService.class.getName(), service, props);

      logger.info("Registered...");
      service.lsdudes(new PrintWriter(System.out), new String[0]);
      logger.info("Ready...");
   }

   @Override
   public void stop(BundleContext context) throws Exception {
      serviceRegistration.unregister();
   }
}
