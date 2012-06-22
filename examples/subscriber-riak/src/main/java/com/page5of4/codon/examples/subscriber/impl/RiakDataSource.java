package com.page5of4.codon.examples.subscriber.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;

@Service
public class RiakDataSource {
   @Value("${riak.url}")
   private String riakUrl;

   public IRiakClient getRiakClient() throws RiakException {
      return RiakFactory.httpClient(riakUrl);
   }
}
