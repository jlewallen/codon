package com.page5of4.codon.camel;

import org.apache.camel.Exchange;

import com.page5of4.codon.Bus;

public class SendLocalProcessor extends BusProcessor {
   public SendLocalProcessor(Bus bus) {
      super(bus);
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      getBus().sendLocal(exchange.getIn().getBody());
   }
}