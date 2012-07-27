package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.CodonKarafDistributionOption.featuresBoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(EagerSingleStagedReactorFactory.class)
public class OutOfTheBoxSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] {
            commonConfiguration(),
            featuresBoot("config", "codon-dependencies", "codon-core")
      };
   }

   @Test
   public void test() {
      Bus bus = helper().getBus();
      System.out.println(executor().executeCommand("osgi:list -s -t 0"));
   }
}
