package com.page5of4.ms.tests;

import static org.fest.assertions.Assertions.assertThat;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.logLevel;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openengsb.labs.paxexam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.ms.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenRunningPublisherAndSubscriber extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(), keepRuntimeFolder(), logLevel(LogLevelOption.LogLevel.INFO), festAssert(), junitBundles() };
   }

   @Before
   public void before() {
      System.err.println(executor().executeCommand("features:listurl"));
      System.err.println(executor().executeCommand("features:addurl " + "mvn:org.apache.camel.karaf/apache-camel/2.9.2/xml/features"));
      System.err.println(executor().executeCommand("features:addurl " + "mvn:org.apache.activemq/activemq-karaf/5.5.0/xml/features"));
      System.err.println(executor().executeCommand("features:addurl " + "mvn:com.page5of4.ms/core/1.0.0-SNAPSHOT/xml/features"));
      // mvn:org.apache.karaf.assemblies.features/standard/2.2.4/xml/features,
      // mvn:org.apache.karaf.assemblies.features/enterprise/2.2.4/xml/features
      // mvn:org.apache.servicemix.nmr/apache-servicemix-nmr/1.5.0/xml/features
      // mavenBundle().groupId("com.page5of4.ms").artifactId("core").versionAsInProject().start(),
      // mavenBundle().groupId("com.page5of4.ms.examples").artifactId("publisher").versionAsInProject().start(),
      // mavenBundle().groupId("com.page5of4.ms.examples").artifactId("subscriber").versionAsInProject().start(false)
   }

   @Test
   public void bundle_is_installed() throws InterruptedException {
      System.out.println(executor().executeCommand("features:install camel-jms"));
      System.out.println(executor().executeCommand("features:install spring-dm"));
      System.out.println(executor().executeCommand("features:install spring-web"));
      System.out.println(executor().executeCommand("features:install camel-jms"));
      System.out.println(executor().executeCommand("features:install camel-jaxb"));
      System.out.println(executor().executeCommand("features:install camel-spring"));
      System.out.println(executor().executeCommand("features:install activemq"));
      System.out.println(executor().executeCommand("osgi:install -s mvn:org.apache.activemq/activemq-camel/5.5.0"));

      System.out.println(executor().executeCommand("features:install ms-core"));
      System.out.println(executor().executeCommand("features:install ms-example"));
      System.out.println(executor().executeCommand("osgi:list -t 0 -s"));
      Thread.sleep(10000);
      assertThat(executor().getInstalledBundle("com.page5of4.ms.examples.publisher")).isNotNull();
   }
}
