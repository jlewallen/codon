package com.page5of4.codon.extender;

import java.util.concurrent.Callable;

public class ContextClassLoaderUtils {
   private ContextClassLoaderUtils() {

   }

   /**
    * Executes a piece of code (callable.call) using a specific class loader set as context class loader. If the curent
    * thread context clas loader is already set, it will be restored after execution.
    * 
    * @param classLoader
    *           clas loader to be used as context clas loader during call.
    * @param callable
    *           piece of code to be executed using the clas loader
    * @return return from callable
    * @throws Exception
    *            re-thrown from callable
    */
   public static <V> V doWithClassLoader(final ClassLoader classLoader, final Callable<V> callable) {
      Thread currentThread = null;
      ClassLoader backupClassLoader = null;
      try {
         /*if(classLoader != null)*/{
            currentThread = Thread.currentThread();
            backupClassLoader = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(classLoader);
         }
         return callable.call();
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
      finally {
         /*if(backupClassLoader != null)*/{
            currentThread.setContextClassLoader(backupClassLoader);
         }
      }
   }
}
