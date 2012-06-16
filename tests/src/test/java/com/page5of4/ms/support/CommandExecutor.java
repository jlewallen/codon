package com.page5of4.ms.support;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class CommandExecutor {
   static final Long COMMAND_TIMEOUT = 120000L;
   static final Long DEFAULT_TIMEOUT = 20000L;
   static final Long SERVICE_TIMEOUT = 30000L;

   static final String DEBUG_OPTS = " --java-opts \"-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=%s\"";

   ExecutorService executor = Executors.newCachedThreadPool();
   BundleContext bundleContext;

   public CommandExecutor(BundleContext bundleContext) {
      super();
      this.bundleContext = bundleContext;
   }

   public String executeCommand(final String command) {
      return executeCommand(command, COMMAND_TIMEOUT, false);
   }

   public String executeCommand(final String command, final Long timeout, final Boolean silent) {
      String response;
      final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      final PrintStream printStream = new PrintStream(byteArrayOutputStream);
      final CommandProcessor commandProcessor = getOsgiService(CommandProcessor.class);
      final CommandSession commandSession = commandProcessor.createSession(System.in, printStream, System.err);
      FutureTask<String> commandFuture = new FutureTask<String>(
            new Callable<String>() {
               @Override
               public String call() {
                  try {
                     if(!silent) {
                        System.err.println(command);
                     }
                     commandSession.execute(command);
                  }
                  catch(Exception e) {
                     e.printStackTrace(System.err);
                  }
                  printStream.flush();
                  return byteArrayOutputStream.toString();
               }
            });

      try {
         executor.submit(commandFuture);
         response = commandFuture.get(timeout, TimeUnit.MILLISECONDS);
      }
      catch(Exception e) {
         e.printStackTrace(System.err);
         response = "SHELL COMMAND TIMED OUT: ";
      }

      return response;
   }

   public String executeCommands(final String... commands) {
      String response;
      final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      final PrintStream printStream = new PrintStream(byteArrayOutputStream);
      final CommandProcessor commandProcessor = getOsgiService(CommandProcessor.class);
      final CommandSession commandSession = commandProcessor.createSession(System.in, printStream, System.err);
      FutureTask<String> commandFuture = new FutureTask<String>(
            new Callable<String>() {
               @Override
               public String call() {
                  try {
                     for(String command : commands) {
                        System.err.println(command);
                        commandSession.execute(command);
                     }
                  }
                  catch(Exception e) {
                     e.printStackTrace(System.err);
                  }
                  return byteArrayOutputStream.toString();
               }
            });

      try {
         executor.submit(commandFuture);
         response = commandFuture.get(COMMAND_TIMEOUT, TimeUnit.MILLISECONDS);
      }
      catch(Exception e) {
         e.printStackTrace(System.err);
         response = "SHELL COMMAND TIMED OUT: ";
      }

      return response;
   }

   public Bundle getInstalledBundle(String symbolicName) {
      for(Bundle b : bundleContext.getBundles()) {
         if(b.getSymbolicName().equals(symbolicName)) {
            return b;
         }
      }
      for(Bundle b : bundleContext.getBundles()) {
         System.err.println("Bundle: " + b.getSymbolicName());
      }
      throw new RuntimeException("Bundle " + symbolicName + " does not exist");
   }

   public <T> T getOsgiService(Class<T> type, long timeout) {
      return getOsgiService(type, null, timeout);
   }

   public <T> T getOsgiService(Class<T> type) {
      return getOsgiService(type, null, SERVICE_TIMEOUT);
   }

   public <T> T getOsgiService(Class<T> type, String filter, long timeout) {
      ServiceTracker tracker = null;
      try {
         String flt;
         if(filter != null) {
            if(filter.startsWith("(")) {
               flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")" + filter + ")";
            }
            else {
               flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")(" + filter + "))";
            }
         }
         else {
            flt = "(" + Constants.OBJECTCLASS + "=" + type.getName() + ")";
         }
         Filter osgiFilter = FrameworkUtil.createFilter(flt);
         tracker = new ServiceTracker(bundleContext, osgiFilter, null);
         tracker.open(true);
         // Note that the tracker is not closed to keep the reference
         // This is buggy, as the service reference may change i think
         Object svc = type.cast(tracker.waitForService(timeout));
         if(svc == null) {
            Dictionary<?, ?> dic = bundleContext.getBundle().getHeaders();
            System.err.println("Test bundle headers: " + explode(dic));

            for(ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, null))) {
               System.err.println("ServiceReference: " + ref);
            }

            for(ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, flt))) {
               System.err.println("Filtered ServiceReference: " + ref);
            }

            throw new RuntimeException("Gave up waiting for service " + flt);
         }
         return type.cast(svc);
      }
      catch(InvalidSyntaxException e) {
         throw new IllegalArgumentException("Invalid filter", e);
      }
      catch(InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   private static String explode(Dictionary<?, ?> dictionary) {
      Enumeration<?> keys = dictionary.keys();
      StringBuffer result = new StringBuffer();
      while(keys.hasMoreElements()) {
         Object key = keys.nextElement();
         result.append(String.format("%s=%s", key, dictionary.get(key)));
         if(keys.hasMoreElements()) {
            result.append(", ");
         }
      }
      return result.toString();
   }

   private static Collection<ServiceReference> asCollection(ServiceReference[] references) {
      return references != null ? Arrays.asList(references) : Collections.<ServiceReference> emptyList();
   }
}
