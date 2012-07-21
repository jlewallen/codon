package com.page5of4.codon.examples.subscriber.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.examples.messages.DeleteAllUsersMessages;

@Service
@MessageHandler
public class DeleteAllUsersHandler {
   private static final Logger logger = LoggerFactory.getLogger(DeleteAllUsersHandler.class);
   private final Bus bus;

   @Autowired
   public DeleteAllUsersHandler(Bus bus) {
      super();
      this.bus = bus;
   }

   @MessageHandler
   public void handle(DeleteAllUsersMessages message) {
      logger.info("Received {}", message);
   }

   @PostConstruct
   public void deleteAllUsersOnStartup() {
      bus.sendLocal(new DeleteAllUsersMessages());
   }
}
