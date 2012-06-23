package com.page5of4.codon.examples.application.impl;

import com.page5of4.codon.Bus;
import com.page5of4.codon.examples.application.OurApplicationService;
import com.page5of4.codon.examples.application.StuffParameters;
import com.page5of4.codon.examples.messages.LaunchWorkMessage;

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
