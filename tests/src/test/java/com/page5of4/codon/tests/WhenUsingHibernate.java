package com.page5of4.codon.tests;

import static com.page5of4.codon.support.BundleAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenUsingHibernate extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration() };
   }

   @Before
   public void before() {
      executor().executeCommand("osgi:install -s mvn:javax.persistence/com.springsource.javax.persistence/2.0.0/");
      executor().executeCommand("osgi:install -s mvn:javax.transaction/com.springsource.javax.transaction/1.1.0/");
      executor().executeCommand("osgi:install -s mvn:org.apache.commons/com.springsource.org.apache.commons.collections/3.2.1");
      executor().executeCommand("osgi:install -s mvn:com.page5of4.codon.bundles/com.page5of4.codon.bundles.hibernate/4.0.0");
   }

   @Test
   public void bundle_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.codon.bundles.hibernate")).isActive();
   }
}
