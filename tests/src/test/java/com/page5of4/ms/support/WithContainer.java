package com.page5of4.ms.support;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.ProbeBuilder;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class WithContainer {
   @Inject
   private BundleContext bundleContext;
   private CommandExecutor executor;

   public CommandExecutor executor() {
      if(executor == null) {
         executor = new CommandExecutor(bundleContext);
      }
      return executor;
   }

   @ProbeBuilder
   public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
      probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*,org.apache.felix.service.*;status=provisional");
      return probe;
   }

   public Option commonConfiguration() {
      return karafDistributionConfiguration().frameworkUrl(maven().groupId("org.apache.karaf").artifactId("apache-karaf").versionAsInProject().type("zip")).karafVersion("2.2.4").name("Apache Karaf");

   }

   public Option festAssert() {
      return mavenBundle().groupId("org.easytesting").artifactId("fest-assert").versionAsInProject();
   }
}
