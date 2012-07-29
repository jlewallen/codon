package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.tests.support.Provision;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningExampleApplicationWithWebSpecs extends WithContainer {
   private static final Logger logger = LoggerFactory.getLogger(WhenRunningExampleApplicationWithWebSpecs.class);

   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(), featuresBoot("config", "codon-dependencies", "codon-core", "activemq", "activemq-spring") };
   }

   @Before
   public void before() {
      Provision.with(executor()).broker().base().core().hibernate().web();
      executor().executeCommands("features:install codon-example-webapp");
      pause();
   }

   @Test
   public void bundles_are_installed_and_active() {
      String[] expected = new String[] {
            "com.page5of4.codon.examples.messages",
            "com.page5of4.codon.examples.application",
            "com.page5of4.codon.examples.webapp"
      };
      for(String name : expected) {
         logger.info("Checking {}", name);
         assertThat(executor().getInstalledBundle(name)).isActive();
      }

      try {
         URL url = new URL("http://127.0.0.1:8181/webapp/resources/templates.js");
         InputStream stream = url.openStream();
         System.out.println(IOUtils.toString(stream));
      }
      catch(Exception e) {
         throw new RuntimeException(e);
      }
   }

   @After
   public void after() {
      executor().executeCommands("features:uninstall codon-example-webapp");
   }
}
