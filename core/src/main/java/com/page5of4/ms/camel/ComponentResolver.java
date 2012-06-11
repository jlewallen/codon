package com.page5of4.ms.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Component;

import com.page5of4.ms.EndpointAddress;

public interface ComponentResolver {
   Component createComponent(EndpointAddress address, CamelContext camelContext);
}
