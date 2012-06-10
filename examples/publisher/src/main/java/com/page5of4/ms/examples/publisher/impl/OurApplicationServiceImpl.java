package com.page5of4.ms.examples.publisher.impl;

import com.page5of4.ms.Bus;
import com.page5of4.ms.examples.messages.LaunchWorkMessage;
import com.page5of4.ms.examples.publisher.OurApplicationService;
import com.page5of4.ms.examples.publisher.StuffParameters;

public class OurApplicationServiceImpl implements OurApplicationService {
   private final Bus bus;

   public OurApplicationServiceImpl(Bus bus) {
      this.bus = bus;
   }

   @Override
   public void startDoingStuff(StuffParameters parameters) {
      for(long i = 0; i < parameters.getNumber(); ++i) {
         bus.sendLocal(new LaunchWorkMessage(parameters.getId(), i));
      }
   }
}
