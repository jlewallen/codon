package com.page5of4.codon.extender;

import java.util.concurrent.Callable;

import org.osgi.framework.BundleContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;

import com.page5of4.codon.extender.config.BundleConfig;
import com.page5of4.codon.extender.config.OsgiConfig;

public class OsgiApplicationContextFactory {
   private final BundleContext extenderContext;
   private AbstractApplicationContext applicationContext;

   public OsgiApplicationContextFactory(BundleContext extenderContext) {
      super();
      this.extenderContext = extenderContext;
   }

   public static AbstractApplicationContext createApplicationContext(BundleContext extenderContext) {
      OsgiBundleXmlApplicationContext applicationContext = new OsgiBundleXmlApplicationContext();
      applicationContext.setBundleContext(extenderContext);
      applicationContext.refresh();

      AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
      childContext.setClassLoader(applicationContext.getClassLoader());
      childContext.setParent(applicationContext);
      childContext.register(BundleConfig.class);
      childContext.register(OsgiConfig.class);
      childContext.refresh();

      return applicationContext;
   }

   public void open() {
      applicationContext = ContextClassLoaderUtils.doWithClassLoader(null, new Callable<AbstractApplicationContext>() {
         @Override
         public AbstractApplicationContext call() throws Exception {
            return createApplicationContext(extenderContext);
         }
      });
   }

   public void close() {
      applicationContext.destroy();
   }
}
