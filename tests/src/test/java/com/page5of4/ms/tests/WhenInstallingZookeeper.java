package com.page5of4.ms.tests;

import static com.page5of4.ms.support.BundleAssert.assertThat;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.ms.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WhenInstallingZookeeper extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(),
            mavenBundle().groupId("org.apache.zookeeper").artifactId("zookeeper").version("3.4.3")
      };
   }

   @Test
   public void zookeeper_bundle_is_installed() {
      assertThat(executor().getInstalledBundle("org.apache.hadoop.zookeeper")).isActive();
   }
}
