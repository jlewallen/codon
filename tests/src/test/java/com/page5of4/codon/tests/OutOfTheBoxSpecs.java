package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;
import org.ops4j.pax.swissbox.core.ContextClassLoaderUtils;
import org.osgi.framework.BundleContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;

import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class OutOfTheBoxSpecs extends WithContainer {
   @Inject
   private BundleContext bundleContext;

   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            featuresBoot("config", "codon-dependencies", "codon-core")
      };
   }

   @Test
   public void test() {
      helper().getBus();
   }

   @Test
   public void configured() throws Exception {
      ContextClassLoaderUtils.doWithClassLoader(getClass().getClassLoader(), new Callable<Object>() {
         @Override
         public Object call() throws Exception {
            OsgiBundleXmlApplicationContext parent = new OsgiBundleXmlApplicationContext();
            parent.setBundleContext(bundleContext);
            parent.refresh();
            AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
            applicationContext.setParent(parent);
            applicationContext.register(ProbeConfig.class);
            applicationContext.refresh();
            parent.destroy();
            return new Object();
         }
      });
   }
}
