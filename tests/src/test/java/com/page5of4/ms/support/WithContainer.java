package com.page5of4.ms.support;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.logLevel;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import javax.inject.Inject;

import org.openengsb.labs.paxexam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.ProbeBuilder;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
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

   public CompositeOption commonConfiguration() {
      return new DefaultCompositeOption(
            karafDistributionConfiguration().frameworkUrl(maven().groupId("org.apache.karaf").artifactId("apache-karaf").versionAsInProject().type("zip")).karafVersion("2.2.4").name("Apache Karaf"),
            runtimeFolderOption(),
            systemProperties(),
            logLevel(LogLevelOption.LogLevel.INFO),
            festAssert(),
            junitBundles());

   }

   private Option systemProperties() {
      if(false) return new Option() {};
      return editConfigurationFilePut("etc/system.properties", "page5of4.main.version", "1.0.0-SNAPSHOT");

   }

   private Option runtimeFolderOption() {
      if(true) return new Option() {};
      return keepRuntimeFolder();
   }

   public CompositeOption festAssert() {
      return new DefaultCompositeOption(
            mavenBundle().groupId("org.easytesting").artifactId("fest-assert").versionAsInProject(),
            mavenBundle().groupId("org.easytesting").artifactId("fest-util").versionAsInProject());
   }
}
