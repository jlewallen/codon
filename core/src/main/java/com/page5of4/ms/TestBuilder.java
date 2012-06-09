package com.page5of4.ms;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class TestBuilder extends RouteBuilder {
   @Override
   public void configure() throws Exception {
      String[] froms = new String[] { "activemq:dev.testing.1", "activemq:dev.testing.2" };
      for(String a : froms) {
         from(a).to("direct:nowhere");
      }
   }
}
