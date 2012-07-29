package com.page5of4.codon.tests.support;

import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.doNotModifyLogConfiguration;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.editConfigurationFilePut;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.keepRuntimeFolder;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.logLevel;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.replaceConfigurationFile;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.systemTimeout;
import static org.ops4j.pax.exam.CoreOptions.vmOption;

import java.io.File;

import javax.inject.Inject;

import org.openengsb.labs.paxexam.karaf.options.LogLevelOption;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.junit.ProbeBuilder;
import org.ops4j.pax.exam.options.CompositeOption;
import org.ops4j.pax.exam.options.DefaultCompositeOption;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.Bus;

public class WithContainer {
   protected final Logger logger;
   protected final boolean debuggingEnabled = false;
   protected final boolean keepRuntimeFolder = false;

   @Inject
   private BundleContext bundleContext;
   private CommandExecutor executor;

   public WithContainer() {
      super();
      logger = LoggerFactory.getLogger(getClass());
   }

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
            container(),
            loggingOptions(),
            debuggingOptions(),
            runtimeFolderOption(),
            systemProperties(),
            logLevel(LogLevelOption.LogLevel.INFO),
            commonBundles(),
            festAssert(),
            junitBundles());
   }

   private Option container() {
      return karafDistributionConfiguration().frameworkUrl(maven().groupId("com.page5of4.codon").artifactId("codon-karaf").version("1.0.0-SNAPSHOT").type("tar.gz")).karafVersion("2.2.8").name("Codon");
   }

   private Option commonBundles() {
      return new Option() {};
   }

   private Option loggingOptions() {
      if(true) return new Option() {};
      File source = new File("D:\\SS\\workspace\\com.page5of4\\codon\\karaf\\org.ops4j.pax.logging.exam.cfg");
      return new DefaultCompositeOption(
            doNotModifyLogConfiguration(),
            replaceConfigurationFile("etc/org.ops4j.pax.logging.cfg", source));
   }

   private Option debuggingOptions() {
      if(!debuggingEnabled) return new Option() {};
      return new DefaultCompositeOption(
            vmOption("-Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"),
            systemTimeout(1000 * 60 * 60));
   }

   private Option systemProperties() {
      return new DefaultCompositeOption(
            editConfigurationFilePut("etc/system.properties", "page5of4.project.version", TestsConfiguration.getProjectVersion()),
            editConfigurationFilePut("etc/org.ops4j.pax.url.mvn.cfg", "org.ops4j.pax.url.mvn.repositories", "http://dev.page5of4.com/nexus/content/groups/public"));

   }

   private Option runtimeFolderOption() {
      if(!keepRuntimeFolder) return new Option() {};
      return keepRuntimeFolder();
   }

   public CompositeOption festAssert() {
      return new DefaultCompositeOption(
            mavenBundle().groupId("org.easytesting").artifactId("fest-assert").versionAsInProject(),
            mavenBundle().groupId("org.easytesting").artifactId("fest-util").versionAsInProject());
   }

   public void pause() {
      logger.info("Provisioned");
      try {
         Thread.sleep(2000);
      }
      catch(InterruptedException e) {
      }
   }

   public OsgiHelper helper() {
      return new OsgiHelper(bundleContext);
   }

   public static class OsgiHelper {
      private final BundleContext bundleContext;

      public OsgiHelper(BundleContext bundleContext) {
         super();
         this.bundleContext = bundleContext;
      }

      public Bus getBus() {
         try {
            ServiceTracker tracker = new ServiceTracker(bundleContext, Bus.class.getName(), null);
            tracker.open();
            Bus bus = (Bus)tracker.waitForService(5000);
            tracker.close();
            return bus;
         }
         catch(Exception e) {
            throw new RuntimeException(e);
         }
      }
   }
}
