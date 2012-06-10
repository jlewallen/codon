package com.page5of4.ms.camel;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.ms.impl.HandlerBinding;
import com.page5of4.ms.impl.HandlerDispatcher;
import com.page5of4.ms.impl.HandlerRegistry;
import com.page5of4.ms.impl.InstanceResolver;
import com.page5of4.ms.impl.MessageUtils;

public class InvokeHandlerProcessor implements Processor {
   private static final Logger logger = LoggerFactory.getLogger(InvokeHandlerProcessor.class);
   private final HandlerRegistry handlerRegistry;
   private final InstanceResolver resolver;

   public InvokeHandlerProcessor(HandlerRegistry handlerRegistry, InstanceResolver resolver) {
      super();
      this.handlerRegistry = handlerRegistry;
      this.resolver = resolver;
   }

   @Override
   public void process(Exchange exchange) throws Exception {
      Message message = exchange.getIn();
      Object body = message.getBody();
      String messageType = MessageUtils.getMessageType(body);
      Map<String, Object> headers = message.getHeaders();
      if(headers.containsKey(CamelTransport.MESSAGE_TYPE_KEY)) {
         messageType = headers.get(CamelTransport.MESSAGE_TYPE_KEY).toString();
      }
      else {
         logger.warn("No message type on message, assuming no conversion necessary: '{}'", messageType);
      }

      logger.debug(String.format("Processing: %s %s", messageType, body));
      for(HandlerBinding binding : handlerRegistry.getBindingsFor(body.getClass())) {
         logger.debug("Invoking {}", binding.getMethod());
         new HandlerDispatcher(resolver, binding).dispatch(body);
      }
   }
}
