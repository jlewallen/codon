package com.page5of4.codon.tests;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.maven;

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

   @Override
   protected Option container() {
      return karafDistributionConfiguration().frameworkUrl(maven().groupId("com.page5of4.codon").artifactId("codon-karaf").version("1.0.0-SNAPSHOT").type("tar.gz")).karafVersion("2.2.8").name("Codon");
   }

   @Before
   public void before() {
      executor().executeCommand("features:install codon-dependencies");
      executor().executeCommand("features:install codon-persistence-jpa");
      executor().executeCommand("features:install codon-core");
   }

   @Test
   public void test() {
      Bus bus = helper().getBus();
      System.out.println(executor().executeCommand("osgi:list -s -t 0"));
   }
}
