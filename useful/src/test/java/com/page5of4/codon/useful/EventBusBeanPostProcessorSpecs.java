package com.page5of4.codon.useful;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.common.eventbus.Subscribe;

public class EventBusBeanPostProcessorSpecs {
   @Test
   public void when_configuring() {
      AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
      applicationContext.register(DomainEventsConfig.class);
      applicationContext.register(SpringConfig.class);
      applicationContext.refresh();

      Object event = new Object();
      DomainEvents.post(event);
      Handler handler = applicationContext.getBean(Handler.class);
      assertThat(handler.getEvent()).isEqualTo(event);
   }

   public static class SpringConfig {
      @Bean
      public Handler handler() {
         return new Handler();
      }
   }

   public static class Handler {
      private Object event;

      public Object getEvent() {
         return event;
      }

      @Subscribe
      public void anything(Object event) {
         this.event = event;
      }
   }
}
