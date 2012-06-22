package com.page5of4.codon.impl;

import com.page5of4.codon.EndpointAddress;

public interface Transport {
   void send(EndpointAddress address, Object message);

   void listen(EndpointAddress address);
}
