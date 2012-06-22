package com.page5of4.codon.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;

import com.page5of4.codon.EndpointAddress;

public interface ComponentResolver {
   Component createComponent(EndpointAddress address, CamelContext camelContext);
}
