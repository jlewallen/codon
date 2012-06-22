package com.page5of4.codon.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;

import com.page5of4.codon.BusException;
import com.page5of4.codon.EndpointAddress;

public class AlwaysFailComponentResolver implements ComponentResolver {
   @Override
   public Component createComponent(EndpointAddress address, CamelContext camelContext) {
      throw new BusException(String.format("No such Component: '%s'", address));
   }
}
