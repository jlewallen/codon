package com.page5of4.codon.tests;

import static com.page5of4.codon.tests.support.BundleAssert.assertThat;
import static org.fest.assertions.Assertions.assertThat;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.AllConfinedStagedReactorFactory;

import com.page5of4.codon.Bus;
import com.page5of4.codon.tests.support.Provision;
import com.page5of4.codon.tests.support.WithContainer;

@RunWith(JUnit4TestRunner.class)
@ExamReactorStrategy(AllConfinedStagedReactorFactory.class)
public class WithCoreInstalledSpecs extends WithContainer {
   @Configuration
   public Option[] config() {
      return new Option[] { commonConfiguration(),
      };
   }

   @Before
   public void before() {
      Provision.with(executor()).base().core();
   }

   @Test
   public void can_get_bus_and_send_message() {
      Bus bus = helper().getBus();
      assertThat(bus).isNotNull();

      bus.sendLocal(new FooMessage("Andrea"));
   }

   @Test
   public void bundle_for_core_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.codon.core")).isActive();
   }

   @Test
   public void bundle_for_extender_is_installed() {
      assertThat(executor().getInstalledBundle("com.page5of4.codon.extender")).isActive();
   }

   public static class FooMessage implements Serializable {
      private static final long serialVersionUID = 1L;
      private String name;

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public FooMessage() {
         super();
      }

      public FooMessage(String name) {
         super();
         this.name = name;
      }
   }
}
