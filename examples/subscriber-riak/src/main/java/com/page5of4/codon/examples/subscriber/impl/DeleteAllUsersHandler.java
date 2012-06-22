package com.page5of4.codon.examples.subscriber.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.bucket.Bucket;
import com.page5of4.codon.Bus;
import com.page5of4.codon.MessageHandler;
import com.page5of4.codon.examples.messages.DeleteAllUsersMessages;

@Service
@MessageHandler
public class DeleteAllUsersHandler {
   private static final Logger logger = LoggerFactory.getLogger(DeleteAllUsersHandler.class);
   private final RiakDataSource dataSource;
   private final Bus bus;

   @Autowired
   public DeleteAllUsersHandler(RiakDataSource dataSource, Bus bus) {
      super();
      this.dataSource = dataSource;
      this.bus = bus;
   }

   @MessageHandler
   public void handle(DeleteAllUsersMessages message) throws RiakException {
      logger.info("Received {}", message);

      IRiakClient riakClient = dataSource.getRiakClient();
      Bucket users = riakClient.createBucket("users").execute();
      for(String key : users.keys()) {
         users.delete(key).execute();
      }
   }

   @PostConstruct
   public void deleteAllUsersOnStartup() {
      bus.sendLocal(new DeleteAllUsersMessages());
   }
}
