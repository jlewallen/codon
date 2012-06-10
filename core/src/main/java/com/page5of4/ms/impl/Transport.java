package com.page5of4.ms.impl;

import com.page5of4.ms.EndpointAddress;

public interface Transport {
   void send(EndpointAddress address, Object message);

   void listen(EndpointAddress address);
}
