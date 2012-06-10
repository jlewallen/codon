package com.page5of4.ms.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.page5of4.ms.BusException;
import com.page5of4.ms.EndpointAddress;
import com.page5of4.ms.impl.MessageUtils;
import com.page5of4.ms.impl.Transport;

public class CamelTransport implements Transport {
   public static final String MESSAGE_TYPE_KEY = "messageType";

   private static final Logger logger = LoggerFactory.getLogger(Transport.class);
   private final CamelContext camelContext;
   private final ProducerTemplate producer;
   private final InvokeHandlerProcessor invokeHandlerProcessor;

   @Autowired
   public CamelTransport(CamelContext camelContext, InvokeHandlerProcessor invokeHandlerProcessor) {
      this.camelContext = camelContext;
      this.invokeHandlerProcessor = invokeHandlerProcessor;
      this.producer = camelContext.createProducerTemplate();
   }

   public void send(EndpointAddress address, Object message) {
      try {
         logger.debug("Sending {} -> {}", message, address);
         Map<String, Object> headers = new HashMap<String, Object>();
         headers.put(MESSAGE_TYPE_KEY, MessageUtils.getMessageType(message));
         producer.sendBodyAndHeaders(toCamelUrl(address), message, headers);
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to send %s to %s", message, address), e);
      }
   }

   public void listen(EndpointAddress address) {
      try {
         camelContext.addRoutes(new HandlerRouteBuilder(toCamelUrl(address), invokeHandlerProcessor));
      }
      catch(Exception e) {
         throw new BusException(String.format("Unable to listen on '%s'", address), e);
      }
   }

   private String toCamelUrl(EndpointAddress address) {
      return String.format("activemq:%s", address);
   }
}
