package com.page5of4.codon.tests;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.osgi.framework.BundleContext;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.Provision;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class OutOfTheBoxSpecs extends WithContainer {
   @Inject
   private BundleContext bundleContext;

   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      Provision.with(executor()).base().core();
   }

   @Test
   public void test() {
      Bus bus = helper().getBus();
      System.out.println(executor().executeCommand("osgi:list -s -t 0"));
   }
}
