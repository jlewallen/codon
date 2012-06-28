package com.page5of4.codon.tests.integration;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.page5of4.codon.MessageHandler;

@MessageHandler
public class MessageAHandler {
   private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

   @MessageHandler
   public void handle(MessageAMessage message, Exchange exchange) {
      logger.info("Entering");
   }
}