package com.page5of4.codon.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Service;

import com.page5of4.codon.BusConfiguration.ListenerConfiguration;

@Service
public class HandlerRouteBuilder extends RouteBuilder {
   private final ListenerConfiguration listenerConfiguration;
   private final InvokeHandlerProcessor handlerProcessor;

   public HandlerRouteBuilder(ListenerConfiguration listenerConfiguration, InvokeHandlerProcessor handlerProcessor) {
      this.listenerConfiguration = listenerConfiguration;
      this.handlerProcessor = handlerProcessor;
   }

   @Override
   public void configure() throws Exception {
      PoisonProcessor poison = new PoisonProcessor();
      ProcessorDefinition<?> def = from(listenerConfiguration.getListenAddress()).
            id(listenerConfiguration.getId());
      if(listenerConfiguration.getTransacted()) {
         def = def.transacted();
      }
      def.choice().
            when(poison).
            to(listenerConfiguration.getPoisonAddress()).
            stop().
            end().
            doTry().
            process(handlerProcessor).
            doFinally().
            process(poison).
            end();
   }
}
