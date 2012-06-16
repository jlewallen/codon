package com.page5of4.ms.tests;

import static org.fest.assertions.Assertions.assertThat;
import static org.openengsb.labs.paxexam.karaf.options.KarafDistributionOption.logLevel;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

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
public class WhenInstallingCore extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(), logLevel(LogLevelOption.LogLevel.WARN), festAssert(), junitBundles(),
            mavenBundle().groupId("com.page5of4.ms").artifactId("core").versionAsInProject()
      };
   }

   @Test
   public void bundle_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.ms.core")).isNotNull();
   }
}
