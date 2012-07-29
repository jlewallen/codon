package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenInstallingMustacheMvc extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            mavenBundle().groupId("org.codehaus.jackson").artifactId("jackson-core-asl").version("1.7.5"),
            mavenBundle().groupId("org.codehaus.jackson").artifactId("jackson-mapper-asl").version("1.7.5"),
            mavenBundle().groupId("com.google.guava").artifactId("guava").version("12.0"),
            mavenBundle().groupId("commons-io").artifactId("commons-io").versionAsInProject(),
            mavenBundle().groupId("commons-lang").artifactId("commons-lang").versionAsInProject()
      };
   }

   @Before
   public void before() {
      executor().executeCommand("features:install war");
      executor().executeCommand("features:install spring-web");
      executor().executeCommand("osgi:install -s mvn:com.page5of4.commons/mustache-mvc/1.2.3-SNAPSHOT");
   }

   @Test
   public void bundle_is_active() {
      assertThat(executor().getInstalledBundle("com.page5of4.commons.mustache-mvc")).isActive();
   }
}
