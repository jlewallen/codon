package com.page5of4.ms.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.page5of4.ms.Bus;

public abstract class BusProcessor implements Processor {
   private final Bus bus;

   public Bus getBus() {
      return bus;
   }

   public BusProcessor(Bus bus) {
      super();
      this.bus = bus;
   }

   @Override
   public abstract void process(Exchange exchange) throws Exception;
}
